package fh.campus.asd;

import fh.campus.asd.usermanagement.helper.implementations.JavaMemoryUserManager;
import fh.campus.asd.usermanagement.helper.implementations.SimplePasswordAuthenticator;
import fh.campus.asd.usermanagement.helper.implementations.SimpleSessionManager;
import fh.campus.asd.usermanagement.interfaces.UserManagerIF;
import fh.campus.asd.usermanagement.main.implementation.UserManager;


public class Main {

    public static void main(String[] args) {
        UserManagerIF userManagerIF = new UserManager(new JavaMemoryUserManager(), new SimpleSessionManager(60000, 1000), new SimplePasswordAuthenticator());
    }
}
