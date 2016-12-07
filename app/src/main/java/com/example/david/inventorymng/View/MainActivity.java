package com.example.david.inventorymng.View;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.WindowDecorActionBar;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.david.inventorymng.Core.Producto;
import com.example.david.inventorymng.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    protected static final int CODIGO_EDITAR_PRODUCTO = 100;
    protected static final int CODIGO_AÑADIR_PRODUCTO = 102;
    protected static final int CODIGO_EST = 104;


    private ArrayList<Producto> items;
    private ArrayAdapter<Producto> adaptadorProducto;
    private InventoryMNG4App app;
    private CreateProduct crear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.app = (InventoryMNG4App) this.getApplication();

        final ListView lista = (ListView) this.findViewById( R.id.lvToDoList );

        // Lista
        this.adaptadorProducto = new ArrayAdapter<>(
                this,
                android.R.layout.simple_selectable_list_item,
                app.getItemList() );
        lista.setAdapter( this.adaptadorProducto );

        lista.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Object listItem = lista.getItemAtPosition(position);
                Intent subActividad = new Intent( MainActivity.this, CreateProduct.class );

                subActividad.putExtra( "pos", 0 );
                MainActivity.this.startActivityForResult( subActividad, CODIGO_EDITAR_PRODUCTO );
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {


                if ( pos >= 0 ) {


                    final AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);


                    alerta.setTitle("Eliminar");
                    alerta.setMessage("¿Desea elminar el producto?");
                    alerta.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {


                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            app.removeItem( pos );
                            MainActivity.this.adaptadorProducto.notifyDataSetChanged();
                        }
                    });


                    alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });


                    alerta.create().show();
                }
                return false;
            }
        });





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent subActividad = new Intent( MainActivity.this, CreateProduct.class );


                subActividad.putExtra( "pos", -1 );
                MainActivity.this.startActivityForResult( subActividad, CODIGO_AÑADIR_PRODUCTO );


                //Snackbar.make(view, "Nuevo producto", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });
    }

    //MULTIACTIVIDADES
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CODIGO_AÑADIR_PRODUCTO && resultCode == Activity.RESULT_OK )
        {
            this.adaptadorProducto.notifyDataSetChanged();
        }


        if (requestCode == CODIGO_EDITAR_PRODUCTO && resultCode == Activity.RESULT_OK )
        {
            this.adaptadorProducto.notifyDataSetChanged();
        }

        if (requestCode == CODIGO_EST && resultCode == Activity.RESULT_OK )
        {

        }

        return;
    }

    //MENU DE OPCIONES CON BUSQUEDA
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean toret = false;

        switch( item.getItemId() ) {
            case R.id.item_añadirproducto:
                Intent subActividad = new Intent( MainActivity.this, CreateProduct.class );
                subActividad.putExtra( "pos", -1 );
                MainActivity.this.startActivityForResult( subActividad, CODIGO_AÑADIR_PRODUCTO );
                toret = true;
                break;
            case R.id.item_estadisticas:
                Intent subActividad2 = new Intent( MainActivity.this, Plot.class );
                MainActivity.this.startActivityForResult( subActividad2, CODIGO_EST );
                toret = true;
                break;
        }

        return toret;
    }



    /*
//La app pasa a segundo plano, y quizás sea elminada
public void onPause() {


   super.onPause();


   SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
   SharedPreferences.Editor saver = prefs.edit();


   saver.putStringSet("items", new HashSet<String>(this.items));


   StringBuilder builder = new StringBuilder();
   for (String item : this.items) {
       builder.append(item);
       builder.append(',');
   }


   saver.putString("items", builder.toString());


   saver.apply();
}
*/


/*
//La app vuelve a estar en ejecución
public void onResume(){
   super.onResume();


   SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
   Set<String> itemsnuevo = prefs.getStringSet("items", new HashSet<String>(this.items));
   this.items.clear();
   this.items.addAll(itemsnuevo);
   this.itemsAdapter.notifyDataSetChanged();


   String codedItems = prefs.getString("items", "");
   String [] items = codedItems.split(" , ");


   this.items.clear();
   for(int i = 0; i < (items.length - 1); ++i){
       this.items.add(items[i]);
   }


   this.itemsAdapter.notifyDataSetChanged();


}
*/
}
