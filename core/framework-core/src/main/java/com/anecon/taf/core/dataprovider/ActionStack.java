package com.anecon.taf.core.dataprovider;

import java.util.LinkedList;

public class ActionStack {
    private LinkedList<Runnable> actions = new LinkedList<>();

    public void put(Runnable action) {
        actions.addFirst(action);
    }

    public void executeAll() {
        actions.forEach(Runnable::run);

        clear();
    }

    public void clear() {
        actions.clear();
    }
}
