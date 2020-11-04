package fh.campus.asd.usermanagement.exceptions.authetication;

import fh.campus.asd.usermanagement.exceptions.datamanger.UserManagerException;

public class UserManagerPasswordWrogException extends UserManagerException {
    public UserManagerPasswordWrogException(String msg) {
        super(msg);
    }
}
