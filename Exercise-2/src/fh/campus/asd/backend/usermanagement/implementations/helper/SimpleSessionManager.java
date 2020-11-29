package fh.campus.asd.backend.usermanagement.implementations.helper;

import fh.campus.asd.backend.usermanagement.exceptions.datamanger.UserManagerSessionNotFoundException;
import fh.campus.asd.backend.usermanagement.interfaces.SessionManagerIF;
import fh.campus.asd.backend.usermanagement.models.Session;
import fh.campus.asd.backend.usermanagement.models.User;

import java.util.ArrayList;
import java.util.List;

import static fh.campus.asd.frontend.Main.ExitApplication;
import static java.lang.Thread.sleep;

@SuppressWarnings("ALL")
public class SimpleSessionManager implements SessionManagerIF, Runnable {

    private static SimpleSessionManager sessionManager = new SimpleSessionManager();

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    private List<Session> sessions = new ArrayList<>();
    private final long cleanUpSessionsOlderThanNMillisec = 60000;
    private final long cleanUpIntervalInMillisec = 300;

    public SimpleSessionManager() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public static SimpleSessionManager getInstance() {
        return sessionManager;
    }

    private String generateSessionId() {
        return String.valueOf(System.currentTimeMillis());
    }

    public Session createNewSession(User user) {
        Session newSession = new Session(user, this.generateSessionId());
        this.sessions.add(newSession);
        return newSession;
    }

    public void destroySessionWithId(Session session) {
        sessions.remove(session);
    }

    public Session findSessionById(String sessionId) throws UserManagerSessionNotFoundException {
        for (Session session : this.sessions) {
            if (session.getSessionId().equals(sessionId)) return session;
        }
        throw new UserManagerSessionNotFoundException("Session with " + sessionId + " not found!");
    }

    @Override
    public List<Session> getSessions() {
        return this.sessions;
    }

    private void cleanUpOlderSessions() {
        System.out.println("Nr auf Active Sessions: "+ sessions.size());
        sessions.removeIf(session -> session.isLastAccessOlderThan(cleanUpSessionsOlderThanNMillisec));
    }

    @Override
    public void run() {
        while (!ExitApplication) {
            this.cleanUpOlderSessions();
            try {
                System.out.println("Waiting for cleaning the sessions...");
                sleep(ExitApplication ? 0: this.cleanUpIntervalInMillisec);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
