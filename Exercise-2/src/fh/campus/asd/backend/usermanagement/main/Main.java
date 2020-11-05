package fh.campus.asd.backend.usermanagement.main;

import fh.campus.asd.backend.usermanagement.implementations.JavaMemoryUserManager;
import fh.campus.asd.backend.usermanagement.implementations.SimplePasswordAuthenticator;
import fh.campus.asd.backend.usermanagement.implementations.SimpleSessionManager;
import fh.campus.asd.backend.usermanagement.interfaces.SessionManagerIF;
import fh.campus.asd.backend.usermanagement.interfaces.UserManagerIF;
import fh.campus.asd.backend.usermanagement.implementation.UserManager;



public class Main {

    public static void main(String[] args) {
        SessionManagerIF managerIF = new SimpleSessionManager(60000, 1000);
        Thread thread = new Thread((Runnable) managerIF);
        thread.start();
        UserManagerIF userManagerIF = new UserManager(new JavaMemoryUserManager(),managerIF , new SimplePasswordAuthenticator());
    }
}
