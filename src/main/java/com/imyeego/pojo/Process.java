package com.imyeego.pojo;

import java.util.List;

public class Process {
    private String roomsession;
    private List<ProcessInfo> sessionData;

    public String getRoomsession() {
        return roomsession;
    }

    public void setRoomsession(String roomsession) {
        this.roomsession = roomsession;
    }

    public List<ProcessInfo> getSessionData() {
        return sessionData;
    }

    public void setSessionData(List<ProcessInfo> sessionData) {
        this.sessionData = sessionData;
    }
}
