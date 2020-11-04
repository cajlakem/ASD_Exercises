package fh.campus.asd.usermanagement.interfaces;

import fh.campus.asd.usermanagement.exceptions.authetication.UserManagerPasswordEncryptionException;
import fh.campus.asd.usermanagement.exceptions.authetication.UserManagerPasswordWrogException;

public interface PasswordAuthenticatorIF {
    String encryptPassword(String password) throws UserManagerPasswordEncryptionException;
    void validatePassword(String userPassword, String providedPassword) throws UserManagerPasswordWrogException;
}
