package ru.job4j.tracker.actions;

import ru.job4j.tracker.services.ITracker;
import ru.job4j.tracker.input.Input;

import java.util.function.Consumer;

public class ExitAction extends BaseAction {

    public ExitAction(int key, String name, Consumer<String> output) {
        super(key, name, output);
    }

    @Override
    public boolean execute(Input input, ITracker tracker) {
        return false;
    }
}
