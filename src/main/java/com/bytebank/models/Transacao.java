package com.bytebank.models;

public class Transacao {
    private int id;
    private double valor;
    private String tipo;
    private String data;

    public Transacao(String tipo ,int id, double valor, String data ) {
        this.id = id;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
