package fh.campus.asd.backend.usermanagement.exceptions.authetication;

import fh.campus.asd.backend.usermanagement.exceptions.datamanger.UserManagerException;

public class UserManagerPasswordEncryptionException extends UserManagerException {
    public UserManagerPasswordEncryptionException(String msg) {
        super(msg);
    }
}
