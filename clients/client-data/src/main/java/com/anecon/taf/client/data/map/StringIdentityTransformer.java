package com.anecon.taf.client.data.map;

public class StringIdentityTransformer implements Transformer<String> {
    @Override
    public String transform(String source) {
        return source;
    }
}
