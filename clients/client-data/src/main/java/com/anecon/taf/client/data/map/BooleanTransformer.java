package com.anecon.taf.client.data.map;

public class BooleanTransformer implements Transformer<Boolean> {
    @Override
    public Boolean transform(String source) {
        if (source.isEmpty()) {
            return null;
        }

        return Boolean.valueOf(source);
    }
}
