package br.com.atps.anhanguera.dispositivosmoveis.listadesejos.modelo;

import java.io.Serializable;

/**
 * Created by Core i5 on 18/05/2015.
 */
public class Desejo implements Serializable{

    private long id;
    private String produto;
    private String categoria;
    private String lojas;
    private double precoMinimo;
    private double precoMaximo;

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return categoria + " - " + produto + "\n" +
                "\t( R$ " +  (precoMinimo + "").replace(".",",") + " - R$ " + (precoMaximo + "").replace(".",",") + ")";
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getLojas() {
        return lojas;
    }

    public void setLojas(String lojas) {
        this.lojas = lojas;
    }

    public double getPrecoMinimo() {
        return precoMinimo;
    }

    public void setPrecoMinimo(double precoMinimo) {
        this.precoMinimo = precoMinimo;
    }

    public double getPrecoMaximo() {
        return precoMaximo;
    }

    public void setPrecoMaximo(double precoMaximo) {
        this.precoMaximo = precoMaximo;
    }
}
