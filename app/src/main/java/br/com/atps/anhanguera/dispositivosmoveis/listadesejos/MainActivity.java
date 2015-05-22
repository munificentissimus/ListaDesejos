package br.com.atps.anhanguera.dispositivosmoveis.listadesejos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;

import java.util.List;

import br.com.atps.anhanguera.dispositivosmoveis.listadesejos.db.DesejoDataSource;
import br.com.atps.anhanguera.dispositivosmoveis.listadesejos.modelo.Desejo;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private DesejoDataSource datasource;
    private List<Desejo> meusDesejos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new DesejoDataSource(this);
        datasource.abrir();

        //Busca os desejos já persistidos no banco de dados
        this.meusDesejos = datasource.pesquisarTodos();

        //Cria o array adapter para exibir os desejos em forma de lista
        ArrayAdapter<Desejo> adapter = new ArrayAdapter<Desejo>(this, R.layout.simple_list_item_1, this.meusDesejos);
        ListView listView = (ListView) findViewById(R.id.listViewDesejos);


        listView.setOnItemClickListener(itemClickListenterListaDesejos);

        listView.setAdapter(adapter);
    }

    private AdapterView.OnItemClickListener itemClickListenterListaDesejos = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Desejo desejo = (Desejo) parent.getItemAtPosition(position);
            Intent telaDetalhar = new Intent(MainActivity.this, DetalhesDesejoActivity.class);
            telaDetalhar.putExtra("desejo", desejo);
            startActivity(telaDetalhar);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new) {
            Log.d(TAG, "Clicou em Inserir");
            Intent telaInserir = new Intent(this, InserirDesejosActivity.class);
            startActivity(telaInserir);
            return true;
        } else if (id== R.id.action_share){
            compartilhar("Meus desejos", this.meusDesejos.toString());
        }

        return super.onOptionsItemSelected(item);
    }

    public void compartilhar(String assunto,String mensagem) {
        Intent txtIntent = new Intent(Intent.ACTION_SEND);
        txtIntent .setType("text/plain");
        txtIntent .putExtra(Intent.EXTRA_SUBJECT, assunto);
        txtIntent .putExtra(Intent.EXTRA_TEXT, mensagem);
        startActivity(Intent.createChooser(txtIntent ,"Share"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Abre o banco de dados
        datasource.abrir();

        //Busca os desejos já persistidos no banco de dados
        List<Desejo> desejos = datasource.pesquisarTodos();

        //Cria o array adapter para exibir os desejos em forma de lista
        ArrayAdapter<Desejo> adapter = new ArrayAdapter<Desejo>(this, R.layout.simple_list_item_1, desejos);
        ListView listView = (ListView) findViewById(R.id.listViewDesejos);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Fecha o banco de dados
        datasource.fechar();
    }
}
