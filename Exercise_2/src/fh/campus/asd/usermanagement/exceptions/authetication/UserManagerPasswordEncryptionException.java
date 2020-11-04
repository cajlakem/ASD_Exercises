package fh.campus.asd.usermanagement.exceptions.authetication;

import fh.campus.asd.usermanagement.exceptions.datamanger.UserManagerException;

public class UserManagerPasswordEncryptionException extends UserManagerException {
    public UserManagerPasswordEncryptionException(String msg) {
        super(msg);
    }
}
