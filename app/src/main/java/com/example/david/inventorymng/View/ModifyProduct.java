package com.example.david.inventorymng.View;

/**
 * Created by dani_ on 14/12/2016.
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david.inventorymng.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ModifyProduct extends AppCompatActivity {

    private InventoryMNG4App app;
    private MainActivity main;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView( R.layout.modify_producto );

        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);

        //getSupportActionBar().setTitle("Producto: ");
        //getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final EditText edNum = (EditText) this.findViewById( R.id.edNum );
        final EditText edDesc = (EditText) this.findViewById( R.id.edDesc );
        final EditText edProv = (EditText) this.findViewById( R.id.edProv );
        final EditText edAdd_date = (EditText) this.findViewById( R.id.edAdd_Date );
        final EditText edCad_date = (EditText) this.findViewById( R.id.edCad_Date );


        final Button btGuardar = (Button) this.findViewById( R.id.idBtnGuarda );
        final Button btCancelar = (Button) this.findViewById( R.id.idBtnCancel );


        final InventoryMNG4App app = (InventoryMNG4App) this.getApplication();

        //Transferir datos de una actividad a otra
        Intent datosEnviados = this.getIntent();
        final int pos = datosEnviados.getExtras().getInt( "pos" );

        String nombre = "", desc="", prov="", ad="", cd="";
        int  numero = 0;


        numero = app.getProducto(pos).getNum();
        desc = app.getProducto(pos).getDesc();
        prov = app.getProducto(pos).getProv();
        ad = app.getProducto(pos).getFechaEnt();
        cd = app.getProducto(pos).getFechaCad();


        edAdd_date.setText( ad );
        edAdd_date.setKeyListener(null);
        edCad_date.setText( cd );
        edCad_date.setKeyListener(null);
        edDesc.setText( desc );
        edNum.setText( Integer.toString(numero) );
        edProv.setText( prov );



        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModifyProduct.this.setResult( Activity.RESULT_CANCELED );
                ModifyProduct.this.finish();
            }
        });


        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final String nombre = app.getProducto(pos).getNombre();
                final int num = Integer.parseInt(edNum.getText().toString());
                final String desc = edDesc.getText().toString();
                final String prov = edProv.getText().toString();
                final String ad = edAdd_date.getText().toString();
                final String cd = edCad_date.getText().toString();

                app.modifyProducto(pos,desc,num,prov,ad,cd);
                app.getItemList().notifyAll();

                ModifyProduct.this.setResult( Activity.RESULT_OK );
                ModifyProduct.this.finish();


            }
        });
        btGuardar.setEnabled( false );


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
                    num = Integer.parseInt( edNum.getText().toString() );
                } catch(NumberFormatException exc) {
                    Log.w( "CreateProduct", "edNum no puede ser convertido a número" );
                }

                if( num > -1){
                    btGuardar.setEnabled(true);
                }
                else{
                    btGuardar.setEnabled(false);
                    Toast.makeText(ModifyProduct.this, "El campo 'Número' no puede estar vacío", Toast.LENGTH_SHORT).show();
                }
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
                if(edDesc.getText().toString().trim().length() > 0){
                    btGuardar.setEnabled(true);
                }
                else{
                    btGuardar.setEnabled(false);
                    Toast.makeText(ModifyProduct.this, "El campo 'Descripción' no puede estar vacío", Toast.LENGTH_SHORT).show();
                }
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
                if(edProv.getText().toString().trim().length() > 0){
                    btGuardar.setEnabled(true);
                }
                else{
                    btGuardar.setEnabled(false);
                    Toast.makeText(ModifyProduct.this, "El campo 'Proveedor' no puede estar vacío", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //No permite que se muestre el teclado
        edAdd_date.setInputType(InputType.TYPE_NULL);
        //Selecciona la fecha de entrada
        edAdd_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dlg = new DatePickerDialog(ModifyProduct.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayofMonth) {
                        edAdd_date.setText(view.getDayOfMonth() + "/" + (view.getMonth() + 1)  + "/" + view.getYear());
                    }
                }, mYear, mMonth, mDay);

                dlg.setTitle("Escoge la fecha de entrada");
                dlg.show();
            }
        });

        edAdd_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btGuardar.setEnabled( true );
            }
        });

        //No permite que se muestre el teclado
        edCad_date.setInputType(InputType.TYPE_NULL);
        //Selecciona la fecha de caducidad
        edCad_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentDate=Calendar.getInstance();
                int mYear=mcurrentDate.get(Calendar.YEAR);
                int mMonth=mcurrentDate.get(Calendar.MONTH);
                int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dlg2 = new DatePickerDialog(ModifyProduct.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayofMonth) {
                        edCad_date.setText(view.getDayOfMonth() + "/" + (view.getMonth() + 1) + "/" + view.getYear());
                    }
                },mYear,mMonth,mDay);

                dlg2.setTitle("Escoge la fecha de caducidad");
                dlg2.show();}
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

                btGuardar.setEnabled( true );
            }

        });

    }

    //Controla el botón de atrás de la toolbar, para que vuelva al MainActivity
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }

}