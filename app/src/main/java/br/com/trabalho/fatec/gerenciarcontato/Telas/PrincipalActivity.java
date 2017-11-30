package br.com.trabalho.fatec.gerenciarcontato.Telas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;

import br.com.trabalho.fatec.gerenciarcontato.Bean.ContatoBean;
import br.com.trabalho.fatec.gerenciarcontato.Dao.ContatoDao;
import br.com.trabalho.fatec.gerenciarcontato.R;

public class PrincipalActivity extends AppCompatActivity {
    private ListView listContatos;
    //private List<ContatoBean> contato;
    private ContatoBean contatoSelect;
    private static final int TELEFONE_CODE_REQUEST = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listContatos = (ListView) findViewById(R.id.listaContatos);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this, InserirContatoActivity.class);
                startActivity(intent);
            }
        });

        //Editando o aluno selecionado
        //Novo setOnItemClickListener
        listContatos.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent alterar = new Intent(PrincipalActivity.this, InserirContatoActivity.class);
                alterar.putExtra("editCont", (Serializable) listContatos.getItemAtPosition(position));
                startActivity(alterar);
            }
        });
        registerForContextMenu(listContatos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.carregaLista();
    }

    private void carregaLista() {
        ContatoDao con = new ContatoDao(this);
        List<ContatoBean> contatos = con.listarContatos();
        ArrayAdapter <ContatoBean> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,contatos);
        this.listContatos.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, view, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        contatoSelect = (ContatoBean) listContatos.getAdapter().getItem(info.position);

        //Enviar SMS
        final MenuItem sms = menu.add("SMS");
        sms.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intentSms = new Intent(Intent.ACTION_VIEW);
                intentSms.setData(Uri.parse("sms:" +contatoSelect.getTelefone()));
                intentSms.putExtra("sms_body", "Mensagem");
                sms.setIntent(intentSms);
                return false;
            }
        });

        //Fazer Ligaçao
        MenuItem ligar = menu.add("Ligar");
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + contatoSelect.getTelefone()));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                return false;
            }
        });

        //site
        final MenuItem site = menu.add("Acessar site");
        site.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intentSite = new Intent(Intent.ACTION_VIEW);
                intentSite.setData(Uri.parse("http:" +contatoSelect.getSite()));
                site.setIntent(intentSite);
                return false;
            }
        });


        //Mapa
        final MenuItem mapa = menu.add("Achar endereço");
        mapa.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intentMapa = new Intent(Intent.ACTION_VIEW);
                intentMapa.setData(Uri.parse("geo:0.0?z=14&q=" + Uri.encode(contatoSelect.getEndereco())));
                mapa.setIntent(intentMapa);
                return false;
            }
        });

        final MenuItem email = menu.add("E-mail");
        email.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+ contatoSelect.getEmail()));
                //intent.putExtra(Intent.EXTRA_EMAIL, contatoSelect.getEmail());
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

                return false;
            }
        });

        MenuItem deletar = menu.add("Apagar contato");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            //Metodo para deletar
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ContatoDao daoContato = new ContatoDao(PrincipalActivity.this);
                daoContato.excluirContato(contatoSelect);
                carregaLista();
                return false;
            }
        });

    }
}
