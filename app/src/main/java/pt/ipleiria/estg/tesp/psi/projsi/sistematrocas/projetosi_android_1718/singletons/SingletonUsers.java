package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.User;

/**
 * Created by leona on 24/11/2017.
 */

public class SingletonUsers {
    private static SingletonUsers INSTANCE = null;
    private List<User> users;

    public static SingletonUsers getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SingletonUsers();

        return INSTANCE;
    }

    private SingletonUsers() {
        users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean addUser(User user)
    {
        return users.add(user);
    }

    public boolean removeUser(User user)
    {
        return users.remove(user);
    }

    public boolean editUser(User oldUser, User newUser)
    {
        User updatedUser = users.set(users.indexOf(oldUser), newUser);

        return users.contains(updatedUser);
    }

    public User searchUserID(Long id)
    {
        return users.get(id.intValue());
    }
}
