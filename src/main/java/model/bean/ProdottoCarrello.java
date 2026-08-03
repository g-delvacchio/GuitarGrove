package model.bean;

public class ProdottoCarrello {
    private int userId;
    private int productId;
    private int quantita;

    public ProdottoCarrello() {}

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }
}