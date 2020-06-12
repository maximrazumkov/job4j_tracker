package ru.job4j.tracker.actions;

import ru.job4j.tracker.services.ITracker;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.models.Item;

import java.util.List;
import java.util.function.Consumer;

public class FindByNameAction extends BaseAction {

    public FindByNameAction(int key, String name, Consumer<String> output) {
        super(key, name, output);
    }

    @Override
    public boolean execute(Input input, ITracker tracker) {
        String name = input.askStr("");
        List<Item> items = tracker.findByName(name);
        for (Item item : items) {
            super.getOutput().accept(item.getId() + " " + item.getName());
        }
        return true;
    }
}
