package fh.campus.asd.backend.usermanagement.implementations.helper;

import fh.campus.asd.backend.usermanagement.exceptions.datamanger.UserManagerSessionNotFoundException;
import fh.campus.asd.backend.usermanagement.interfaces.SessionManagerIF;
import fh.campus.asd.backend.usermanagement.models.Session;
import fh.campus.asd.backend.usermanagement.models.User;

import java.util.ArrayList;
import java.util.List;

public class SimpleSessionManager implements SessionManagerIF, Runnable {

    private static SimpleSessionManager sessionManager = new SimpleSessionManager();
    private List<Session> sessions = new ArrayList<>();
    private final long cleanUpSessionsOlderThanNMillisec = 60000;
    private final long cleanUpIntervalInMillisec = 30000;

    public SimpleSessionManager() {
        Thread thread = new Thread(this);
        thread.start();

    }

    public static SimpleSessionManager getInstance(){
        return sessionManager;
    }

    private String generateSessionId(){
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
        for (Session session: this.sessions) {
            if(session.getSessionId().equals(sessionId)) return session;
        }
        throw new UserManagerSessionNotFoundException("Session with "+sessionId+ " not found!");
    }

    @Override
    public List<Session> getSessions() {
        return this.sessions;
    }

    private void cleanUpOlderSessions(){
        sessions.removeIf(session -> session.isLastAccessOlderThan(cleanUpSessionsOlderThanNMillisec));
    }

    @Override
    public void run() {
        while(true){
            this.cleanUpOlderSessions();
            try {
                System.out.println("Waiting for cleaning the sessions...");
                Thread.sleep(this.cleanUpIntervalInMillisec);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
