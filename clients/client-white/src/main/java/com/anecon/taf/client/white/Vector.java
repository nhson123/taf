package com.anecon.taf.client.white;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class Vector {
    private static final Gson GSON = Converters.registerAll(new GsonBuilder()).setPrettyPrinting().serializeNulls().create();

    private int x;
    private int y;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
