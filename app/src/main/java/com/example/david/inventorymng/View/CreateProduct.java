package com.example.david.inventorymng.View;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import com.example.david.inventorymng.R;
import java.util.Date;

public class CreateProduct extends AppCompatActivity {

    private InventoryMNG4App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView( R.layout.add_producto );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Añadir/Modificar producto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final EditText edNom = (EditText) this.findViewById( R.id.edNom );
        final EditText edCod = (EditText) this.findViewById( R.id.edCod );
        final EditText edNum = (EditText) this.findViewById( R.id.edNum );
        final EditText edDesc = (EditText) this.findViewById( R.id.edDesc );
        final EditText edProv = (EditText) this.findViewById( R.id.edProv );
        final EditText edAdd_date = (EditText) this.findViewById( R.id.edAdd_Date );
        final EditText edCad_date = (EditText) this.findViewById( R.id.edCad_Date );


        final Button btGuardar = (Button) this.findViewById( R.id.idBtnCrear );
        final Button btCancelar = (Button) this.findViewById( R.id.idBtnCancelar );


        final InventoryMNG4App app = (InventoryMNG4App) this.getApplication();

        //Transferir datos de una actividad a otra
        Intent datosEnviados = this.getIntent();
        final int pos = datosEnviados.getExtras().getInt( "pos" );

        String nombre = "", desc="", prov="", ad="", cd="";
        int cod = 1, numero = 0;


        if ( pos >= 0 ) {
            nombre = app.getItemList().get( pos ).getNombre();
            numero = app.getItemList().get( pos ).getNum();
            cod = app.getItemList().get( pos ).getCod();
            desc = app.getItemList().get( pos ).getDesc();
            prov = app.getItemList().get( pos ).getProv();
            ad = app.getItemList().get( pos ).getFechaEnt();
            cd = app.getItemList().get( pos ).getFechaCad();


            edNom.setText( nombre );
            edNom.setKeyListener(null);
            edAdd_date.setText( ad );
            edAdd_date.setKeyListener(null);
            edCad_date.setText( cd );
            edCad_date.setKeyListener(null);
            edCod.setText( Integer.toString(cod) );
            edCod.setKeyListener(null);
            edDesc.setText( desc );
            edNum.setText( Integer.toString(numero) );
            edProv.setText( prov );

        }else {


            edNom.setText(nombre);
            edCod.setText(Integer.toString(cod));
            edNum.setText(Integer.toString(numero));
            edDesc.setText(desc);
            edProv.setText(prov);
            edAdd_date.setText(ad);
            edCad_date.setText(cd);
        }

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
                final String ad = edAdd_date.getText().toString();
                final String cd = edCad_date.getText().toString();

                if ( pos >= 0 ) {

                    app.modifyProducto(pos,desc,num,prov);
                } else {
                    app.addProducto(nombre, cod, num, desc, prov, ad, cd);
                }

                CreateProduct.this.setResult( Activity.RESULT_OK );
                CreateProduct.this.finish();


            }
        });
        btGuardar.setEnabled( false );


        edNom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(  !app.getNoms( edNom.getText().toString() )  ) {
                    btGuardar.setEnabled(false);
                }else
                    btGuardar.setEnabled( edNom.getText().toString().trim().length() > 0 );
            }
        });

        edNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int num = 0;

                try {
                    num = Integer.parseInt( edCod.getText().toString() );
                } catch(NumberFormatException exc) {
                    Log.w( "CreateProduct", "edNum no puede ser convertido a número" );
                }

                btGuardar.setEnabled( num > 0 );
            }
        });


        edCod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }


            @Override
            public void afterTextChanged(Editable editable) {
                int cod = 0;

                try {
                    cod = Integer.parseInt( edCod.getText().toString() );
                } catch(NumberFormatException exc) {
                    Log.w( "CreateProduct", "edCod no puede ser convertido a número" );
                }

                btGuardar.setEnabled( cod > 0 );
            }
        });


        edDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }


            @Override
            public void afterTextChanged(Editable editable) {
                btGuardar.setEnabled( edDesc.getText().toString().trim().length() > 0 );
            }
        });


        edProv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }


            @Override
            public void afterTextChanged(Editable editable) {
                btGuardar.setEnabled( edProv.getText().toString().trim().length() > 0 );
            }
        });


        edAdd_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void afterTextChanged(Editable editable) {
                btGuardar.setEnabled( edAdd_date.getText().toString().trim().length() > 0 );
            }
        });


        edCad_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                btGuardar.setEnabled( edCad_date.getText().toString().trim().length() > 0 );
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

