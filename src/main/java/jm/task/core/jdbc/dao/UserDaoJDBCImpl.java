package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    private static final String CREATE_TABLE = "CREATE TABLE if not exists `users`.`users` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NULL,\n" +
            "  `lastName` VARCHAR(45) NULL,\n" +
            "  `age` INT NULL,\n" +
            "  PRIMARY KEY (`id`))\n" +
            "ENGINE = InnoDB\n" +
            "DEFAULT CHARACTER SET = utf8;\n";
    private static final String DROP_TABLE = "DROP TABLE if exists users";
    private static final String INSERT_USERS = "INSERT INTO users (name, lastname, age) VALUES(?, ?, ?)";
    private static final String DELETE_USERS_ID = "DELETE FROM USERS WHERE ID=?";
    private static final String SELECT_FROM_USERS = "SELECT * FROM users";
    private static final String TRUNCATE_TABLE = "TRUNCATE TABLE users";


    private Connection connection = getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (PreparedStatement preparedStatement = connection.
                prepareStatement(CREATE_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        User user = null;
        if (user != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS)){
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setByte(3, user.getAge());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public void removeUserById(long id) {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (ResultSet resultSet = connection.createStatement().executeQuery(SELECT_FROM_USERS)) {
            while (resultSet.next()) {
                users = new ArrayList<>();
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    public void cleanUsersTable() {
        Statement statement = connection.createStatement();
            statement.executeUpdate(TRUNCATE_TABLE);


    }
}