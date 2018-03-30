package com.anecon.taf.client.rest;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Generic class for handling automatic metadata and entity extraction from {@link Response}
 *
 * @param <P> the payload type
 * @param <E> the error type
 */
public class GenericHttpResponse<P, E> {
    private final Class<E> errorType;
    private final Class<P> payloadType;

    private final Response response;
    private final P payload;
    private final E error;

    /**
     * Creates a new {@code GenericHttpResponse}, unmarshalling either the payload or the error when possible.
     * <p>
     * {@code response}'s entity stream will be converted to a buffered one by calling {@link Response#bufferEntity()}
     *
     * @param response    the response to extract information from
     * @param payloadType the payload class
     * @param errorType   the error class
     */
    protected GenericHttpResponse(Response response, Class<P> payloadType, Class<E> errorType) {
        this.response = Objects.requireNonNull(response);
        this.errorType = Objects.requireNonNull(errorType);
        this.payloadType = Objects.requireNonNull(payloadType);

        response.bufferEntity();

        final Optional<P> payloadDto = readPayload(response);
        if (payloadDto.isPresent()) {
            this.payload = payloadDto.get();
            this.error = null;
        } else {
            this.payload = null;
            this.error = readErrorDto(response).orElse(null);
        }
    }

    protected GenericHttpResponse(Response response) {
        this.response = Objects.requireNonNull(response);
        this.errorType = null;
        this.payloadType = null;

        this.payload = null;
        this.error = null;
    }

    public final int getStatusCode() {
        return response.getStatus();
    }

    public final P getPayload() {
        return payload;
    }

    public final Optional<E> getError() {
        return Optional.ofNullable(error);
    }

    /**
     * Checks if this Response contains an error, either if its HTTP Status Code indicates an error or if an error entity
     * could be extracted
     *
     * @return true if this response contains an error
     */
    public boolean isError() {
        return getStatusCode() >= 400 || getError().isPresent();
    }

    public boolean hasPayload() {
        return payload != null;
    }

    public Map<String, NewCookie> getCookies() {
        return response.getCookies();
    }

    public URI getLocation() {
        return response.getLocation();
    }

    public MultivaluedMap<String, Object> getHeaders() {
        return response.getHeaders();
    }

    public MultivaluedMap<String, String> getStringHeaders() {
        return response.getStringHeaders();
    }

    protected Optional<E> readErrorDto(Response response) {
        try {
            return Optional.of(response.readEntity(errorType));
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    protected Optional<P> readPayload(Response response) {
        try {
            return Optional.of(response.readEntity(payloadType));
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }
}
