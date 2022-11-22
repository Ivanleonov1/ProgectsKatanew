package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    User user = new User();

    Connection connection = getConnection();
    //Statement statement = null;
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE `users`.`users` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NULL,\n" +
                "  `lastname` VARCHAR(45) NULL,\n" +
                "  `age` INT NULL,\n" +
                "  PRIMARY KEY (`id`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;\n";

        try (PreparedStatement preparedStatement = Util.getConnection().
                prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE if exists users";
        try {
            PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement preparedStatement;

        if (user == null) {
            String sql = "INSERT INTO users (name, lastname, age) VALUES(?, ?, ?)";
            try {
                connection = Util.getConnection();
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setByte(3, user.getAge());

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void removeUserById(long id) throws SQLException {
        PreparedStatement preparedStatement;

        String sql = "DELETE FROM USERS WHERE ID=?";

        try {
            connection =Util.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Util.getConnection().close();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users")) {
            while (resultSet.next()) {
                users = new ArrayList<>();
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);

            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                Util.getConnection().close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return getAllUsers();
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}