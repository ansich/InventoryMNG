package com.example.david.inventorymng.Core;

import java.util.Date;

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
    private Date fechaEnt;
    private Date fechaCad;

    public Producto(){

        nombre = "";
        cod = 0;
        num = 0;
        desc = "";
        prov = "";
        fechaEnt = null;
        fechaCad = null;

    }

    public Producto(String nom, int co, int n, String des, String prv, Date fEnt, Date fCad ){

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

    public Date getFechaCad() {
        return fechaCad;
    }

    public void setFechaCad(Date fechaCad) {
        this.fechaCad = fechaCad;
    }

    public Date getFechaEnt() {
        return fechaEnt;
    }

    public void setFechaEnt(Date fechaEnt) {
        this.fechaEnt = fechaEnt;
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
                ", fechaCad=" + fechaCad +
                '}';
    }
}