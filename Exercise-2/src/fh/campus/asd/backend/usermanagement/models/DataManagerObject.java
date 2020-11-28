package fh.campus.asd.backend.usermanagement.models;


public class DataManagerObject {
    public long getTsLastAccess() {
        return tsLastAccess;
    }

    public void setTsLastAccess(long tsLastAccess) {
        this.tsLastAccess = tsLastAccess;
    }

    private long tsLastAccess;
    public DataManagerObject(){
        tsLastAccess = System.currentTimeMillis();
    }
}
