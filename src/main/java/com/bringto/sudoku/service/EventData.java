package com.bringto.sudoku.service;

public class EventData {

    private final EventEnum eventEnum;
    private final Object payload;

    private final Integer oldValue;
    private final Integer newValue;
    private final int row;
    private final int col;

    public EventData(EventEnum eventEnum, Object payload) {
        this(eventEnum, payload, null, null, -1, -1);
    }

    public EventData(
            EventEnum eventEnum,
            Object payload,
            Integer oldValue,
            Integer newValue,
            int row,
            int col
    ) {
        this.eventEnum = eventEnum;
        this.payload = payload;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.row = row;
        this.col = col;
    }

    public EventEnum getEventEnum() {
        return eventEnum;
    }

    public Object getPayload() {
        return payload;
    }

    public Integer getOldValue() {
        return oldValue;
    }

    public Integer getNewValue() {
        return newValue;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
