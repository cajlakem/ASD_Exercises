package fh.campus.asd.backend.usermanagement.models;

public class Session {
    private final User user;
    private String sessionId;
    private long tsLastAccess;

    public Session(User user, String sessionId) {
        this.user = user;
        this.sessionId = sessionId;
        this.tsLastAccess = System.currentTimeMillis();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isLastAccessOlderThan(long seconds){
        long userActiveTime = System.currentTimeMillis() - this.tsLastAccess;
        return userActiveTime <= seconds;
    }

    public long getTsLastAccess() {
        return tsLastAccess;
    }

    public void setTsLastAccess(long tsLastAccess) {
        this.tsLastAccess = tsLastAccess;
    }
    public User getUser() {
        return user;
    }
}
