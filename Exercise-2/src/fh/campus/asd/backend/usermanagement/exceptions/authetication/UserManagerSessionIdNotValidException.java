package fh.campus.asd.backend.usermanagement.exceptions.authetication;

import fh.campus.asd.backend.usermanagement.exceptions.datamanger.UserManagerException;

public class UserManagerSessionIdNotValidException extends UserManagerException {
    public UserManagerSessionIdNotValidException(String msg) {
        super(msg);
    }
}
