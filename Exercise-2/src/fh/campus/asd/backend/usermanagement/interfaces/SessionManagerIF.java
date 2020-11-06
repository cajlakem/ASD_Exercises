package fh.campus.asd.backend.usermanagement.interfaces;

import fh.campus.asd.backend.usermanagement.exceptions.datamanger.UserManagerSessionNotFoundException;
import fh.campus.asd.backend.usermanagement.models.Session;
import fh.campus.asd.backend.usermanagement.models.User;

import java.util.List;

public interface SessionManagerIF {
    Session createNewSession(User user);
    void destroySessionWithId(Session session) throws UserManagerSessionNotFoundException;
    Session findSessionById(String sessionId) throws UserManagerSessionNotFoundException;
    List<Session> getSessions();
}
