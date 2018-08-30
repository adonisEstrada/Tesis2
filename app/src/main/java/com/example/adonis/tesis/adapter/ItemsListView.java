package com.example.adonis.tesis.adapter;

public class ItemsListView {
    private String titulo1;
    private String titulo2;
    private String titulo3;


    public ItemsListView(String titulo1, String titulo2, String titulo3) {
        this.titulo1 = titulo1;
        this.titulo2 = titulo2;
        this.titulo3 = titulo3;
    }

    public ItemsListView(String titulo1) {
        this.titulo1 = titulo1;
    }

    public ItemsListView(String titulo1, String titulo2) {
        this.titulo1 = titulo1;
        this.titulo2 = titulo2;
    }

    public ItemsListView() {
    }

    public String getTitulo1() {
        return titulo1;
    }

    public void setTitulo1(String titulo1) {
        this.titulo1 = titulo1;
    }

    public String getTitulo2() {
        return titulo2;
    }

    public void setTitulo2(String titulo2) {
        this.titulo2 = titulo2;
    }

    public String getTitulo3() {
        return titulo3;
    }

    public void setTitulo3(String titulo3) {
        this.titulo3 = titulo3;
    }

}
