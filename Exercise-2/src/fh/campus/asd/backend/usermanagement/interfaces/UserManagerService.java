package fh.campus.asd.backend.usermanagement.interfaces;

import fh.campus.asd.backend.usermanagement.exceptions.dataaccessor.UserManagerUserNotFoundException;
import fh.campus.asd.backend.usermanagement.exceptions.datamanger.UserManagerException;
import fh.campus.asd.backend.usermanagement.models.User;

public interface UserManagerService {
    String login(String userName, String password) throws UserManagerException;
    void changePassword(String sessionId, String oldPassword, String newPassword) throws UserManagerException;
    void deleteUserProfile(String sessionId) throws UserManagerException;
    void createUserProfile(String firstName, String lastName, String userName, String password) throws UserManagerException;
    void logout(String sessionId) throws UserManagerException;
    void disableAcoount(String userId) throws UserManagerUserNotFoundException;
}
