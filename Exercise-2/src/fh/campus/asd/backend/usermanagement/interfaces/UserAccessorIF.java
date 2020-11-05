package fh.campus.asd.backend.usermanagement.interfaces;

import fh.campus.asd.backend.usermanagement.exceptions.dataaccessor.UserManagerUserAlreadyExistException;
import fh.campus.asd.backend.usermanagement.exceptions.dataaccessor.UserManagerUserNotFoundException;
import fh.campus.asd.backend.usermanagement.models.User;

public interface UserAccessorIF {
    User findUserby(String userName) throws UserManagerUserNotFoundException;
    void deleteUserWithId(String userName) throws UserManagerUserNotFoundException;
    void addUser(User user) throws UserManagerUserAlreadyExistException;
}
