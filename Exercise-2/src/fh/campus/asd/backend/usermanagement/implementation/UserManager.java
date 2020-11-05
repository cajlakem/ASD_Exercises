package fh.campus.asd.backend.usermanagement.implementation;

import fh.campus.asd.backend.usermanagement.exceptions.authetication.UserManagerPasswordEncryptionException;
import fh.campus.asd.backend.usermanagement.exceptions.authetication.UserManagerPasswordWrogException;
import fh.campus.asd.backend.usermanagement.exceptions.authetication.UserManagerSessionIdNotValidException;
import fh.campus.asd.backend.usermanagement.exceptions.dataaccessor.UserManagerUserNotFoundException;
import fh.campus.asd.backend.usermanagement.exceptions.datamanger.*;
import fh.campus.asd.backend.usermanagement.interfaces.PasswordAuthenticatorIF;
import fh.campus.asd.backend.usermanagement.interfaces.SessionManagerIF;
import fh.campus.asd.backend.usermanagement.interfaces.UserAccessorIF;
import fh.campus.asd.backend.usermanagement.interfaces.UserManagerIF;
import fh.campus.asd.backend.usermanagement.models.Session;
import fh.campus.asd.backend.usermanagement.models.User;

public class UserManager implements UserManagerIF {
    private final UserAccessorIF userAccessorIF;
    private final SessionManagerIF sessionManagerIF;
    private final PasswordAuthenticatorIF authenticatorIF;

    public UserManager(UserAccessorIF userAccessorIF, SessionManagerIF sessionManagerIF, PasswordAuthenticatorIF authenticatorIF) {
        this.userAccessorIF = userAccessorIF;
        this.sessionManagerIF = sessionManagerIF;
        this.authenticatorIF = authenticatorIF;
    }

    public String login(String userName, String loginPassword) throws UserManagerCredentialsWrogException {
        try {
            User user = userAccessorIF.findUserby(userName);
            authenticatorIF.validatePassword(user.getPassword(), loginPassword);
            Session newSession = sessionManagerIF.createNewSession(user);
            return newSession.getSessionId();
        } catch (UserManagerUserNotFoundException | UserManagerPasswordWrogException e) {
            throw new UserManagerCredentialsWrogException("Your credentials are wrong");
        }
    }

    public void changePassword(String sessionId, String oldPassword, String newPassword) throws UserManagerException {
        try {
            Session session = sessionManagerIF.findSessionById(sessionId);
            User currentUser = session.getUser();
            authenticatorIF.validatePassword(session.getUser().getPassword(), oldPassword);
            currentUser.setPassword(authenticatorIF.encryptPassword(newPassword));
        } catch (UserManagerSessionNotFoundException e) {
            throw new UserManagerSessionIdNotValidException("Your session is not vaild!");
        } catch (UserManagerPasswordWrogException e) {
            throw new UserManagerCredentialsWrogException("Your credentials are wrong");
        } catch (UserManagerPasswordEncryptionException e) {
            throw new UserManagerException(e.getMessage());
        }
    }

    public void deleteUserProfile(String sessionId) throws UserManagerException {
        try {
            Session session = sessionManagerIF.findSessionById(sessionId);
            User currentUser = session.getUser();
            userAccessorIF.deleteUserWithId(currentUser.getUserName());
        } catch (UserManagerSessionNotFoundException e) {
            throw new UserManagerSessionIdNotValidException("Your session is not vaild!");
        } catch (UserManagerUserNotFoundException e){
            throw new UserManagerUserNotFoundException("User not found!");
        }

    }

    public void createUserProfile(String firstName, String lastName, String userName, String password) throws UserManagerException {
        try {
            userAccessorIF.addUser(new User(userName, firstName, lastName, authenticatorIF.encryptPassword(password)));
        } catch (fh.campus.asd.backend.usermanagement.exceptions.dataaccessor.UserManagerUserAlreadyExistException e) {
            throw new UserManagerUserAlreadyExistException("User with this name already exists!");
        }catch (UserManagerPasswordEncryptionException e){
            throw new UserManagerException(e.getMessage());
        }

    }

    public void logout(String sessionId) throws UserManagerSessionIdNotValidException {
        try {
            Session session = sessionManagerIF.findSessionById(sessionId);
            sessionManagerIF.destroySessionWithId(session);
        } catch (UserManagerSessionNotFoundException e) {
            throw new UserManagerSessionIdNotValidException("Your session is not vaild!");
        }
    }
}
