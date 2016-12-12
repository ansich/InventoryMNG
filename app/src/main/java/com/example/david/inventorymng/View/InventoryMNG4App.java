package com.example.david.inventorymng.View;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

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


    public void modifyProducto(int pos, String des, int num, String prv, String fechaEntrada, String fechaCad)
    {
        // Actualizar lista
        this.leerBD();
        Producto p = this.items.get(pos);
        int c = p.getCod();
        String d = Integer.toString(c);

        // Actualizar base de datos
        SQLiteDatabase db = this.getDB();
        try {
            db.beginTransaction();
            db.execSQL( "UPDATE producto SET desc = ?, num = ? , proveedor = ? , fechaEntrada = ? , fechaCad = ? WHERE cod = ?", new String[]{ des, Integer.toString(num), prv, fechaEntrada, fechaCad, d } );
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }

        // Modificar lista
        Producto prod = new Producto( p.getNombre(), p.getCod(), num, des, prv, fechaEntrada , fechaCad );
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

    public boolean getCods(String co){

        SQLiteDatabase db = this.getDB();
        Cursor c = db.rawQuery("SELECT cod FROM producto ", null);
        if(c.moveToFirst()){
            do{
                String column1 = c.getString(0);
                if( !co.equals(column1)  ){
                    return true;
                }
            }while(c.moveToNext());
        }
        c.close();

        return false;
    }

    public boolean getNoms(String nom){

        SQLiteDatabase db = this.getDB();
        Cursor c = db.rawQuery("SELECT nombre FROM producto ", null);
        if(c.moveToFirst()){
            do{
                String column1 = c.getString(0);
                if( !nom.equals(column1)  ){
                    return true;
                }
            }while(c.moveToNext());
        }
        c.close();

        return false;
    }

    public ArrayList<String> getTopNoms() {

        SQLiteDatabase db = this.getDB();
        Cursor c = db.rawQuery("SELECT nombre FROM producto ORDER by num desc limit 5", new String[] {});
        ArrayList<String> array = new ArrayList<String>();
        while (c.moveToNext()) {
            String uname = c.getString(0);
            array.add(uname);
        }
        return array;
    }

    public ArrayList<Double> getTopNums() {

        SQLiteDatabase db = this.getDB();
        Cursor c = db.rawQuery("SELECT num FROM producto ORDER by num desc limit 5", new String[] {});
        ArrayList<Double> array = new ArrayList<Double>();
        while (c.moveToNext()) {
            Double uname = Double.parseDouble(c.getString(0));
            array.add(uname);
        }
        return array;
    }

}