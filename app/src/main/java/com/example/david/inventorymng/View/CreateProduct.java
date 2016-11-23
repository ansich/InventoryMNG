package com.example.david.inventorymng.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import com.example.david.inventorymng.R;

import java.util.Date;

/**
 * Created by David on 22/11/2016.
 */

public class CreateProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.add_producto );

        final EditText edNom = (EditText) this.findViewById( R.id.edNom );
        final EditText edCod = (EditText) this.findViewById( R.id.edCod );
        final EditText edNum = (EditText) this.findViewById( R.id.edNum );
        final EditText edDesc = (EditText) this.findViewById( R.id.edDesc );
        final EditText edProv = (EditText) this.findViewById( R.id.edProv );
        final EditText edAdd_date = (EditText) this.findViewById( R.id.edAdd_date );
        final EditText edCad_date = (EditText) this.findViewById( R.id.edCad_date );
        final Button btGuardar = (Button) this.findViewById( R.id.idBtnCrear );
        final Button btCancelar = (Button) this.findViewById( R.id.idBtnCancelar );
        final InventoryMNG4App app = (InventoryMNG4App) this.getApplication();

        Intent datosEnviados = this.getIntent();
        final int pos = datosEnviados.getExtras().getInt( "pos" );
        String nombre = "", desc="", prov="";
        int cod=1, np = 1;
        Date ad = new Date();
        Date cd = new Date();

//        if ( pos >= 0 ) {
//            nombre = app.getItemList().get( pos ).getNombre();
//            desc = app.getItemList().get( pos ).getDesc();
//            prov = app.getItemList().get( pos ).getProv();
//            ad = app.getItemList().get( pos ).getProv();
//            cantidad = app.getItemList().get( pos ).getNum();
//        }

        edNom.setText( nombre );
        edCod.setText( Integer.toString(cod) );
        edDesc.setText( desc );
        edProv.setText( prov );
        edAdd_date.setText( ad.toString() );
        edCad_date.setText( cd.toString() );


        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateProduct.this.setResult( Activity.RESULT_CANCELED );
                CreateProduct.this.finish();
            }
        });

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nombre = edNom.getText().toString();
                final int cod = Integer.parseInt(edCod.getText().toString());
                final int num = Integer.parseInt(edNum.getText().toString());
                final String desc = edDesc.getText().toString();
                final String prov = edProv.getText().toString();
                final Date ad = parseFecha(edAdd_date.getText().toString());
                final Date cd = parseFecha(edCad_date.getText().toString());


                app.addProducto(nombre, cod, num, desc, prov, ad, cd);


                CreateProduct.this.setResult( Activity.RESULT_OK );
                CreateProduct.this.finish();
            }
        });
        btGuardar.setEnabled( false );

        edNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                btGuardar.setEnabled( edNombre.getText().toString().trim().length() > 0 );
            }
        });

        edCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int cantidad = 0;

                try {
                    cantidad = Integer.parseInt( edCantidad.getText().toString() );
                } catch(NumberFormatException exc) {
                    Log.w( "ItemEditionActivity", "edCantidad no puede ser convertido a número" );
                }

                btGuardar.setEnabled( cantidad > 0 );
            }
        });
    }

    /**
     * Permite convertir un String en fecha (Date).
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


}
