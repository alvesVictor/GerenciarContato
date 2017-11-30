package br.com.trabalho.fatec.gerenciarcontato.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.trabalho.fatec.gerenciarcontato.Bean.ContatoBean;
import br.com.trabalho.fatec.gerenciarcontato.Helper.DatabaseHelper;

/**
 * Created by Victor on 12/11/2017.
 */

public class ContatoDao {
    private DatabaseHelper helper;
    private List<ContatoBean> contatos;

    public ContatoDao(Context context){
        helper = new DatabaseHelper(context);
    }

    public boolean inserirContato(ContatoBean contato){

        SQLiteDatabase db = helper.getWritableDatabase();
        //nome TEXT, site TEXT, telefone TEXT, foto BLOB, endereco TEXT, email TEXT
        ContentValues values = new ContentValues();
        values.put("nome",contato.getNome());
        values.put("site",contato.getSite());
        values.put("telefone",contato.getTelefone());
        values.put("foto",contato.getFoto());
        values.put("endereco",contato.getEndereco());
        values.put("email",contato.getEmail());

        long resul = db.insert("contato",null,values);
        db.close();
        if(resul != -1 ){
            return true;
        }
        return false;
    }

    public boolean alterarContato(ContatoBean contato){
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("nome",contato.getNome());
        values.put("site",contato.getSite());
        values.put("telefone",contato.getTelefone());
        values.put("foto",contato.getFoto());
        values.put("endereco",contato.getEndereco());
        values.put("email",contato.getEmail());

        long resul = db.update("contato",values,"_id = ?",new String[]{contato.getId().toString()});
        db.close();
        if(resul != -1 ){
            return true;
        }
        return false;
    }

    public boolean excluirContato(ContatoBean contato){
        SQLiteDatabase db = helper.getWritableDatabase();

        long resul = db.delete("contato","_id = ?",new String[]{contato.getId().toString()});
        db.close();
        if(resul != 0){
            return true;
        }
        return false;
    }

    public List<ContatoBean> listarContatos(){
        SQLiteDatabase db = helper.getReadableDatabase();
        //nome TEXT, site TEXT, telefone TEXT, foto BLOB, endereco TEXT, email TEXT
        Cursor cursor = db.rawQuery("SELECT * FROM contato ORDER BY nome",null);
        cursor.moveToFirst();
        contatos = new ArrayList<>();

        for(int i=0;i<cursor.getCount();i++){
            ContatoBean con = new ContatoBean();
            con.setId(cursor.getInt(0));
            con.setNome(cursor.getString(1));
            con.setSite(cursor.getString(2));
            con.setTelefone(cursor.getString(3));
            con.setFoto(cursor.getString(4));
            con.setEndereco(cursor.getString(5));
            con.setEmail(cursor.getString(6));

            contatos.add(con);

            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return contatos;
    }
}
