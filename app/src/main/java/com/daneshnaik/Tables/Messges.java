package com.daneshnaik.Tables;



public class Messges {
    private  int feeling=-1;
    private  String MessageId,message,sendId;
    private long timestamp;

    public int getFeeling() {
        return feeling;
    }

    public void setFeeling(int feeling) {
        this.feeling = feeling;
    }





    public Messges(String message, String sendId, long timestamp) {
        this.message = message;
        this.sendId = sendId;
        this.timestamp = timestamp;
    }

    public Messges() {

    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
