package com.bringto.sudoku.service;

public interface EventListener {
    void update(EventEnum eventType, EventData data);
}
