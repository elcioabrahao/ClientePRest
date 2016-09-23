package br.usjt.arqdesis.clienteprest.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import br.usjt.arqdesis.clienteprest.R;

import static android.media.CamcorderProfile.get;


/**
 * Created by asbonato on 9/19/16.
 * adaptado por elcioa em 23/09/2016
 */
public class ClienteAdapter extends BaseAdapter
{
    Activity context;
    List<Cliente> clientes;



    public ClienteAdapter(Activity context, List<Cliente> clientes){
        this.context = context;
        this.clientes = clientes;
    }
    @Override
    public int getCount() {
        return clientes.size();
    }

    @Override
    public Object getItem(int position) {
        if(position >= 0 && position < clientes.size())
            return clientes.get(position);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //o list view recicla os layouts para melhor performance
        //o layout reciclado vem no parametro convert view
        View view = convertView;
        //se nao recebeu um layout para reutilizar deve inflar um
        if(view == null) {
            //um inflater transforma um layout em uma view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.linha_cliente, parent, false);

            ImageView letraCliente = (ImageView)view.findViewById(R.id.foto_cliente);
            TextView nomeCliente = (TextView)view.findViewById(R.id.nome_cliente);
            TextView detalheCliente = (TextView)view.findViewById(R.id.detalhe_cliente);
            //faz cache dos widgets instanciados na tag da view para reusar quando houver reciclagem
            view.setTag(new ViewHolder(letraCliente, nomeCliente, detalheCliente));
        }
        //usa os widgets cacheados na view reciclada
        ViewHolder holder = (ViewHolder)view.getTag();
        //carrega os novos valores
        //Drawable drawable = Util.getDrawable(context, clientes.get(position).getFoto());
        Picasso.with(context).load("http://www.qpainformatica.com.br/exemplorest/images/"+clientes.get(position).getFoto()+".jpg").into(holder.getFotoCliente());
        //holder.getFotoCliente().setImageDrawable(drawable);
        Locale locale = new Locale("pt", "BR");
        holder.getNomeCliente().setText(clientes.get(position).getNome());
        holder.getDetalheCliente().setText(String.format("%s - %s", clientes.get(position).getFone(),
                clientes.get(position).getEmail()));

        return view;
    }


    public void updateClienteList(List<Cliente> newlist) {
        clientes.clear();
        clientes.addAll(newlist);
        this.notifyDataSetChanged();
    }

}
