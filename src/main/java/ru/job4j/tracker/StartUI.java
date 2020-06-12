package ru.job4j.tracker;


import ru.job4j.tracker.actions.*;
import ru.job4j.tracker.input.ConsoleInput;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.input.ValidateInput;
import ru.job4j.tracker.services.ITracker;
import ru.job4j.tracker.services.Tracker;

import java.util.ArrayList;
import java.util.function.Consumer;

public class StartUI {

    private final Consumer<String> output;

    public StartUI(Consumer<String> output) {
        this.output = output;
    }

    public void init(Input input, ITracker tracker, ArrayList<UserAction> actions) {
        boolean run = true;
        while (run) {
            this.showMenu(actions);
            int select = input.askInt("Select: ");
            UserAction action = actions.get(select);
            run = action.execute(input, tracker);
            output.accept(" ");
        }
    }

    private void showMenu(ArrayList<UserAction> actions) {
        output.accept("Menu.");
        actions.forEach(userAction -> output.accept(userAction.name()));
    }

    public static void main(String[] args) {
        Input input = new ConsoleInput();
        Input validate = new ValidateInput(input);
        ITracker tracker = new Tracker();
        ArrayList<UserAction> actions = new ArrayList<UserAction>() { {
                add(new CreateAction(0, "Add item", System.out::println));
                add(new ReplaceAction(1, "Replace item", System.out::println));
                add(new DeleteAction(2, "Delete item", System.out::println));
                add(new FindAllAction(3, "Find all items", System.out::println));
                add(new FindByIdAction(4, "Find by id item", System.out::println));
                add(new FindByNameAction(5, "Find by name item", System.out::println));
                add(new ExitAction(6, "Exit", System.out::println));
        } };
        new StartUI(System.out::println).init(validate, tracker, actions);
    }
}
