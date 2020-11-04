package fh.campus.asd.usermanagement.models;


public class DataManagerObject {
    private long tsLastAccess;
    DataManagerObject(){
        tsLastAccess = System.currentTimeMillis();
    }
}
