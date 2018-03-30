package com.anecon.taf.client.white;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import java.util.ArrayList;
import java.util.List;

public class RemoteUIClientFactory {
    public static RemoteUiClientWrapper getRemoteUIClientWrapper(String endpointUrl) {
        return new RemoteUiClientWrapper(RemoteUIClientFactory.getRemoteUIClient(endpointUrl));
    }

    private static IRemoteUIClient getRemoteUIClient(String endpointUrl) {
        List<Object> providers = new ArrayList<>();
        JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        jsonProvider.setMapper(mapper);
        providers.add(jsonProvider);

        IRemoteUIClient clientProxy = JAXRSClientFactory.create(endpointUrl, IRemoteUIClientAnnotated.class, providers);
        ClientConfiguration config = WebClient.getConfig(clientProxy);
        HTTPConduit conduit = (HTTPConduit) config.getConduit();
        HTTPClientPolicy policy = conduit.getClient();
        policy.setReceiveTimeout(10 * 60 * 1000); // 10 minutes
        return clientProxy;
    }
}
