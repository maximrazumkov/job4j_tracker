package ru.job4j.tracker.actions;

import ru.job4j.tracker.services.ITracker;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.models.Item;

import java.util.List;
import java.util.function.Consumer;

public class FindAllAction extends BaseAction {

    public FindAllAction(int key, String name, Consumer<String> output) {
        super(key, name, output);
    }

    @Override
    public boolean execute(Input input, ITracker tracker) {
        List<Item> items = tracker.findAll();
        for (Item item : items) {
            super.getOutput().accept(item.getId() + " " + item.getName());
        }
        return true;
    }
}
