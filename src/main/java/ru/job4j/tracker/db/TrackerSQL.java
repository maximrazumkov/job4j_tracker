package ru.job4j.tracker.db;

import ru.job4j.tracker.models.Item;
import ru.job4j.tracker.services.ITracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TrackerSQL implements ITracker<Integer>, AutoCloseable {

    private final Connection connection;

    public TrackerSQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void close() throws Exception {
        try {
            connection.close();
        } catch (Exception e) {
            try  {
                connection.close();
            } catch (Exception t) {

            }
        }
    }

    @Override
    public Item add(Item item) {
        String query = "insert into item(name) values (?)";
        try (PreparedStatement preStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preStatement.setString(1, item.getName());
            preStatement.executeUpdate();
            ResultSet rs = preStatement.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getInt("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public boolean replace(Integer id, Item item) {
        String query = "update item set name = ? where id = ?";
        boolean result = true;
        try (PreparedStatement preStatement = connection.prepareStatement(query)) {
            preStatement.setString(1, item.getName());
            preStatement.setInt(2, id);
            int res = preStatement.executeUpdate();
            result = res == 0 ? false : true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    public boolean delete(Integer id) {
        String query = "delete from item where id = ?";
        boolean result = true;
        try (PreparedStatement preStatement = connection.prepareStatement(query)) {
            preStatement.setInt(1, id);
            int res = preStatement.executeUpdate();
            result = res == 0 ? false : true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    public Item findById(Integer id) {
        String query = "select * from item where id = ?";
        Item item = null;
        try (PreparedStatement preStatement = connection.prepareStatement(query)) {
            preStatement.setInt(1, id);
            ResultSet rs = preStatement.executeQuery();
            if (rs.next()) {
                item = new Item(rs.getInt("id"), rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<Item> findAll() {
        String query = "select * from item";
        List<Item> items = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                items.add(new Item(rs.getInt("id"), rs.getString("name")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public List<Item> findByName(String key) {
        String query = "select * from item where name = ?";
        List<Item> items = new ArrayList<>();
        try (PreparedStatement preStatement = connection.prepareStatement(query)) {
            preStatement.setString(1, key);
            ResultSet rs = preStatement.executeQuery();
            while (rs.next()) {
                items.add(new Item(rs.getInt("id"), rs.getString("name")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
}
