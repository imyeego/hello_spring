package com.imyeego.pojo;

public class HeartBean {
    /**
     * servertime : 1569308976193
     * state : 1
     * message : 成功
     */

    private long servertime;
    private int state;
    private String message;
    /**
     * data : null
     */

    private Object data;
    private String vername;//版本名称
    private String vercode;//版本号
    private String examplanid;//考试任务id
    private String examplanname;//考试任务名称

    public String getVername() {
        return vername;
    }

    public void setVername(String vername) {
        this.vername = vername;
    }

    public String getVercode() {
        return vercode;
    }

    public void setVercode(String vercode) {
        this.vercode = vercode;
    }

    public String getExamplanid() {
        return examplanid;
    }

    public void setExamplanid(String examplanid) {
        this.examplanid = examplanid;
    }

    public String getExamplanname() {
        return examplanname;
    }

    public void setExamplanname(String examplanname) {
        this.examplanname = examplanname;
    }

    public long getServertime() {
        return servertime;
    }

    public void setServertime(long servertime) {
        this.servertime = servertime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
