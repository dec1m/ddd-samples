package com.trach.domains;


import lombok.Getter;

import java.util.ArrayDeque;
import java.util.Queue;

@Getter
public class Elevator {

    private final Floor minFloor;
    private final Floor maxFloor;

    private Floor currentFloor;
    private Direction direction = Direction.NONE;
    private Elevator.State state = Elevator.State.IDLE;
    private Door.State door = Door.State.CLOSED;

    private final Queue<Order> requests = new ArrayDeque<>();

    public Elevator(Floor minFloor, Floor maxFloor, Floor startAt) {
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.currentFloor = startAt;
    }

    public void add(Order order) {
        ensureInService();
        ensureValidFloor(order.getFloor());
        requests.add(order);
    }

    public void step() {
        if (state == State.IDLE && !requests.isEmpty()) {
            closeDoors();
            chooseDirection();
            state = State.MOVING;
        }

        if (state == State.MOVING) {
            moveOneFloor();

            requests.stream()
                    .filter(order -> order.getFloor().equals(currentFloor))
                    .findFirst()
                    .ifPresent(this::stopAtCurrentFloor);
        }
    }

    private void moveOneFloor() {
        ensureDoorsClosed();

        int nextFloorNumber = Direction.UP.equals(direction)
                ? currentFloor.number() + 1
                : currentFloor.number() - 1;

        if (nextFloorNumber > maxFloor.number() || nextFloorNumber < minFloor.number()) {
            throw new IllegalStateException("Cannot move outside of shaft");
        }

        currentFloor = new Floor(nextFloorNumber);
    }

    private void stopAtCurrentFloor(Order order) {
        state = State.STOPPED;
        requests.remove(order);
        openDoors();
        state = State.IDLE;
        direction = Direction.NONE;
    }

    private void chooseDirection() {
        Order nextOrder = requests.peek();
        if (nextOrder == null) {
            direction = Direction.NONE;
            return;
        }

        Floor nextFloor = nextOrder.getFloor();

        if (nextFloor.above(currentFloor)) {
            direction = Direction.UP;
        } else if (nextFloor.below(currentFloor)) {
            direction = Direction.DOWN;
        } else {
            direction = Direction.NONE;
        }
    }

    private void openDoors() {
        ensureOnFloor();
        door = Door.State.OPEN;
    }

    private void closeDoors() {
        door = Door.State.CLOSED;
    }

    private void ensureDoorsClosed() {
        if (Door.State.OPEN.equals(door)) {
            throw new IllegalStateException("Cannot move with open doors");
        }
    }

    private void ensureOnFloor() {
        if (Elevator.State.MOVING.equals(state)) {
            throw new IllegalStateException("Cannot open doors between floors");
        }
    }

    private void ensureValidFloor(Floor floor) {
        if(floor.below(minFloor) || floor.above(maxFloor))  {
            throw new IllegalArgumentException("Order is not valid");
        }
    }

    private void ensureInService() {
        if (Elevator.State.OUT_OF_SERVICE.equals(state)) {
            throw new IllegalStateException("Elevator out of service");
        }
    }


    public enum State {
        IDLE,
        MOVING,
        STOPPED,
        OUT_OF_SERVICE
    }
}
