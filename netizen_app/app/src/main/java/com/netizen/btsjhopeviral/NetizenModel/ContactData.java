package com.netizen.btsjhopeviral.NetizenModel;

import java.io.Serializable;

public class ContactData implements Serializable {

    private String name;
    private String lastSeenState;
    private String imageuser;
    private long time;

    public ContactData(String name, String lastSeenState, String img, long tm) {
        this.name = name;
        this.lastSeenState = lastSeenState;
        this.imageuser = img;
        this.time = tm;
    }

    public String getName() {
        return name;
    }
    public long getTime() {
        return time;
    }

    public String getImageuser() {
        return imageuser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastSeenState() {
        return lastSeenState;
    }

    public void setLastSeenState(String lastSeenState) {
        this.lastSeenState = lastSeenState;
    }
}
