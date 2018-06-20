package com.example.taya.justcook.domain.entity.dimension;

/**
 * Created by tayab on 02.06.2016.
 */
public class Piece extends Dimension {
    public Piece() {
        super();
        name="штуки";
    }
    public Piece(double value) {
        super(value);
        name="штуки";
    }

    @Override
    protected double convertToBaseDimension(int index) {
        return value;
    }
}
