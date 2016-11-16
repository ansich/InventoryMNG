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
    private int fechaEnt;
    private int fechaSal;
    private int fechaCad;

    public Producto(){

        nombre = "";
        cod = 0;
        num = 0;
        desc = "";
        prov = "";
        fechaEnt = 0;
        fechaSal = 0;
        fechaCad = 0;

    }

    public Producto(String nom, int co, String des, String prv, int fEnt, int fSal, int fCad ){

        nombre = nom;
        cod = co;
        desc = des;
        prov = prv;
        fechaEnt = fEnt;
        fechaSal = fSal;
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

    public int getFechaEnt(){
        return fechaEnt;
    }

    public void setFechaEnt(int f){
        this.fechaEnt = f;
    }

    public int getFechaSal(){
        return fechaSal;
    }

    public void setFechaSal(int f){
        this.fechaSal = f;
    }

    public int getFechaCad(){
        return fechaCad;
    }

    public void setFechaCad(int f){
        this.fechaCad = f;
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

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", cod=" + cod +
                ", num=" + num +
                ", desc='" + desc + '\'' +
                ", prov='" + prov + '\'' +
                ", fechaEnt=" + fechaEnt +
                ", fechaSal=" + fechaSal +
                ", fechaCad=" + fechaCad +
                '}';
    }

}