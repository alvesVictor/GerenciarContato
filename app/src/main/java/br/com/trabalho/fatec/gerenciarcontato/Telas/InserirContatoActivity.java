package br.com.trabalho.fatec.gerenciarcontato.Telas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

import br.com.trabalho.fatec.gerenciarcontato.Bean.ContatoBean;
import br.com.trabalho.fatec.gerenciarcontato.Dao.ContatoDao;
import br.com.trabalho.fatec.gerenciarcontato.R;

public class InserirContatoActivity extends AppCompatActivity {
    private static final int FOTO = 1234;
    private EditText campoNome, campoEmail, campoTelefone, campoEndereco, campoSite;
    private Button btnCadastro;
    private ImageButton btnFoto;
    private ContatoDao contatoDao;
    private ContatoBean contato;
    private ImageView f;
    private String caminhoFoto;
    private ContatoBean cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_contato);

        contatoDao = new ContatoDao(getBaseContext());

        campoNome = (EditText) findViewById(R.id.campoNome);
        campoEmail = (EditText) findViewById(R.id.campoEmail);
        campoTelefone = (EditText) findViewById(R.id.campoTelefone);
        campoEndereco = (EditText) findViewById(R.id.campoEndereco);
        campoSite = (EditText) findViewById(R.id.campoSite);
        btnFoto = (ImageButton) findViewById(R.id.botaoFoto);
        btnCadastro = (Button) findViewById(R.id.botaoCadastro);
        f = (ImageView) findViewById(R.id.campoFoto);

        Intent intent = this.getIntent();
        cont = (ContatoBean) intent.getSerializableExtra("editCont");
        if(cont != null){
                preencheCampos(cont);
        }

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
            }
        });


        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //_id NUMERIC PRIMARY KEY AUTOINCREMENT, nome TEXT, site TEXT, telefone TEXT, foto TEXT, endereco TEXT, email TEXT
                if(cont != null) {
                    contato = new ContatoBean(cont.getId(),campoNome.getText().toString(),campoSite.getText().toString(),campoTelefone.getText().toString(),campoEndereco.getText().toString(),caminhoFoto,campoEmail.getText().toString());
                    if (contatoDao.alterarContato(contato)) {
                        Toast.makeText(getBaseContext(), "Sucesso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), "Não foi possivel realizar operação", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    contato = new ContatoBean(campoNome.getText().toString(),campoSite.getText().toString(),campoTelefone.getText().toString(),campoEndereco.getText().toString(),caminhoFoto,campoEmail.getText().toString());
                    if (contatoDao.inserirContato(contato)) {
                        Toast.makeText(getBaseContext(), "Sucesso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), "Não foi possivel realizar operação", Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
            }
        });
    }

    private void tirarFoto() {
        caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis()+".jpg";
        Intent tirarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri localFoto = Uri.fromFile(new File(caminhoFoto));
        tirarFoto.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);
        if (tirarFoto.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(tirarFoto, FOTO);
        }
        else {
            Toast.makeText(getBaseContext(), "Não foi possivel tirar foto",Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FOTO && resultCode == RESULT_OK) {
            Bitmap imagemFoto = BitmapFactory.decodeFile(caminhoFoto);
            imagemFoto = Bitmap.createScaledBitmap(imagemFoto, 250, 400, true);
            f.setImageBitmap(imagemFoto);
            f.setTag(caminhoFoto);
            //f.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        else{
            caminhoFoto = null;
        }
    }

    public void preencheCampos(ContatoBean cont){
        campoNome.setText(cont.getNome());
        campoEmail.setText(cont.getEmail());
        campoTelefone.setText(cont.getTelefone());
        campoEndereco.setText(cont.getEndereco());
        campoSite.setText(cont.getSite());
        if(cont.getFoto() != null) {
            Bitmap imagemFoto = BitmapFactory.decodeFile(cont.getFoto());
            imagemFoto = Bitmap.createScaledBitmap(imagemFoto, 250, 400, true);
            f.setImageBitmap(imagemFoto);
            f.setTag(cont.getFoto());
        }
        btnCadastro.setText("Alterar");
    }

}
