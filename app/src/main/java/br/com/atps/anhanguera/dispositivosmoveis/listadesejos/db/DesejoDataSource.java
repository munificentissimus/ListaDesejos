package br.com.atps.anhanguera.dispositivosmoveis.listadesejos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.atps.anhanguera.dispositivosmoveis.listadesejos.modelo.Desejo;

/**
 * Created by Core i5 on 18/05/2015.
 */
public class DesejoDataSource {

    private static final String TAG = "banco_de_dados";

    private SQLiteOpenHelper dbhelper;
    private SQLiteDatabase database;

    //Array de strings com os nomes das colunas da tabela desejo
    private static final String[] COLUNAS_TABELA_DESEJO = {
            DesejoDBOpenHelper.COLUNA_ID,
            DesejoDBOpenHelper.COLUNA_PRODUTO,
            DesejoDBOpenHelper.COLUNA_CATEGORIA,
            DesejoDBOpenHelper.COLUNA_PRECO_MINIMO,
            DesejoDBOpenHelper.COLUNA_PRECO_MAXIMO,
            DesejoDBOpenHelper.COLUNA_LOJAS
    };

    public DesejoDataSource(Context context) {
        dbhelper = new DesejoDBOpenHelper(context);

    }

    public void abrir(){
        Log.i(TAG, "Banco de dados aberto!");
        database = dbhelper.getWritableDatabase();
    }

    public void fechar(){
        Log.i(TAG, "Banco de dados fechado!");
        dbhelper.close();
    }

    /**
     * Cria uma nova instancia de Desejo e persiste no banco de dados
     * @param desejo
     * @return
     */
    public Desejo salvar(Desejo desejo){
        ContentValues valores = new ContentValues();
        valores.put(DesejoDBOpenHelper.COLUNA_PRODUTO, desejo.getProduto());
        valores.put(DesejoDBOpenHelper.COLUNA_CATEGORIA, desejo.getCategoria());
        valores.put(DesejoDBOpenHelper.COLUNA_PRECO_MINIMO, desejo.getPrecoMinimo());
        valores.put(DesejoDBOpenHelper.COLUNA_PRECO_MAXIMO, desejo.getPrecoMaximo());
        valores.put(DesejoDBOpenHelper.COLUNA_LOJAS, desejo.getLojas());

        long novoId = database.insert(DesejoDBOpenHelper.TABELA_DESEJO, null, valores);
        desejo.setId(novoId);

        return desejo;
    }

    public List<Desejo> pesquisarTodos(){
        List<Desejo> desejos = new ArrayList<Desejo>();

        Cursor cursor = database.query(DesejoDBOpenHelper.TABELA_DESEJO,
                    COLUNAS_TABELA_DESEJO, null, null, null, null, null);

        Log.i(TAG, "Retornou  " + cursor.getCount() + " registros.");

        //Se retornou algo
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Desejo desejo = new Desejo();

                desejo.setId(cursor.getLong(cursor.getColumnIndex(DesejoDBOpenHelper.COLUNA_ID)));
                desejo.setProduto(cursor.getString(cursor.getColumnIndex(DesejoDBOpenHelper.COLUNA_PRODUTO)));
                desejo.setCategoria(cursor.getString(cursor.getColumnIndex(DesejoDBOpenHelper.COLUNA_CATEGORIA)));
                desejo.setPrecoMinimo(cursor.getDouble(cursor.getColumnIndex(DesejoDBOpenHelper.COLUNA_PRECO_MINIMO)));
                desejo.setPrecoMaximo(cursor.getDouble(cursor.getColumnIndex(DesejoDBOpenHelper.COLUNA_PRECO_MAXIMO)));
                desejo.setLojas(cursor.getString(cursor.getColumnIndex(DesejoDBOpenHelper.COLUNA_LOJAS)));

                desejos.add(desejo);
            }
        }

        return desejos;
    }

    public Desejo perquisar(long idDesejo){
        Cursor cursor = database.query(DesejoDBOpenHelper.TABELA_DESEJO, COLUNAS_TABELA_DESEJO,
                DesejoDBOpenHelper.COLUNA_ID + "=?", new String[]{String.valueOf(idDesejo)},null,null,null,null);

        if (cursor != null && cursor.getCount() > 0){
            Log.i(TAG,"Pesquisa retornou dados: " + cursor.getCount());
            cursor.moveToNext();
        }

        Desejo desejo = new Desejo();

        desejo.setId(idDesejo);
        desejo.setProduto(cursor.getString(cursor.getColumnIndex(DesejoDBOpenHelper.COLUNA_PRODUTO)));
        desejo.setCategoria(cursor.getString(cursor.getColumnIndex(DesejoDBOpenHelper.COLUNA_CATEGORIA)));
        desejo.setPrecoMinimo(cursor.getDouble(cursor.getColumnIndex(DesejoDBOpenHelper.COLUNA_PRECO_MINIMO)));
        desejo.setPrecoMaximo(cursor.getDouble(cursor.getColumnIndex(DesejoDBOpenHelper.COLUNA_PRECO_MAXIMO)));
        desejo.setLojas(cursor.getString(cursor.getColumnIndex(DesejoDBOpenHelper.COLUNA_LOJAS)));

        Log.i(TAG, "Desejo pesquisado: " + desejo);

        return desejo;
    }


    public boolean excluir(long idDesejo){
       return database.delete(DesejoDBOpenHelper.TABELA_DESEJO, DesejoDBOpenHelper.COLUNA_ID + "=" + idDesejo, null) > 0;
    }

    public boolean alterar(Desejo desejo){
        ContentValues valores = new ContentValues();
        valores.put(DesejoDBOpenHelper.COLUNA_PRODUTO, desejo.getProduto());
        valores.put(DesejoDBOpenHelper.COLUNA_CATEGORIA,desejo.getCategoria());
        valores.put(DesejoDBOpenHelper.COLUNA_PRECO_MINIMO,desejo.getPrecoMinimo());
        valores.put(DesejoDBOpenHelper.COLUNA_PRECO_MAXIMO,desejo.getPrecoMaximo());
        valores.put(DesejoDBOpenHelper.COLUNA_LOJAS,desejo.getLojas());
        return database.update(DesejoDBOpenHelper.TABELA_DESEJO, valores ,DesejoDBOpenHelper.COLUNA_ID + "=" + desejo.getId(), null) > 0;
    }
}
