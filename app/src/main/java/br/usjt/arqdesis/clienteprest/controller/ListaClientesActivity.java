package br.usjt.arqdesis.clienteprest.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.usjt.arqdesis.clienteprest.R;
import br.usjt.arqdesis.clienteprest.model.Cliente;
import br.usjt.arqdesis.clienteprest.model.ClienteAdapter;
import br.usjt.arqdesis.clienteprest.util.APIClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ListaClientesActivity extends AppCompatActivity {
    public static final String NOME = "br.usjt.arqdesis.clientep2.nome";
    public static final String EMAIL = "br.usjt.arqdesis.clientep2.email";
    public static final String FONE = "br.usjt.arqdesis.clientep2.fone";
    Activity atividade;
    List<Cliente> lista;
    Callback callBackCliente;
    ClienteAdapter adapter;
    ListView listView;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        lista = new ArrayList<Cliente>();

        configurarCallbackCliente();

        atividade = this;
        Intent intent = getIntent();
        String chave = intent.getStringExtra(MainActivity.CHAVE);

        //lista = Data.buscaClientes(chave);

        adapter = new ClienteAdapter(this, lista);
        listView = (ListView) findViewById(R.id.lista_clientes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // manda para a tela de detalhe
                Intent intent = new Intent(atividade, DetalheClienteActivity.class);
                intent.putExtra(NOME, lista.get(position).getNome());
                intent.putExtra(FONE, lista.get(position).getFone());
                intent.putExtra(EMAIL, lista.get(position).getEmail());

                startActivity(intent);

            }

        });

        Toast.makeText(ListaClientesActivity.this,"Carregando...",Toast.LENGTH_SHORT).show();

        if(chave !=null && chave.length()>0){
            new APIClient().getRestService().getAllPoetaLikeName(chave,callBackCliente);
        } else{
            new APIClient().getRestService().getAllPoetas(callBackCliente);
        }


    }

    private void configurarCallbackCliente() {

        callBackCliente = new Callback<List<Cliente>>() {

            @Override public void success(List<Cliente> lista, Response response) {

                if(response.getStatus()==200) {
                    adapter.updateClienteList(lista);
                }else{
                    Toast.makeText(ListaClientesActivity.this,"Falha na comunicação com o servidor!",Toast.LENGTH_LONG).show();
                }

            }

            @Override public void failure(RetrofitError error) {

                Toast.makeText(ListaClientesActivity.this,"Falha na comunicação com o servidor!",Toast.LENGTH_LONG).show();

                Log.e("RETROFIT", "Error:"+error.getMessage());
            }
        };
    }

}
