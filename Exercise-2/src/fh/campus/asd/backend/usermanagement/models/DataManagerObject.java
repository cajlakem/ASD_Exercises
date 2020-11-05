package fh.campus.asd.backend.usermanagement.models;


public class DataManagerObject {
    private long tsLastAccess;
    DataManagerObject(){
        tsLastAccess = System.currentTimeMillis();
    }
}
