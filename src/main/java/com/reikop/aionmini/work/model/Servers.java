package com.reikop.aionmini.work.model;

public enum Servers {
    GUARDIAN(1, "가디언"),
    ARKAN(2, "아칸"),
    NEZAKAN(22, "네자칸"),
    LUMIEL(27, "루미엘"),
    BYZEL(24, "바이젤"),
    ISRAFEL(21, "이스라펠"),
    ZICKEL(23, "지켈"),
    KAISINEL(26, "카이시넬"),
    TRINIEL(25, "트리니엘");

    private final int server;
    private final String name;

    Servers(int server, String name) {
        this.server = server;
        this.name = name;
    }

    public int getServer() {
        return server;
    }

    public String getName() {
        return name;
    }

    public static Servers getServerValue(int value){
        switch (value){
            case 1 : return GUARDIAN;
            case 2 : return ARKAN;
            case 21 : return ISRAFEL;
            case 22 : return NEZAKAN;
            case 23 : return ZICKEL;
            case 24 : return BYZEL;
            case 25 : return TRINIEL;
            case 26 : return KAISINEL;
            case 27 : return LUMIEL;
            default: throw new NullPointerException();
        }
    }
}
