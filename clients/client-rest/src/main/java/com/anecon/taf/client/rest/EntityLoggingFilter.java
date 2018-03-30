package com.anecon.taf.client.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Priority(Integer.MIN_VALUE)
class EntityLoggingFilter implements ClientRequestFilter, ClientResponseFilter, WriterInterceptor {
    static final EntityLoggingFilter INSTANCE = new EntityLoggingFilter();

    private static final Logger logger = LoggerFactory.getLogger(EntityLoggingFilter.class);
    private static final String ENTITY_STREAM_PROPERTY = "EntityLoggingFilter.entityStream";
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final int ENTITY_SIZE_MAX = 8 * 1024; // 8 Kilobytes

    private static InputStream copyStreamToStringBuilder(InputStream stream, final StringBuilder sb, final Charset charset) throws IOException {
        if (!stream.markSupported()) {
            stream = new BufferedInputStream(stream);
        }
        stream.mark(ENTITY_SIZE_MAX + 1);
        final byte[] entityBuffer = new byte[ENTITY_SIZE_MAX + 1];
        final int entitySize = stream.read(entityBuffer);

        sb.append(new String(entityBuffer, 0, Math.min(entitySize, ENTITY_SIZE_MAX), charset));
        if (entitySize > ENTITY_SIZE_MAX) {
            sb.append("...more...");
        }
        stream.reset();
        return stream;
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        if (requestContext.hasEntity()) {
            final OutputStream stream = new LoggingStream(requestContext.getEntityStream());
            requestContext.setEntityStream(stream);
            requestContext.setProperty(ENTITY_STREAM_PROPERTY, stream);
        }
    }

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        final StringBuilder sb = new StringBuilder();
        if (responseContext.hasEntity()) {
            responseContext.setEntityStream(copyStreamToStringBuilder(responseContext.getEntityStream(), sb, DEFAULT_CHARSET));
            logger.trace("<-- {}", sb);
        }
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        final LoggingStream stream = (LoggingStream) context.getProperty(ENTITY_STREAM_PROPERTY);
        context.proceed();
        if (stream != null) {
            logger.trace("--> {}", stream.getContent(DEFAULT_CHARSET));
        }
    }

    private static class LoggingStream extends FilterOutputStream {
        private final StringBuilder sb = new StringBuilder();
        private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        LoggingStream(OutputStream out) {
            super(out);
        }

        String getContent(Charset charset) {
            // write entity to the builder
            final byte[] entity = buffer.toByteArray();

            sb.append(new String(entity, 0, entity.length, charset));
            if (entity.length > ENTITY_SIZE_MAX) {
                sb.append("...more...");
            }

            return sb.toString();
        }

        @Override
        public void write(final int i) throws IOException {
            if (buffer.size() <= ENTITY_SIZE_MAX) {
                buffer.write(i);
            }
            out.write(i);
        }
    }
}