package fh.campus.asd.usermanagement.exceptions.dataaccessor;

import fh.campus.asd.usermanagement.exceptions.datamanger.UserManagerException;

public class UserManagerUserNotFoundException extends UserManagerException {
    public UserManagerUserNotFoundException(String msg){
        super(msg);
    }
}
