package ru.job4j.tracker.actions;

import ru.job4j.tracker.services.ITracker;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.models.Item;

import java.util.function.Consumer;

public class FindByIdAction extends BaseAction {

    public FindByIdAction(int key, String name, Consumer<String> output) {
        super(key, name, output);
    }

    @Override
    public boolean execute(Input input, ITracker tracker) {
        String id = input.askStr("");
        Item item = tracker.findById(id);
        if (item == null) {
            super.getOutput().accept("Заявка не найдена");
        } else {
            super.getOutput().accept(item.getId() + " " + item.getName());
        }
        return true;
    }
}
