package fh.campus.asd.backend.usermanagement.implementations.helper;

import fh.campus.asd.backend.usermanagement.exceptions.dataaccessor.UserManagerUserAlreadyExistException;
import fh.campus.asd.backend.usermanagement.exceptions.dataaccessor.UserManagerUserNotFoundException;
import fh.campus.asd.backend.usermanagement.interfaces.UserAccessorIF;
import fh.campus.asd.backend.usermanagement.models.User;

import java.util.ArrayList;
import java.util.List;

public class JavaMemoryUserManager implements UserAccessorIF {
    public List<User> getUserList() {
        return userList;
    }

    private final List<User> userList = new ArrayList<>();

    public static void setInstance(JavaMemoryUserManager instance) {
        JavaMemoryUserManager.instance = instance;
    }

    private static JavaMemoryUserManager instance = new JavaMemoryUserManager();

    public JavaMemoryUserManager() {

    }

    public static JavaMemoryUserManager getInstance(){
        return instance;
    }

    public User findUserby(String userName) throws UserManagerUserNotFoundException {
        for(User user: userList){
            if(user.getUserName().equals(userName)) return user;
        }
        throw new UserManagerUserNotFoundException("User "+userName+" not found!");
    }

    public void deleteUserWithId(String userName) throws UserManagerUserNotFoundException {
        boolean response = userList.removeIf(user -> user.amINamed(userName));
        if(!response)
        {
            throw new UserManagerUserNotFoundException("User "+userName+" not found!");
        }
    }

    public void addUser(User user) throws UserManagerUserAlreadyExistException{
        try {
            this.findUserby(user.getUserName());
            throw new UserManagerUserAlreadyExistException("User is already in use!");
        } catch (UserManagerUserNotFoundException e) {
            userList.add(user);
        }
    }

}
