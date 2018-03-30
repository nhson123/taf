package com.anecon.taf.client.data.map;

public class DoubleTransformer extends DecimalTransformer implements Transformer<Double> {
    @Override
    public Double transform(String source) {
        if (source == null) {
            return null;
        }

        return Double.valueOf(commaToDot(source));
    }
}
