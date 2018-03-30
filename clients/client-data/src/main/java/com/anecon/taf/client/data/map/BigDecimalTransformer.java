package com.anecon.taf.client.data.map;

import java.math.BigDecimal;

public class BigDecimalTransformer extends DecimalTransformer implements Transformer<BigDecimal> {
    @Override
    public BigDecimal transform(String source) {
        if (source == null) {
            return null;
        }

        return new BigDecimal(commaToDot(source));
    }
}
