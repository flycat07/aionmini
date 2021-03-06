package com.reikop.aionmini.work.model;

import javax.persistence.*;

@Entity(name = "user")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "server", "userid" }) })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private Servers server;
    private int userid;
    private int level;
    private String serverName;
    private String image;
    private String raceName;
    private String className;
    private String charname;
    private String guildName;
    private int guildId;

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public int getGuildId() {
        return guildId;
    }

    public void setGuildId(int guildId) {
        this.guildId = guildId;
    }

    public String getRaceName() {
        return raceName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getCharname() {
        return charname;
    }

    public void setCharname(String charname) {
        this.charname = charname;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
