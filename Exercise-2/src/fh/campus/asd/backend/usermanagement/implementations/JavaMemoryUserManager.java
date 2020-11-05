package fh.campus.asd.backend.usermanagement.implementations;

import fh.campus.asd.backend.usermanagement.exceptions.dataaccessor.UserManagerUserNotFoundException;
import fh.campus.asd.backend.usermanagement.interfaces.UserAccessorIF;
import fh.campus.asd.backend.usermanagement.models.User;

import java.util.ArrayList;
import java.util.List;

public class JavaMemoryUserManager implements UserAccessorIF {
    private final List<User> userList = new ArrayList<>();

    public User findUserby(String userName) throws UserManagerUserNotFoundException {
        for (User user: userList){
            if(user.amINamed(userName)) return user;
        }
        throw new UserManagerUserNotFoundException("User "+userName+" not found!");
    }

    public void deleteUserWithId(String userName) throws UserManagerUserNotFoundException {
        userList.removeIf(user -> user.amINamed(userName));
        throw new UserManagerUserNotFoundException("User "+userName+" not found!");

    }

    public void addUser(User user) {
        userList.add(user);
    }
}
