package com.anecon.taf.client.data.map;

public abstract class DecimalTransformer {
    protected String commaToDot(String number) {
        return number.replace(',', '.');
    }
}
