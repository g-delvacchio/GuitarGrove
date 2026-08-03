package model.bean;

public class Indirizzo {
    private int userId;
    private String paese;
    private String citta;
    private String cap;
    private String via;
    private String civico;

    public Indirizzo() {}

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getPaese() { return paese; }
    public void setPaese(String paese) { this.paese = paese; }

    public String getCitta() { return citta; }
    public void setCitta(String citta) { this.citta = citta; }

    public String getCap() { return cap; }
    public void setCap(String cap) { this.cap = cap; }

    public String getVia() { return via; }
    public void setVia(String via) { this.via = via; }

    public String getCivico() { return civico; }
    public void setCivico(String civico) { this.civico = civico; }
}