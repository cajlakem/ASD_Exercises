package fh.campus.asd.usermanagement.interfaces;

import fh.campus.asd.usermanagement.exceptions.datamanger.UserManagerException;

public interface UserManagerIF {
    String login(String userName, String password) throws UserManagerException;
    void changePassword(String sessionId, String oldPassword, String newPassword) throws UserManagerException;
    void deleteUserProfile(String sessionId) throws UserManagerException;
    void createUserProfile(String firstName, String lastName, String userName, String password) throws UserManagerException;
    void logout(String sessionId) throws UserManagerException;
}
