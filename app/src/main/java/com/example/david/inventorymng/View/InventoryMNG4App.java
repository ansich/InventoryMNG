package com.example.david.inventorymng.View;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.david.inventorymng.Core.Producto;
import com.example.david.inventorymng.Core.SqlIO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class InventoryMNG4App extends Application {
    private SqlIO db;
    private List<Producto> items;
    private CreateProduct crear;

    @Override
    public void onCreate()
    {
        this.items = new ArrayList<>();
        this.db = new SqlIO( this );
    }

    public SQLiteDatabase getDB()
    {
        return this.db.getWritableDatabase();
    } //Forma mas gen. de ref bd

    public List<Producto> getItemList()
    {
        this.leerBD();
        return this.items;
    }

    private void leerBD()
    {
        SQLiteDatabase db = this.db.getReadableDatabase();
        this.items.clear();

        Cursor cursor = db.rawQuery( "SELECT * FROM producto", null );

        if ( cursor.moveToFirst() ) {
            do {
                Producto prod = new Producto( cursor.getString( 0 ), cursor.getInt( 1 ), cursor.getInt( 2 ), cursor.getString( 3 ),
                        cursor.getString( 4 ),cursor.getString(5) ,cursor.getString(6) );
                this.items.add( prod );
            } while( cursor.moveToNext() );


            cursor.close();
        }

        return;
    }


    public void addProducto(String nom, int co, int num, String des, String prv, String fEnt, String fSal )
    {
        SQLiteDatabase db = this.getDB();

        try {
            db.beginTransaction();
            // Actualizar la base de datos
            db.execSQL( "INSERT INTO producto(nombre, cod, num, desc, proveedor, fechaEntrada, fechaCad) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    new String[]{ nom, Integer.toString( co ), Integer.toString( num ), des, prv, fEnt, fSal } );


            // Actualizar la lista
            Producto prod = new Producto( nom, co, num, des, prv, fEnt, fSal );
            this.items.add( prod );
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }

        return;
    }


    public void modifyProducto(int pos, String des, int num, String prv)
    {
        // Actualizar lista
        this.leerBD();
        Producto p = this.items.get(pos);
        int c = p.getCod();
        int nu = p.getNum();
        String d = Integer.toString(c);
        String n = Integer.toString(nu);

        // Actualizar base de datos
        SQLiteDatabase db = this.getDB();
        try {
            db.beginTransaction();
            db.execSQL( "UPDATE producto SET desc = ?, num = ? , proveedor = ? WHERE cod = ?", new String[]{ des, n, prv, d } );
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }

        // Modificar lista
        Producto prod = new Producto( p.getNombre(), p.getCod(), num, des, prv, p.getFechaEnt(), p.getFechaCad() );
        this.items.set( pos, prod );

        return;
    }


    public void removeItem(int pos){


        //guardar la lista sin el. borrado
        SQLiteDatabase db = this.getDB();
        Producto p = this.items.get(pos);
        int c = p.getCod();
        String d = Integer.toString(c);

        try {
            db.beginTransaction();
            db.execSQL( "DELETE FROM producto WHERE cod = ? ", new String[]{ d } );
            this.items.remove( p );
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }


    /**
     * Permite convertir un String en fecha (Date)..
     * @param fecha Cadena de fecha dd/MM/yyyy
     * @return Objeto Date
     */
    public static Date parseFecha(String fecha)
    {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        }
        catch (ParseException ex)
        {
            System.out.println(ex);
        }
        return fechaDate;
    }

    public void getCods(){

        SQLiteDatabase db = this.getDB();
        Cursor c = db.rawQuery("SELECT nombre, cod FROM producto ", null);
        if(c.moveToFirst()){
            do{
                String column1 = c.getString(0);
                String column2 = c.getString(1);
            }while(c.moveToNext());
        }
        c.close();
        db.close();
    }

}