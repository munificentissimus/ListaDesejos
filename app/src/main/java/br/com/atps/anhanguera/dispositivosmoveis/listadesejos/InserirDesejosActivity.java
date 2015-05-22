package br.com.atps.anhanguera.dispositivosmoveis.listadesejos;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import br.com.atps.anhanguera.dispositivosmoveis.listadesejos.db.DesejoDataSource;
import br.com.atps.anhanguera.dispositivosmoveis.listadesejos.modelo.Desejo;

/**
 * Created by Core i5 on 18/05/2015.
 */
public class InserirDesejosActivity extends ActionBarActivity {

    private static final String TAG = "InserirDesejosActivity";

    private DesejoDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_desejo);

        EditText txtPrecoMinimo = (EditText) findViewById(R.id.txtPrecoMinimo);
        txtPrecoMinimo.setRawInputType(Configuration.KEYBOARD_12KEY);

        datasource = new DesejoDataSource(this);
        datasource.abrir();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inserir_desejos , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_salvar) {
            Log.d(TAG,"Clicou em salvar");
            //Salvar no banco de dados
            EditText txtNomeProduto = (EditText) findViewById(R.id.txtNomeProduto);
            EditText txtCategoria = (EditText) findViewById(R.id.txtCategoria);
            EditText txtPrecoMinimo = (EditText) findViewById(R.id.txtPrecoMinimo);
            EditText txtPrecoMaximo = (EditText) findViewById(R.id.txtPrecoMaximo);
            EditText txtLojas = (EditText) findViewById(R.id.txtLojas);

            Desejo novoDesejo = new Desejo();
            novoDesejo.setProduto(txtNomeProduto.getText().toString());
            novoDesejo.setCategoria(txtCategoria.getText().toString());
            novoDesejo.setPrecoMinimo(new Double(txtPrecoMinimo.getText().toString()));
            novoDesejo.setPrecoMaximo(new Double(txtPrecoMaximo.getText().toString()));
            novoDesejo.setLojas(txtLojas.getText().toString());

            novoDesejo = datasource.salvar(novoDesejo);

            Log.d(TAG,"Salvou no banco de dados");
            //Emite informa��o para o usu�rio Toast
            Toast toast = Toast.makeText(getApplicationContext(),"Desejo incluido com sucesso!", Toast.LENGTH_SHORT);
            toast.show();
            //Retorna para a lista de desejos (tela principal)
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Abre o banco de dados
        datasource.abrir();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Fecha o banco de dados
        datasource.fechar();
    }

}
