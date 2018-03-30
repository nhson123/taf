package com.anecon.taf.client.rest;

import com.anecon.taf.core.AutomationFrameworkException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.net.ssl.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class RestClientBuilder {
    private static final HostnameVerifier NULL_HOSTNAME_VERIFIER = (s, sslSession) -> true;
    private static final TrustManager[] NULL_TRUST_MANAGER = new TrustManager[]{new AllTrustingManager()};
    private static final SSLContext sc;

    private JsonProvider jsonProvider = new JsonProvider();
    private ClientBuilder clientBuilder = ClientBuilder.newBuilder().register(JacksonFeature.class).register(jsonProvider);

    static {
        System.setProperty("https.protocols", "TLSv1");
        try {
            sc = SSLContext.getInstance("TLSv1");
            sc.init(null, NULL_TRUST_MANAGER, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            SSLContext.setDefault(sc);
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            throw new AutomationFrameworkException("Could not initialize SSL context", e);
        }
    }

    public RestClientBuilder acceptAllCerts() {
        clientBuilder = clientBuilder.sslContext(sc).hostnameVerifier(NULL_HOSTNAME_VERIFIER);
        return this;
    }

    public RestClientBuilder useEntityLogger() {
        clientBuilder.register(EntityLoggingFilter.INSTANCE);
        return this;
    }

    public RestClientBuilder register(Class<?> componentClass) {
        clientBuilder = clientBuilder.register(componentClass);
        return this;
    }

    public RestClientBuilder register(Object component) {
        clientBuilder = clientBuilder.register(component);
        return this;
    }

    public RestClientBuilder jsonIncludeFields(JsonInclude.Include include) {
        jsonProvider.mapper.setSerializationInclusion(include);
        return this;
    }

    public Client build() {
        return clientBuilder.build();
    }

    private static class AllTrustingManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    @Provider
    private static class JsonProvider implements ContextResolver<ObjectMapper> {
        private final ObjectMapper mapper;

        private JsonProvider() {
            mapper = new ObjectMapper();
        }

        @Override
        public ObjectMapper getContext(Class<?> type) {
            return mapper;
        }
    }
}
