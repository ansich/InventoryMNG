package com.example.david.inventorymng.Core;

/**
 * Created by David on 16/11/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlIO extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Inventario";
    private static final int DATABASE_VERSION = 1;

    public SqlIO(Context context)
    {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try {
            db.beginTransaction();
            db.execSQL( "CREATE TABLE IF NOT EXISTS producto( "
                    + "nombre string(50) UNIQUE"
                    + "cod int PRIMARY KEY"
                    + "desc string(255) NOT NULL"
                    + "proveedor string NOT NULL"
                    + "fechaEntrada date NOT NULL "
                    + "fechaSalida date NOT NULL "
                    + "fechaCaducidad date NOT NULL)");
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i(  "InventoryMNG.SqlIo",
                "Actualizando BBDD de version " + oldVersion + " a la " + newVersion );

        try {
            db.beginTransaction();
            db.execSQL( "DROP TABLE IF EXISTS producto");
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        this.onCreate( db );
    }
}