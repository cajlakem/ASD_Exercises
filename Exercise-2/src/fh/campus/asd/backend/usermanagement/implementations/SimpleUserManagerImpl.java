package fh.campus.asd.backend.usermanagement.implementations;

import fh.campus.asd.backend.usermanagement.exceptions.authetication.UserManagerPasswordEncryptionException;
import fh.campus.asd.backend.usermanagement.exceptions.authetication.UserManagerPasswordWrogException;
import fh.campus.asd.backend.usermanagement.exceptions.authetication.UserManagerSessionIdNotValidException;
import fh.campus.asd.backend.usermanagement.exceptions.dataaccessor.UserManagerUserAlreadyExistException;
import fh.campus.asd.backend.usermanagement.exceptions.dataaccessor.UserManagerUserNotFoundException;
import fh.campus.asd.backend.usermanagement.exceptions.datamanger.*;
import fh.campus.asd.backend.usermanagement.implementations.helper.JavaMemoryUserManager;
import fh.campus.asd.backend.usermanagement.implementations.helper.SimplePasswordAuthenticator;
import fh.campus.asd.backend.usermanagement.implementations.helper.SimpleSessionManager;
import fh.campus.asd.backend.usermanagement.interfaces.PasswordAuthenticatorIF;
import fh.campus.asd.backend.usermanagement.interfaces.SessionManagerIF;
import fh.campus.asd.backend.usermanagement.interfaces.UserAccessorIF;
import fh.campus.asd.backend.usermanagement.interfaces.UserManagerService;
import fh.campus.asd.backend.usermanagement.models.Session;
import fh.campus.asd.backend.usermanagement.models.User;

public class SimpleUserManagerImpl implements UserManagerService {
    private static SimpleUserManagerImpl userManager = new SimpleUserManagerImpl();

    private  UserAccessorIF userAccessorIF;
    private  SessionManagerIF sessionManagerIF;
    private  PasswordAuthenticatorIF authenticatorIF;

    public SimpleUserManagerImpl(){

    }

    public void init(){
        this.sessionManagerIF = SimpleSessionManager.getInstance();
        this.userAccessorIF = new JavaMemoryUserManager().getInstance();
        this.authenticatorIF = new SimplePasswordAuthenticator();
    }

    public static SimpleUserManagerImpl getInstance() {
        return userManager;
    }

    public String login(String userName, String loginPassword) throws UserManagerCredentialsWrogException, UserManagerMaxLoginAttemptsReachedException {
        try {
            User user = userAccessorIF.findUserby(userName);
            if(user.getIsAccountDisabled()){
                throw new UserManagerMaxLoginAttemptsReachedException("Your account has been disabled because of too many login attempts!");
            }
            authenticatorIF.validatePassword(user.getPassword(), loginPassword);
            Session newSession = sessionManagerIF.createNewSession(user);
            return newSession.getSessionId();
        } catch (UserManagerUserNotFoundException | UserManagerPasswordWrogException e) {
            throw new UserManagerCredentialsWrogException("Your credentials are wrong!"+e.getMessage());
        }
        catch (UserManagerMaxLoginAttemptsReachedException ex){
            throw ex;
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
        userAccessorIF.addUser(new User(userName, firstName, lastName, authenticatorIF.encryptPassword(password)));
    }



    public void logout(String sessionId) throws UserManagerSessionIdNotValidException {

        try {
            for (Session s: sessionManagerIF.getSessions()) {
                System.out.println(s.getSessionId());
            }
            Session session = sessionManagerIF.findSessionById(sessionId);
            sessionManagerIF.destroySessionWithId(session);
        } catch (UserManagerSessionNotFoundException e) {
            throw new UserManagerSessionIdNotValidException("Your session is not vaild! ");
        }
    }

    @Override
    public void disableAcoount(String userId) throws UserManagerUserNotFoundException {
        User user = userAccessorIF.findUserby(userId);
        user.setIsAccountDisabled(true);
    }
}
