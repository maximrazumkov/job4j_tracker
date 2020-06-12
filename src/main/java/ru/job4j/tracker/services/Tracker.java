package ru.job4j.tracker.services;

import ru.job4j.tracker.models.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Tracker implements ITracker<String> {
    /**
     * Массив для хранение заявок.
     */
    private final ArrayList<Item> items = new ArrayList<>();
    /**
     * Указатель ячейки для новой заявки.
     */
    private int position = 0;

    /**
     * Метод реализаущий добавление заявки в хранилище
     * @param item новая заявка
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        items.add(item);
        return item;
    }

    /**
     * Редактирование заявок
     * @param id уникальный идентификатор заявки
     * @param item отредактированая заявка
     * @return результат редактирования заявки (удалось отредактировать или не удалось)
     */
    public boolean replace(String id, Item item) {
        return Collections.replaceAll(items, item, item);
    }

    /**
     * Удаление заявок
     * @param id уникальный идентификатор заявки
     * @return результат удаления заявки (удалось удалить или не удалось)
     */
    public boolean delete(String id) {
        Item item = new Item("");
        item.setId(Integer.valueOf(id));
        return items.remove(item);
    }

    /**
     * Получение списка всех заявок
     * @return список всех заявок
     */
    public List<Item> findAll() {
        return items;
    }

    /**
     * Получение списка по имени
     * @param key название заявки
     * @return список найденный заявок
     */
    public List<Item> findByName(String key) {
        List<Item> itemList = new ArrayList<>();
        this.items.forEach(item -> {
            if (item.getName().equals(key)) {
                itemList.add(item);
            }
        });
        return itemList;
    }

    /**
     * Получение заявки по id
     * @param id уникальный идентификатор заявки
     * @return найденная заявка
     */
    public Item findById(String id) {
        Item item = new Item("");
        item.setId(Integer.valueOf(id));
        int idx = items.indexOf(item);
        item = (idx != -1) ? items.get(idx) : null;
        return item;

    }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описание. Для идентификации нам нужен уникальный ключ.
     * @return Уникальный ключ.
     */
    private Integer generateId() {
        return new Random().nextInt() + (int) (System.currentTimeMillis() >>> 32);
    }
}
