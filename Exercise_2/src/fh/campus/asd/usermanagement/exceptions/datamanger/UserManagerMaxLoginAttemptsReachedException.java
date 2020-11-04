package fh.campus.asd.usermanagement.exceptions.datamanger;

public class UserManagerMaxLoginAttemptsReachedException extends UserManagerException{

    public UserManagerMaxLoginAttemptsReachedException(String msg) {
        super(msg);
    }
}
