package com.trach.domains;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void creation() {
        Floor floor = new Floor(3);
        Order order = new Order(floor, Direction.UP, Order.Source.OUTSIDE_FLOOR);

        assertEquals(floor, order.getFloor());
        assertEquals(Direction.UP, order.getDirection());
        assertEquals(Order.Source.OUTSIDE_FLOOR, order.getSource());
    }

    @Test
    void floorIsRequired() {
        assertThrows(IllegalArgumentException.class, () ->
                new Order(null, Direction.UP, Order.Source.INSIDE_CABIN));
    }

    @Test
    void directionIsRequired() {
        assertThrows(IllegalArgumentException.class, () ->
                new Order(new Floor(2), null, Order.Source.INSIDE_CABIN));
    }

    @Test
    void sourceIsRequired() {
        assertThrows(IllegalArgumentException.class, () ->
                new Order(new Floor(2), Direction.DOWN, null));
    }

    @Test
    void equality() {
        EqualsVerifier.forClass(Order.class).verify();
    }
}