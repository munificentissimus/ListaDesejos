package br.com.atps.anhanguera.dispositivosmoveis.listadesejos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import br.com.atps.anhanguera.dispositivosmoveis.listadesejos.db.DesejoDataSource;
import br.com.atps.anhanguera.dispositivosmoveis.listadesejos.modelo.Desejo;


public class DetalhesDesejoActivity extends ActionBarActivity {

    private DesejoDataSource datasource;
    private Desejo desejo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_desejo);

        datasource = new DesejoDataSource(this);

        //Recebe o desejo passado pela atividade anterior
        Intent telaDetalhes = getIntent();
        this.desejo = (Desejo) telaDetalhes.getSerializableExtra("desejo");

        //Busca as referencias aos elementos da tela
        atualizarTela();

        ImageButton buscapeLogo = (ImageButton) findViewById(R.id.buscape_logo);

        buscapeLogo.setOnClickListener(pesquisarProduto);
    }

    private View.OnClickListener pesquisarProduto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://compare.buscape.com.br/" +
                            DetalhesDesejoActivity.this.desejo.getProduto()));
            startActivity(browserIntent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhes_desejo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            Intent telaAlterar = new Intent(this, AlterarDesejoActivity.class);
            telaAlterar.putExtra("desejo", this.desejo);
            startActivityForResult(telaAlterar, 0);
        } else if (id == R.id.action_discard){
            //Exibir dialogo de confirma��o da exclus�o
            AlertDialog.Builder confirmacao = new AlertDialog.Builder(this);
            confirmacao.setMessage("Confirma exclus�o do desejo?");
            confirmacao.setCancelable(false);

            confirmacao.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast de confirma��o
                    datasource.abrir();
                    if (datasource.excluir(DetalhesDesejoActivity.this.desejo.getId())) {
                        Toast.makeText(DetalhesDesejoActivity.this, "Desejo exclu�do!", Toast.LENGTH_SHORT).show();
                    }
                    datasource.fechar();
                    //Retornar para tela de listagem
                    Intent telaPrincipal = new Intent(DetalhesDesejoActivity.this, MainActivity.class);
                    startActivity(telaPrincipal);
                }
            });

            confirmacao.setNegativeButton("N�o", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //N�o fazer nada.
                }
            });

            confirmacao.create().show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (this.desejo == null) {
            Intent telaDetalhes = getIntent();
            this.desejo = (Desejo) telaDetalhes.getSerializableExtra("desejo");
            atualizarTela();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        datasource.fechar();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            this.desejo = (Desejo) data.getSerializableExtra("desejo");
            atualizarTela();
        }
    }

    private void atualizarTela() {
        TextView txtNomeProduto = (TextView) findViewById(R.id.txtNomeProduto);
        TextView txtCategoria = (TextView) findViewById(R.id.txtCategoria);
        TextView txtPrecoMinimo = (TextView) findViewById(R.id.txtPrecoMinimo);
        TextView txtPrecoMaximo = (TextView) findViewById(R.id.txtPrecoMaximo);
        TextView txtLojas = (TextView) findViewById(R.id.txtLojas);

        txtNomeProduto.setText(desejo.getProduto());
        txtCategoria.setText(desejo.getCategoria());
        txtPrecoMinimo.setText("R$ "+  (desejo.getPrecoMinimo()+"").replace(".",",") );
        txtPrecoMaximo.setText("R$ "+  (desejo.getPrecoMaximo()+"").replace(".",",") );
        txtLojas.setText(desejo.getLojas());
    }
}
