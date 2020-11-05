package fh.campus.asd;

import fh.campus.asd.usermanagement.helper.implementations.JavaMemoryUserManager;
import fh.campus.asd.usermanagement.helper.implementations.SimplePasswordAuthenticator;
import fh.campus.asd.usermanagement.helper.implementations.SimpleSessionManager;
import fh.campus.asd.usermanagement.interfaces.SessionManagerIF;
import fh.campus.asd.usermanagement.interfaces.UserManagerIF;
import fh.campus.asd.usermanagement.main.implementation.UserManager;


public class Main {

    public static void main(String[] args) {
        SessionManagerIF managerIF = new SimpleSessionManager(60000, 1000);
        Thread thread = new Thread((Runnable) managerIF);
        thread.start();
        UserManagerIF userManagerIF = new UserManager(new JavaMemoryUserManager(),managerIF , new SimplePasswordAuthenticator());
    }
}
