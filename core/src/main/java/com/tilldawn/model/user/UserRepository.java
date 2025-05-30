package com.tilldawn.model.user;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static UserRepository repo;

    public UserRepository() {
        users.add(new User("GUEST", "", "", ""));
    }

    public static UserRepository getInstance() {
        if (repo == null)
            repo = new UserRepository();
        return repo;
    }

    List<User> users = new ArrayList<>();

    public User getByUsername(String username) {
        for (User user: users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public User get(String username, String pass) {
        User user = getByUsername(username);
        if (user.getPassword().equals(pass))
            return user;
        return null;
    }

    public void add(User user) {
        users.add(user);
    }

    public User getGuest() {
        return getByUsername("GUEST");
    }
}
