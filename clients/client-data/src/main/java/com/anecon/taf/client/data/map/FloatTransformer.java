package com.anecon.taf.client.data.map;

public class FloatTransformer extends DecimalTransformer implements Transformer<Float> {
    @Override
    public Float transform(String source) {
        if (source == null) {
            return null;
        }

        return Float.valueOf(commaToDot(source));
    }
}
