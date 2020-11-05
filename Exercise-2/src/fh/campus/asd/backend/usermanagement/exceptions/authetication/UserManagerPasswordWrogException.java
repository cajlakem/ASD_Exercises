package fh.campus.asd.backend.usermanagement.exceptions.authetication;


import fh.campus.asd.backend.usermanagement.exceptions.datamanger.UserManagerException;

public class UserManagerPasswordWrogException extends UserManagerException {
    public UserManagerPasswordWrogException(String msg) {
        super(msg);
    }
}
