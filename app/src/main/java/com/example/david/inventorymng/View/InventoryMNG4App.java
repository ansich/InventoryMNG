package com.example.david.inventorymng.View;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.david.inventorymng.Core.Producto;
import com.example.david.inventorymng.Core.SqlIO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by David on 16/11/2016.
 */

public class InventoryMNG4App extends Application {
    private SqlIO db;
    private List<Producto> items;

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
                        cursor.getString( 4 ),cursor.getString( 5 ), cursor.getInt( 6 ) );
                this.items.add( prod );
            } while( cursor.moveToNext() );

            cursor.close();
        }

        return;
    }

    public void addProducto(String nom, int co, int num, String des, String prv, Date fEnt, Date fSal )
    {
        SQLiteDatabase db = this.getDB();

        try {
            db.beginTransaction();
            // Actualizar la base de datos
            db.execSQL( "INSERT INTO producto(nom, co, num, des, prv, fEnt, fSal) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    new String[]{ nom, Integer.toString( co ), Integer.toString( num ), des, prv, Integer.toString(fEnt), Integer.toString(fSal) } );

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

        // Modificar lista
        Producto prod = new Producto( p.getNombre(), p.getCod(), num, des, prv, p.getFechaEnt(), p.getFechaSal() );
        this.items.set( pos, prod );

        // Actualizar base de datos
        SQLiteDatabase db = this.getDB();
        try {
            db.beginTransaction();
            db.execSQL( "UPDATE producto SET des = ?, num = ? , prv = ? WHERE cod = c" );
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }

        return;
    }

    public void removeItem(int pos){

        //guardar la lista sin el. borrado
        SQLiteDatabase db = this.getDB();
        Producto p = this.items.get(pos);
        int c = p.getCod();

        try {
            db.beginTransaction();
            db.execSQL( "DELETE FROM compra WHERE cod = c " );
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

}
