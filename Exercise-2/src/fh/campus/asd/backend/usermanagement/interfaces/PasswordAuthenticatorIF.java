package fh.campus.asd.backend.usermanagement.interfaces;

import fh.campus.asd.backend.usermanagement.exceptions.authetication.UserManagerPasswordEncryptionException;
import fh.campus.asd.backend.usermanagement.exceptions.authetication.UserManagerPasswordWrogException;

public interface PasswordAuthenticatorIF {
    String encryptPassword(String password) throws UserManagerPasswordEncryptionException;
    void validatePassword(String userPassword, String providedPassword) throws UserManagerPasswordWrogException;
}
