package com.example.kuba.chatapp.Utilities;

import java.util.Date;

/**
 * Created by Kuba on 11.07.2018.
 */

public class User {

    private String uid, nickname;
    private Date joinDate;

    public User(String uid, String nickname, Date joinDate){
        this.joinDate = joinDate;
        this.nickname = nickname;
        this.uid = uid;

    }




    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }





}
