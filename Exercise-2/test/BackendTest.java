import fh.campus.asd.backend.usermanagement.exceptions.authetication.UserManagerPasswordEncryptionException;
import fh.campus.asd.backend.usermanagement.exceptions.authetication.UserManagerPasswordWrogException;
import fh.campus.asd.backend.usermanagement.exceptions.dataaccessor.UserManagerUserAlreadyExistException;
import fh.campus.asd.backend.usermanagement.exceptions.dataaccessor.UserManagerUserNotFoundException;
import fh.campus.asd.backend.usermanagement.exceptions.datamanger.UserManagerException;
import fh.campus.asd.backend.usermanagement.exceptions.datamanger.UserManagerSessionNotFoundException;
import org.junit.jupiter.api.Test;
import fh.campus.asd.backend.usermanagement.models.User;
import fh.campus.asd.backend.usermanagement.models.Session;
import fh.campus.asd.backend.usermanagement.models.DataManagerObject;
import fh.campus.asd.backend.usermanagement.implementations.helper.JavaMemoryUserManager;
import fh.campus.asd.backend.usermanagement.implementations.helper.SimplePasswordAuthenticator;
import fh.campus.asd.backend.usermanagement.implementations.helper.SimpleSessionManager;
import fh.campus.asd.backend.usermanagement.implementations.SimpleUserManagerImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BackendTest {
    User user = new User("john","john","doe","password");
    Session session = new Session(user,"session");
    @Test
    void TestUser() {
        user.setIsAccountDisabled(true);
        assertEquals("john",user.getUserName());
        assertEquals("john",user.getFirstName());
        assertEquals("doe",user.getLastName());
        assertEquals("password",user.getPassword());
        assertEquals(true,user.getIsAccountDisabled());
    }

    @Test
    void TestSession() {

        session.setTsLastAccess(1);
        assertEquals("session",session.getSessionId());
        assertEquals(1,session.getTsLastAccess());
        assertEquals(false,session.isLastAccessOlderThan(5));
    }

    @Test
    void TestDataManagerObject() {
        DataManagerObject datamanager =  new DataManagerObject();
        datamanager.setTsLastAccess(123);
        assertEquals(123,datamanager.getTsLastAccess());

    }

    @Test
    void TestJavaMemoryUserManager() throws UserManagerUserNotFoundException, UserManagerUserAlreadyExistException {
        JavaMemoryUserManager  javausermanager = new JavaMemoryUserManager();
        javausermanager.addUser(user);
        assertEquals(user,javausermanager.findUserby("john"));
        javausermanager.deleteUserWithId("john");
        assertEquals(0,javausermanager.getUserList().size());
    }

    @Test
    void TestSimplePasswordAuthenticator() throws UserManagerPasswordEncryptionException, UserManagerPasswordWrogException {
        SimplePasswordAuthenticator  passwordauthenticator = new SimplePasswordAuthenticator();
        String password = "password";
        assertEquals(String.valueOf(password.hashCode()),passwordauthenticator.encryptPassword(password));
    }

    @Test
    void TestSimpleSessionManager() throws UserManagerSessionNotFoundException {
        SimpleSessionManager simplesessionmanager = new SimpleSessionManager();
        List<Session> sessions = new ArrayList<>();
        sessions.add(session);
        simplesessionmanager.setSessions(sessions);
        simplesessionmanager.createNewSession(user);
        assertEquals(session,simplesessionmanager.findSessionById("session"));
        assertEquals(sessions,simplesessionmanager.getSessions());
        simplesessionmanager.destroySessionWithId(session);
        assertNotEquals(0,simplesessionmanager.getSessions().size());
    }

    @Test
    void TestSimpleUserManagerImpl() throws UserManagerException {
        SimpleUserManagerImpl simpleusermanager = new SimpleUserManagerImpl();
        //simpleusermanager.changePassword(session.getSessionId(),"password","newpassword");
        //assertEquals("newpassword",session.getUser().getPassword());
    }
}