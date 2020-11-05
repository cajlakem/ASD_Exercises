package fh.campus.asd.backend.usermanagement.exceptions.dataaccessor;

import fh.campus.asd.backend.usermanagement.exceptions.datamanger.UserManagerException;

public class UserManagerUserAlreadyExistException extends UserManagerException {
    public UserManagerUserAlreadyExistException(String msg){
        super(msg);
    }
}
