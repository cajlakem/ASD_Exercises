package fh.campus.asd.backend.usermanagement.exceptions.dataaccessor;

import fh.campus.asd.backend.usermanagement.exceptions.datamanger.UserManagerException;

public class UserManagerUserNotFoundException extends UserManagerException {
    public UserManagerUserNotFoundException(String msg){
        super(msg);
    }
}
