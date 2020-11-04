package fh.campus.asd.usermanagement.interfaces;

import fh.campus.asd.usermanagement.exceptions.datamanger.UserManagerSessionNotFoundException;
import fh.campus.asd.usermanagement.models.Session;
import fh.campus.asd.usermanagement.models.User;

public interface SessionManagerIF {
    Session createNewSession(User user);
    void destroySessionWithId(Session session) throws UserManagerSessionNotFoundException;
    Session findSessionById(String sessionId) throws UserManagerSessionNotFoundException;
}
