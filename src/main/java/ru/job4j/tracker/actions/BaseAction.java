package ru.job4j.tracker.actions;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class BaseAction implements UserAction {
    private final int key;
    private final String name;
    private final Consumer<String> output;

    protected BaseAction(final int key, final String name, Consumer<String> output) {
        this.key = key;
        this.name = name;
        this.output = output;
    }

    @Override
    public Consumer<String> getOutput() {
        return output;
    }

    @Override
    public int key() {
        return this.key;
    }

    @Override
    public String name() {
        return String.format("%s : %s", this.key, this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseAction that = (BaseAction) o;
        return key == that.key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
