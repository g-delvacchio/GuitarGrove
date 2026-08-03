package model.bean;

import java.time.LocalDateTime;

public class Acquisto {
    private int orderId;
    private int userId;
    private double totale;
    private double spedizione;
    private String stato;
    private LocalDateTime dataAcquisto;
    private String pagamento;

    public Acquisto() {}

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public double getTotale() { return totale; }
    public void setTotale(double totale) { this.totale = totale; }

    public double getSpedizione() { return spedizione; }
    public void setSpedizione(double spedizione) { this.spedizione = spedizione; }

    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }

    public LocalDateTime getDataAcquisto() { return dataAcquisto; }
    public void setDataAcquisto(LocalDateTime dataAcquisto) { this.dataAcquisto = dataAcquisto; }

    public String getPagamento() { return pagamento; }
    public void setPagamento(String pagamento) { this.pagamento = pagamento; }

}