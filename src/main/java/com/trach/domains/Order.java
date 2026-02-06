package com.trach.domains;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public final class Order {

    private final Floor floor;
    private final Direction direction;
    private final Source source;

    public Order(Floor floor, Direction direction, Source source) {
        if (floor == null || direction == null || source == null) {
            throw new IllegalArgumentException("Floor and direction must not be null");
        }
        this.floor = floor;
        this.direction = direction;
        this.source = source;
    }

    public enum Source {
        INSIDE_CABIN,
        OUTSIDE_FLOOR
    }
}