package fh.campus.asd.backend.usermanagement.factory;

import fh.campus.asd.backend.usermanagement.exceptions.creation.UserManagerFailedToCreateUserManagerException;
import fh.campus.asd.backend.usermanagement.implementations.SimpleUserManagerImpl;
import fh.campus.asd.backend.usermanagement.interfaces.UserManagerService;

public class UserManagerFactory {
    public static UserManagerService getUserManagerIF(String type) throws UserManagerFailedToCreateUserManagerException {
        if(type.equalsIgnoreCase("SimpleUserManagerImpl")){
            SimpleUserManagerImpl userManager = SimpleUserManagerImpl.getInstance();
            userManager.init();
            return userManager;
        }
        throw new UserManagerFailedToCreateUserManagerException("Unable to create instance!");
    }
}
