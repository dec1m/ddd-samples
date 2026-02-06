package com.trach.domains;


import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloorTest {

    @Test
    void creation() {
        var floor = new Floor(5);
        assertEquals(5, floor.number());
    }

    @Test
    void above() {
        var floor1 = new Floor(2);
        var floor2 = new Floor(5);

        assertTrue(floor2.above(floor1));
        assertFalse(floor1.above(floor2));
        assertFalse(floor1.above(floor1));
    }

    @Test
    void below() {
        var floor1 = new Floor(2);
        var floor2 = new Floor(5);

        assertTrue(floor1.below(floor2));
        assertFalse(floor2.below(floor1));
        assertFalse(floor1.below(floor1));
    }

    @Test
    public void equality() {
        EqualsVerifier.simple().forClass(Floor.class).verify();
    }
}