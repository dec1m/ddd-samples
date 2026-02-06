package com.trach.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ElevatorTest {

    private Elevator elevator;

    @BeforeEach
    void setup() {
        elevator = new Elevator(new Floor(0), new Floor(5), new Floor(0));
    }

    @Test
    void shouldStartIdleAtInitialFloor() {
        assertEquals(new Floor(0), elevator.getCurrentFloor());
        assertEquals(Elevator.State.IDLE, elevator.getState());
        assertEquals(Direction.NONE, elevator.getDirection());
    }

    @Test
    void shouldAddOrderSuccessfully() {
        var order = new Order(new Floor(3), Direction.UP, Order.Source.OUTSIDE_FLOOR);
        elevator.add(order);
        elevator.step();
        assertNotEquals(Elevator.State.IDLE, elevator.getState());
    }

    @Test
    void shouldMoveUpAndStopAtRequestedFloor() {
        var order = new Order(new Floor(2), Direction.UP, Order.Source.OUTSIDE_FLOOR);
        elevator.add(order);

        elevator.step();
        assertEquals(Elevator.State.MOVING, elevator.getState());
        assertEquals(Direction.UP, elevator.getDirection());
        assertEquals(new Floor(1), elevator.getCurrentFloor());

        elevator.step();
        assertEquals(new Floor(2), elevator.getCurrentFloor());
        assertEquals(Elevator.State.IDLE, elevator.getState());
        assertEquals(Direction.NONE, elevator.getDirection());
    }

    @Test
    void shouldThrowIfOrderIsOutsideOfShaft() {
        var invalidOrder = new Order(new Floor(6), Direction.UP, Order.Source.OUTSIDE_FLOOR);
        assertThrows(IllegalArgumentException.class, () -> elevator.add(invalidOrder));
    }



    @Test
    void shouldNotOpenDoorsWhileMoving() {
        var order = new Order(new Floor(2), Direction.UP, Order.Source.OUTSIDE_FLOOR);
        elevator.add(order);

        elevator.step();
        assertEquals(Elevator.State.MOVING, elevator.getState());
        assertEquals(Direction.UP, elevator.getDirection());

        elevator.step();
        assertEquals(Elevator.State.IDLE, elevator.getState());
        assertEquals(new Floor(2), elevator.getCurrentFloor());
        assertEquals(Direction.NONE, elevator.getDirection());
    }
}
