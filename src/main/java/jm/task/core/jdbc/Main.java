package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            UserService userService = new UserServiceImpl();
            userService.createUsersTable();
            userService.saveUser("Ivan", "Ivanov", (byte) 40);
            userService.saveUser("Gena", "Varvarov", (byte) 23);
            userService.saveUser("Stepan", "Sidorov", (byte) 20);
            userService.saveUser("Michael", "Michaelov", (byte) 22);
            userService.saveUser("David", "Davidov", (byte) 21);
            userService.saveUser("Richard", "Richardov", (byte) 22);

            List<User> users = userService.getAllUsers();
            for (User user : users) {
                System.out.println(user);
            }

            if(users.size() > 0){
                userService.removeUserById(users.get(0).getId());
            }

            for (User user : userService.getAllUsers()) {
                System.out.println(user);
            }

            userService.cleanUsersTable();

            for (User user : userService.getAllUsers()) {
                System.out.println(user);
            }

            userService.dropUsersTable();
        } finally {
            Util.closeSessionFactory();
        }
    }
}
