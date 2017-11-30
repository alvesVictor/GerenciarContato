package br.com.trabalho.fatec.gerenciarcontato.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Victor on 12/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String BANCO_DADOS = "Contato";
    private static int VERSAO = 1;
    public DatabaseHelper(Context context) {
        super(context, BANCO_DADOS, null, VERSAO);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE contato (_id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, site TEXT, telefone TEXT, foto TEXT, endereco TEXT, email TEXT);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion) {

    }
}
