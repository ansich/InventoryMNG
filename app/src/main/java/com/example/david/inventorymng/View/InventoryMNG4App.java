package com.example.david.inventorymng.View;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.david.inventorymng.Core.Producto;
import com.example.david.inventorymng.Core.SqlIO;

import java.util.ArrayList;
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
                Item prod = new Item( cursor.getString( 0 ) );
                item.setNum( cursor.getInt( 1 ) );
                this.items.add( item );
            } while( cursor.moveToNext() );

            cursor.close();
        }

        return;
    }

    public void addItem(String nombre, int num)
    {
        SQLiteDatabase db = this.getDB();

        try {
            db.beginTransaction();
            // Actualizar la base de datos
            db.execSQL( "INSERT INTO compra(nombre, num) VALUES (?, ?)",
                    new String[]{ nombre, Integer.toString( num ) } );

            // Actualizar la lista
            Item item = new Item( nombre );
            item.setNum( num );
            this.items.add( item );
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }

        return;
    }

    public void modifyItem(int pos, String nombre, int num)
    {
        // Actualizar lista
        this.leerBD();

        // Modificar lista
        Item item = new Item( nombre );
        item.setNum( num );
        this.items.set( pos, item );

        // Actualizar base de datos
        SQLiteDatabase db = this.getDB();
        try {
            db.beginTransaction();
            db.execSQL( "DELETE FROM compra" );
            for(Item it: this.items) {
                db.execSQL( "INSERT INTO compra(nombre, num) VALUES(?, ?)",
                        new String[]{ it.getNombre(), Integer.toString( it.getNum() ) } );
            }
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }

        return;
    }

    public void removeItem(String n){

        //guardar la lista sin el. borrado
        SQLiteDatabase db = this.getDB();

        try {
            db.beginTransaction();

            //borrar todos los registros
            db.execSQL( "DELETE FROM compra WHERE nombre = n " );
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    /*public void removeItem(int i){

        //elimina de la lista
        this.items.remove( i );

        //guardar la lista sin el. borrado
        SQLiteDatabase db = this.getDB();

        try {
            db.beginTransaction();

            //borrar todos los registros
            db.execSQL( "DELETE FROM compra" );

            //guardar la lista act.
            for(Item it: this.items) {
                db.execSQL( "INSERT INTO compra(nombre, num) VALUES(?, ?)",
                        new String[]{ it.getNombre(), Integer.toString( it.getNum() ) } );
            }

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }*/
}
