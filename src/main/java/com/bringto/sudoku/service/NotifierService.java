package com.bringto.sudoku.service;

import java.util.*;

/* HUB de eventos */
public class NotifierService {

    private static NotifierService instance;

    public static NotifierService getInstance() {
        if (instance == null) {
            instance = new NotifierService();
        }
        return instance;
    }

    private final Map<EventEnum, List<EventListener>> listeners =
            new EnumMap<>(EventEnum.class);

    private NotifierService() {
        for (EventEnum e : EventEnum.values()) {
            listeners.put(e, new ArrayList<>());
        }
    }

    public void subscribe(EventEnum event, EventListener listener) {
        listeners
                .computeIfAbsent(event, e -> new ArrayList<>());

        if (!listeners.get(event).contains(listener)) {
            listeners.get(event).add(listener);
        }
    }

    public void notify(final EventEnum eventType, final EventData data) {

        List<EventListener> selected = listeners.get(eventType);

        if (selected == null || selected.isEmpty()) {
            return;
        }

        for (EventListener listener : new ArrayList<>(selected)) {
            listener.update(eventType, data);
        }
    }
}
