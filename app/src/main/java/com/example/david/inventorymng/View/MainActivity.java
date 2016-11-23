package com.example.david.inventorymng.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.david.inventorymng.Core.Producto;
import com.example.david.inventorymng.R;

public class MainActivity extends AppCompatActivity {


    protected static final int CODIGO_EDITAR_PRODUCTO = 100;
    protected static final int CODIGO_AÑADIR_PRODUCTO = 102;


    private ArrayAdapter<Producto> adaptadorProducto;
    private InventoryMNG4App app;
    private CreateProduct crear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.app = (InventoryMNG4App) this.getApplication();


        ListView lista = (ListView) this.findViewById( R.id.lvToDoList );


        this.registerForContextMenu( lista );


        // Lista
        this.adaptadorProducto = new ArrayAdapter<>(
                this,
                android.R.layout.simple_selectable_list_item,
                app.getItemList() );
        lista.setAdapter( this.adaptadorProducto );


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent subActividad = new Intent( MainActivity.this, CreateProduct.class );


                subActividad.putExtra( "pos", -1 );
                MainActivity.this.startActivityForResult( subActividad, CODIGO_AÑADIR_PRODUCTO );


                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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


        return;
    }




    //MENU CONTEXTUAL
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo cmi)
    {
        if ( view.getId() == R.id.lvToDoList )
        {
            this.getMenuInflater().inflate( R.menu.contextual_menu, contextMenu );
            contextMenu.setHeaderTitle( R.string.app_name );
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem menuItem)
    {
        boolean toret = false;


        switch( menuItem.getItemId() ) {
            case R.id.item_eliminarproducto:
                int pos = ( (AdapterView.AdapterContextMenuInfo)menuItem.getMenuInfo() ).position;
                this.app.removeItem(pos);
                toret = true;
                break;
            case R.id.item_modificarproducto:
                int pos2 = ( (AdapterView.AdapterContextMenuInfo)menuItem.getMenuInfo() ).position;
                this.app.modifyProducto(pos2,);
                toret = true;
                break;
        }


        return toret;
    }




    //MENU DE OPCIONES
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        super.onCreateOptionsMenu( menu );


        this.getMenuInflater().inflate( R.menu.menu_main, menu );
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        boolean toret = false;


        switch( item.getItemId() ) {
            case R.id.item_ajustes:
                //this.crear
                toret = true;
                break;
            case R.id.item_añadirproducto:
                //this.crear
                toret = true;
                break;
            case R.id.item_estadisticas:
                //this.crear
                toret = true;
                break;
            case R.id.item_barrabusqueda:
                //this.crear
                toret = true;
                break;
        }


        return toret;
    }
}
