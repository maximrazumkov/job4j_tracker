package ru.job4j.tracker.db;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class IniterConnection {

    private Connection connection;

    public Connection init() {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            createBD(config);
            this.connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
            createTablesDb();
            return connection;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private void createTablesDb() throws Exception {
        String query = String.format("create table if not exists item(id serial primary key, name varchar(250));");
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (Exception e) {
            throw e;
        }
    }

    private void createBD(Properties config) throws Exception {
        String url = config.getProperty("url");
        String nameDb = url.substring(url.lastIndexOf("/") + 1);
        String urlWithoutBd = url.substring(0, url.lastIndexOf("/") + 1);
        try (
                Connection connection = DriverManager.getConnection(
                        urlWithoutBd,
                        config.getProperty("username"),
                        config.getProperty("password")

                )
        ) {
            PreparedStatement preStatement = connection.prepareStatement("select 1 from pg_database where datname = ?");
            preStatement.setString(1, nameDb);
            ResultSet rs = preStatement.executeQuery();
            if (!rs.next()) {
                String query = String.format("create database %s with owner = postgres encoding = 'UTF8' TABLESPACE = pg_default", nameDb);
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
