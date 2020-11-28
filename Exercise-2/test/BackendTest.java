import fh.campus.asd.backend.usermanagement.models.DataManagerObject;
import org.junit.jupiter.api.Test;
import fh.campus.asd.backend.usermanagement.models.User;
import fh.campus.asd.backend.usermanagement.models.Session;
import fh.campus.asd.backend.usermanagement.models.DataManagerObject;
import static org.junit.jupiter.api.Assertions.*;

class BackendTest {
    User user = new User("john","john","doe","password");
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
        Session session = new Session(user,"session");
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

}