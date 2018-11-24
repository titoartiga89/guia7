package org.dev4u.hv.guia7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import modelo.AdaptadorCategoria;
import modelo.AdaptadorProducto;
import modelo.Categoria;
import modelo.DB;
import modelo.Producto;

public class ProductosActivity extends AppCompatActivity {

    private DB db;
    private AdaptadorProducto adaptadorProducto;
    private ListView listView;
    private TextView lblId_Cat;
    private EditText txtNombreProducto,txtDescripcionProducto;
    private Button btnGuardar,btnEliminar;
    //lista de datos (categoria)
    private ArrayList<Producto> lstProductos;
    //sirve para manejar la eliminacion
    private Producto producto_temp = null;
    private String idCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        Intent intent = getIntent();
        idCategoria = intent.getStringExtra("idCategoria");

        //inicializando los controles
        lblId_Cat               = (TextView) findViewById(R.id.lblId_pro_main);
        txtNombreProducto        = (EditText) findViewById(R.id.txtProducto);
        txtDescripcionProducto = (EditText) findViewById(R.id.txtDescripcion);
        btnGuardar              = (Button)   findViewById(R.id.btnGuardar);
        btnEliminar             = (Button)   findViewById(R.id.btnEliminar);
        listView                = (ListView) findViewById(R.id.lstProductos);
        //inicializando lista y db
        db                      = new DB(this);
        lstProductos            = db.getArrayProducto(
                db.getCursorProducto(idCategoria)
        );
        if(lstProductos==null)//si no hay datos
            lstProductos = new ArrayList<>();
        adaptadorProducto      = new AdaptadorProducto(this,lstProductos);
        listView.setAdapter(adaptadorProducto);
        //listeners
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seleccionar(lstProductos.get(position));
            }
        });
        //limpiando


        limpiar();
    }


    private void guardar(){
        Producto producto = new Producto(lblId_Cat.getText().toString(),txtNombreProducto.getText().toString(),
               txtDescripcionProducto.getText().toString(),idCategoria );
        producto_temp=null;
        if(db.guardar_O_ActualizarProducto(producto)){
            Toast.makeText(this,"Se guardo producto",Toast.LENGTH_SHORT).show();
            //TODO limpiar los que existen y agregar los nuevos
            lstProductos.clear();
            lstProductos.addAll(db.getArrayProducto(
                    db.getCursorProducto(idCategoria)
            ));

            adaptadorProducto.notifyDataSetChanged();
            limpiar();
        }else{
            Toast.makeText(this,"Ocurrio un error al guardar",Toast.LENGTH_SHORT).show();
        }
    }
    private void eliminar(){
        if(producto_temp!=null){
            db.borrarCategoria(producto_temp.getId_producto());
            lstProductos.remove(producto_temp);
            adaptadorProducto.notifyDataSetChanged();
            producto_temp=null;
            Toast.makeText(this,"Se elimino producto",Toast.LENGTH_SHORT).show();
            limpiar();
        }
    }
    private void seleccionar(Producto producto){
        producto_temp = producto;
        lblId_Cat.setText(producto_temp.getId_categoria());
        txtNombreProducto.setText(producto.getNombre());
        txtDescripcionProducto.setText(producto.getDescripcion());
    }
    private void limpiar(){
        lblId_Cat.setText(null);
        txtNombreProducto.setText(null);
        txtDescripcionProducto.setText(null);
    }
}
