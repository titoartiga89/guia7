package modelo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.dev4u.hv.guia7.R;

import java.util.List;



public class AdaptadorProducto extends ArrayAdapter<Producto> {

    public AdaptadorProducto(Context context, List<Producto> objects) {
        super(context, 0, objects);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obteniendo el dato
        Producto producto = getItem(position);
        //TODO inicializando el layout correspondiente al item (Categoria)
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_producto, parent, false);
        }
        TextView lblProducto = (TextView) convertView.findViewById(R.id.lblProducto);
        TextView lblDescripcion = (TextView) convertView.findViewById(R.id.lblDescripcion);
        // mostrar los datos
        lblProducto.setText(producto.getId_producto() +" "+producto.getNombre());
        lblDescripcion.setText(producto.getDescripcion());
        // Return la convertView ya con los datos
        return convertView;
    }
}
