package br.com.atps.anhanguera.dispositivosmoveis.listadesejos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Core i5 on 18/05/2015.
 */
public class DesejoDBOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "banco_de_dados";

    private static final String DATABASE_NAME = "desejos.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABELA_DESEJO = "tb_desejo";

    public static final String COLUNA_ID = "id";
    public static final String COLUNA_PRODUTO = "produto";
    public static final String COLUNA_CATEGORIA = "categoria";
    public static final String COLUNA_LOJAS = "lojas";
    public static final String COLUNA_PRECO_MINIMO = "preco_minimo";
    public static final String COLUNA_PRECO_MAXIMO = "preco_maximo";

    private static final String CRIAR_TABELA_DESEJO =
            "CREATE TABLE " + TABELA_DESEJO + " (" +
                COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUNA_PRODUTO + " TEXT, " +
                COLUNA_CATEGORIA + " TEXT, " +
                COLUNA_LOJAS + " TEXT, " +
                COLUNA_PRECO_MAXIMO + " REAL, " +
                COLUNA_PRECO_MINIMO + " REAL )";


    public DesejoDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION    );
    }

    //Ao criar a versão do banco de dados
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRIAR_TABELA_DESEJO);
        Log.i(TAG,"Tabela tb_desejo criada!");
    }

    //Quando a versao do banco de dados já existir
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_DESEJO);
        onCreate(db);
    }
}
