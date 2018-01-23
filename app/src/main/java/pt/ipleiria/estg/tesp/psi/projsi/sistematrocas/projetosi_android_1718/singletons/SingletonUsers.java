package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.singletons;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers.UserBDTable;
import pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.modelos.User;

/**
 * Created by leona on 24/11/2017.
 */

public class SingletonUsers {
    private static SingletonUsers INSTANCE = null;
    private ArrayList<User> users;
    private UserBDTable bdTable;

    public static SingletonUsers getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SingletonUsers(context);

        return INSTANCE;
    }

    private SingletonUsers(Context context) {
        users = new ArrayList<>();
        bdTable = new UserBDTable(context);
        users = bdTable.select();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean adicionarUserLocal(User user) {

        User userInserido = bdTable.insert(user);

        return userInserido != null && users.add(userInserido);
    }

    public boolean removerUserLocal(User user) {
        return bdTable.delete(user.getId()) && users.remove(user);
    }

    public boolean editarUserLocal(User user) {

        if(bdTable.update(user)) {
            User novoUser = users.set(user.getId().intValue(), user);

            return users.contains(novoUser);
        } else {
            return false;
        }
    }

    public User pesquisarUserPorID(Long id)
    {
        return users.get(id.intValue());
    }
}
