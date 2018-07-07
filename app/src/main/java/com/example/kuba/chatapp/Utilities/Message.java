package com.example.kuba.chatapp.Utilities;

import java.util.Date;

/**
 * Created by Kuba on 05.07.2018.
 */

public class Message {

    private String Content, Sender, Receiver, SenderNickname;
    private Date SendDate;









    public Message(String Content, String Sender, String Receiver, String SenderNickname, Date SendDate)
    {
        this.Content=Content;
        this.Sender=Sender;
        this.Receiver=Receiver;
        this.SenderNickname=SenderNickname;
        this.SendDate=SendDate;

    }

    public Message() { }


    public String getContent(){return Content;}

    public void setContent(String Content) {
        this.Content = Content;
    }


    public String getSender(){return Sender;}

    public void setSender(String Sender) {
        this.Sender = Sender;
    }


    public String getSenderNickname(){return SenderNickname;}

    public void setSenderNickname(String SenderNickname) {
        this.SenderNickname = SenderNickname;
    }


    public Date getSendDate(){return SendDate;}

    public void setSendDate(Date SendDate) {
        this.SendDate = SendDate;
    }


    public String getReceiver() {return Receiver;}

    public void setReceiver(String Receiver) {
        this.Receiver = Receiver;
    }





}
