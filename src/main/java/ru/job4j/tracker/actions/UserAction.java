package ru.job4j.tracker.actions;

import ru.job4j.tracker.services.ITracker;
import ru.job4j.tracker.input.Input;

import java.util.function.Consumer;

public interface UserAction {
    int key();
    String name();
    Consumer<String> getOutput();
    boolean execute(Input input, ITracker tracker);
}
