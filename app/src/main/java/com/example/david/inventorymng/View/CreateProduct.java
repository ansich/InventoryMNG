package com.example.david.inventorymng.View;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import com.example.david.inventorymng.R;

import java.util.Calendar;
import java.util.Date;
import com.example.david.inventorymng.R;

import static android.R.attr.y;

public class CreateProduct extends AppCompatActivity {

    private InventoryMNG4App app;
    private MainActivity main;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView( R.layout.add_producto );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setTitle("Añadir/Modificar Producto");
        //getSupportActionBar().setDisplayShowTitleEnabled(true);
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


        edNom.setText(nombre);
        edCod.setText(Integer.toString(cod));
        edNum.setText(Integer.toString(numero));
        edDesc.setText(desc);
        edProv.setText(prov);
        edAdd_date.setText(ad);
        edCad_date.setText(cd);

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

                app.addProducto(nombre, cod, num, desc, prov, ad, cd);

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
                    Toast.makeText(app.getApplicationContext(),
                            "Introduzca un nombre que no esté repetido", Toast.LENGTH_SHORT).show();
                    btGuardar.setEnabled(false);
                }else
                    btGuardar.setEnabled( edNom.getText().toString().trim().length() > 0
                            && app.getNoms(edNom.getText().toString())
                            && app.getCods(edCod.getText().toString()));
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
                    num = Integer.parseInt( edNum.getText().toString() );
                } catch(NumberFormatException exc) {
                    Log.w( "CreateProduct", "edNum no puede ser convertido a número" );
                }
                btGuardar.setEnabled( num > -1
                        && app.getNoms(edNom.getText().toString())
                        && app.getCods(edCod.getText().toString()));
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

                if(  !app.getCods( edCod.getText().toString() )  ) {
                    Toast.makeText(app.getApplicationContext(),
                            "Introduzca un codigo que no esté repetido", Toast.LENGTH_SHORT).show();
                    btGuardar.setEnabled(false);
                }else
                btGuardar.setEnabled( cod > 0
                        && app.getNoms(edNom.getText().toString())
                        && app.getCods(edCod.getText().toString()));
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
                btGuardar.setEnabled( edDesc.getText().toString().trim().length() > 0
                        && app.getNoms(edNom.getText().toString())
                        && app.getCods(edCod.getText().toString()));
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
                btGuardar.setEnabled( edProv.getText().toString().trim().length() > 0
                        && app.getNoms(edNom.getText().toString())
                        && app.getCods(edCod.getText().toString()));
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


                DatePickerDialog dlg = new DatePickerDialog(CreateProduct.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayofMonth) {
                        edAdd_date.setText(view.getDayOfMonth() + "/" + (view.getMonth() + 1) + "/" + view.getYear());
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


                DatePickerDialog dlg2 = new DatePickerDialog(CreateProduct.this, new DatePickerDialog.OnDateSetListener() {
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