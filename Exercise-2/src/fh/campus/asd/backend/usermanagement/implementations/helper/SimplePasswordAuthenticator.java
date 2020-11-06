package fh.campus.asd.backend.usermanagement.implementations.helper;

import fh.campus.asd.backend.usermanagement.exceptions.authetication.UserManagerPasswordEncryptionException;
import fh.campus.asd.backend.usermanagement.exceptions.authetication.UserManagerPasswordWrogException;
import fh.campus.asd.backend.usermanagement.interfaces.PasswordAuthenticatorIF;

public class SimplePasswordAuthenticator implements PasswordAuthenticatorIF {

    public String encryptPassword(String password) throws UserManagerPasswordEncryptionException {
        //TODO: Encrypt that shit correctly and throw a nice exception
        return String.valueOf(password.hashCode());
    }

    public void validatePassword(String userPassword, String providedPassword) throws UserManagerPasswordWrogException {
        if(!userPassword.equals(String.valueOf(providedPassword.hashCode()))) throw new UserManagerPasswordWrogException("Oh nooo");
    }
}
