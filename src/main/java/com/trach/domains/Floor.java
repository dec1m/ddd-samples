package com.trach.domains;

public record Floor(int number) {

    public Floor {
        if (number < 0) {
            throw new IllegalArgumentException("Floor below ground not supported");
        }
    }

    public boolean above(Floor other) {
        return this.number > other.number;
    }

    public boolean below(Floor other) {
        return this.number < other.number;
    }
}