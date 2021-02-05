package com.reikop.aionmini.work.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private Servers server;
    private int userid;
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date regdate;

    @PrePersist
    void prePersist(){
        this.regdate = new Date();
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Servers getServer() {
        return server;
    }

    public void setServer(Servers server) {
        this.server = server;
    }
}
