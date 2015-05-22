package br.com.atps.anhanguera.dispositivosmoveis.listadesejos;

import android.content.Intent;
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
public class AlterarDesejoActivity extends ActionBarActivity {

    private DesejoDataSource datasource;
    private Desejo desejo;
    private static final String TAG = "AlterarDesejoActivity";
    public static final int ALTERAR_DESEJO = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_desejo);

        datasource = new DesejoDataSource(this);

        Intent telaDetalhes = getIntent();
        this.desejo = (Desejo) telaDetalhes.getSerializableExtra("desejo");

        atualizarTela();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alterar_desejo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_desfazer) {
            this.finish();
        } else if (id == R.id.action_salvar){
            Log.d(TAG, "Clicou em salvar");
            //Salvar no banco de dados
            EditText txtNomeProduto = (EditText) findViewById(R.id.txtNomeProduto);
            EditText txtCategoria = (EditText) findViewById(R.id.txtCategoria);
            EditText txtPrecoMinimo = (EditText) findViewById(R.id.txtPrecoMinimo);
            EditText txtPrecoMaximo = (EditText) findViewById(R.id.txtPrecoMaximo);
            EditText txtLojas = (EditText) findViewById(R.id.txtLojas);

            this.desejo.setProduto(txtNomeProduto.getText().toString());
            this.desejo.setCategoria(txtCategoria.getText().toString());
            this.desejo.setPrecoMinimo(new Double(txtPrecoMinimo.getText().toString()));
            this.desejo.setPrecoMaximo(new Double(txtPrecoMaximo.getText().toString()));
            this.desejo.setLojas(txtLojas.getText().toString());

            datasource.abrir();
            if (datasource.alterar(this.desejo)){
                datasource.fechar();
                Log.d(TAG, "Salvou no banco de dados");
                //Emite informação para o usuário Toast
                Toast toast = Toast.makeText(getApplicationContext(),"Desejo alterado com sucesso!", Toast.LENGTH_SHORT);
                toast.show();
                //Retorna a atividade com o codigo de requisicao
                Intent telaDetalhar = new Intent(this, DetalhesDesejoActivity.class);
                telaDetalhar.putExtra("desejo", desejo);
                setResult(RESULT_OK, telaDetalhar);
                finish();
            } else {
                datasource.fechar();
                Log.d(TAG,"Erro ao tentar salvar o desejo");
                //Emite informação para o usuário Toast
                Toast toast = Toast.makeText(getApplicationContext(),"Erro ao tentar salvar o desejo!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (this.desejo == null){
            Intent telaDetalhes = getIntent();
            this.desejo = (Desejo) telaDetalhes.getSerializableExtra("desejo");
        }

        atualizarTela();
    }

    private void atualizarTela() {
        EditText txtNomeProduto = (EditText) findViewById(R.id.txtNomeProduto);
        EditText txtCategoria = (EditText) findViewById(R.id.txtCategoria);
        EditText txtPrecoMinimo = (EditText) findViewById(R.id.txtPrecoMinimo);
        EditText txtPrecoMaximo = (EditText) findViewById(R.id.txtPrecoMaximo);
        EditText txtLojas = (EditText) findViewById(R.id.txtLojas);

        txtNomeProduto.setText(this.desejo.getProduto());
        txtCategoria.setText(this.desejo.getCategoria());
        txtPrecoMinimo.setText(String.valueOf(this.desejo.getPrecoMinimo()));
        txtPrecoMaximo.setText(String.valueOf(this.desejo.getPrecoMaximo()));
        txtLojas.setText(this.desejo.getLojas());
    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.fechar();
    }
}
