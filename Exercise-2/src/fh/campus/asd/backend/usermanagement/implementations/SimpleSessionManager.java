package fh.campus.asd.backend.usermanagement.implementations;

import fh.campus.asd.backend.usermanagement.exceptions.datamanger.UserManagerSessionNotFoundException;
import fh.campus.asd.backend.usermanagement.interfaces.SessionManagerIF;
import fh.campus.asd.backend.usermanagement.models.Session;
import fh.campus.asd.backend.usermanagement.models.User;

import java.util.HashSet;
import java.util.Set;

public class SimpleSessionManager implements SessionManagerIF, Runnable {
    private final Set<Session> sessions = new HashSet<>();
    private final long cleanUpSessionsOlderThanNMillisec;
    private final long cleanUpIntervalInMillisec;

    public SimpleSessionManager(long cleanUpSessionsOlderThanNMillisec, long cleanUpIntervalInMillisec) {
        this.cleanUpSessionsOlderThanNMillisec = cleanUpSessionsOlderThanNMillisec;
        this.cleanUpIntervalInMillisec = cleanUpIntervalInMillisec;
    }

    private String generateSessionId(){
        return String.valueOf(System.currentTimeMillis());
    }

    public Session createNewSession(User user) {
        Session newSession = new Session(user, this.generateSessionId());
        sessions.add(newSession);
        return newSession;
    }

    public void destroySessionWithId(Session session) {
        sessions.remove(session);
    }

    public Session findSessionById(String sessionId) throws UserManagerSessionNotFoundException {
        for (Session session: sessions) {
            if(session.getSessionId().equals(sessionId)) return session;
        }
        throw new UserManagerSessionNotFoundException("Session with "+sessionId+ " not found!");
    }

    public void run() {
        try {
            Thread.sleep(cleanUpIntervalInMillisec);
            this.cleanUpOlderSessions();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void cleanUpOlderSessions(){
        sessions.removeIf(session -> session.isLastAccessOlderThan(cleanUpSessionsOlderThanNMillisec));
    }
}
