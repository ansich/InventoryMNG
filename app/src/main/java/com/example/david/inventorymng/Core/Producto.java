package com.example.david.inventorymng.Core;


/**
 * Created by david on 28/10/2016.
 * Atributos de los productos:
 * Nombre, código (clave primaria), descripción breve, proveedor, fecha de entrada, fecha de caducidad (si la tiene), Cantidad, fecha de salida
 */

public class Producto {

    //atributos:
    private String nombre;
    private int cod;
    private int num;
    private String desc;
    private String prov;
    private String fechaEnt;
    private String fechaCad;

    public Producto(){

        nombre = "";
        cod = 0;
        num = 0;
        desc = "";
        prov = "";
        fechaEnt = null;
        fechaCad = null;

    }

    public Producto(String nom, int co, int n, String des, String prv, String fEnt, String fCad ){

        nombre = nom;
        cod = co;
        num = n;
        desc = des;
        prov = prv;
        fechaEnt = fEnt;
        fechaCad = fCad;

    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getCod(){
        return cod;
    }

    public void setCod(int c){
        this.cod = c;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String n){
        this.nombre = n;
    }

    public String getDesc(){
        return desc;
    }

    public void setDesc(String d){
        this.desc = d;
    }

    public String getProv(){
        return prov;
    }

    public void setProv(String p){
        this.prov = p;
    }

    public String getFechaEnt() {
        return fechaEnt;
    }

    public void setFechaEnt(String fechaEnt) {
        this.fechaEnt = fechaEnt;
    }

    public String getFechaCad() {
        return fechaCad;
    }

    public void setFechaCad(String fechaCad) {
        this.fechaCad = fechaCad;
    }

    @Override
    public String toString() {
        return  "" + nombre +
                "\nCódigo: " + cod +
                "\nCantidad: " + num +
                "\nDescripción = " + desc ;
    }
}