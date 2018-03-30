package com.anecon.taf.client.data.map;

public class IntegerTransformer implements Transformer<Integer> {
    @Override
    public Integer transform(String source) {
        if (source.isEmpty()) {
            return null;
        }

        return Integer.valueOf(source);
    }
}
