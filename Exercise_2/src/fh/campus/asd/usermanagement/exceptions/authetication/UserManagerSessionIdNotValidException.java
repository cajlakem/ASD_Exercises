package fh.campus.asd.usermanagement.exceptions.authetication;

import fh.campus.asd.usermanagement.exceptions.datamanger.UserManagerException;

public class UserManagerSessionIdNotValidException extends UserManagerException {
    public UserManagerSessionIdNotValidException(String msg) {
        super(msg);
    }
}
