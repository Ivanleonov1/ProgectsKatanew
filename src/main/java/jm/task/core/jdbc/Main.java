package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
            public static void main(String[] args) throws SQLException {

                UserService userService = new UserServiceImpl();


                userService.createUsersTable();
                userService.saveUser("Ivan", "Ivanov", (byte) 25);
                userService.saveUser("Sveta", "Ivanova", (byte) 35);
                userService.saveUser("Oleg", "Ivanov", (byte) 23);
                userService.saveUser("Stas", "Petrov", (byte) 48);

                userService.getAllUsers();
                userService.cleanUsersTable();
                userService.dropUsersTable();
            }
        }



