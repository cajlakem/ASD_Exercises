package fh.campus.asd.usermanagement.exceptions.dataaccessor;

import fh.campus.asd.usermanagement.exceptions.datamanger.UserManagerException;

public class UserManagerUserAlreadyExistException extends UserManagerException {
    public UserManagerUserAlreadyExistException(String msg){
        super(msg);
    }
}
