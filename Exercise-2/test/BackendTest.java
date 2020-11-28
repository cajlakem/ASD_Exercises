import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import fh.campus.asd.backend.usermanagement.models.User;
import static org.junit.jupiter.api.Assertions.*;

class BackendTestTest {
    @Test
    void TestUser() {
        User user = new User("john","john","doe","password");
        assertEquals("john",user.getUserName());
        assertEquals("john",user.getFirstName());
        assertEquals("doe",user.getLastName());
        assertEquals("password",user.getPassword());
    }
}