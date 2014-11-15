package br.com.meindique;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import br.com.meindique.bens.Dados;
import br.com.meindique.util.ConexaoHttpClient;
import br.com.meindique.util.ServerHost;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class IndicacaoActivity extends ActionBarActivity {
	private EditText etEstadoCidade,etAssunto, etMensagem;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicacao);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
//		getIntent().getExtras().getInt("id");
		
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.indicacao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_indicacao, container, false);
            return rootView;
        }
    }
    
    public void gravar(View v){
		etEstadoCidade = (EditText) findViewById(R.id.etEstadoCidade);
		etAssunto = (EditText) findViewById(R.id.etAssunto);
		etMensagem = (EditText) findViewById(R.id.etMensagem);
		
		salvar(String.valueOf(getIntent().getExtras().getString("idIndicar")), "1", etAssunto.getText().toString(), etMensagem.getText().toString(), etEstadoCidade.getText().toString());
    }
    
	private void salvar(String idLojas, String idUsuarios, String titulo, String descricao, String endereco) {
		String urlGet =  ServerHost.HOST.getHost()+"meIndique/gravarIndicacao.php?idLojas="+idLojas.trim()+"&idUsuarios="+idUsuarios.trim()+"&titulo="+titulo.trim()+"&descricao="+descricao.trim()+"&endereco="+endereco.trim();
		String msg = null;
		try {
			//msg = ConexaoHttpClient.executaHttpPost(urlPost,parametrosPost).toString().replaceAll("\\s+", "");
			msg = ConexaoHttpClient.executaHttpGet(urlGet).toString();//.replaceAll("\\s+", "");
			msg = Pattern.compile("[^0-9]").matcher(msg).replaceAll("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(msg.equals("0")){
			mensagem("Erro", "Erro ao gravar");
		}
		startActivity(new Intent(this, MenulojasActivity.class));
	}  
    
	private void mensagem(String titulo, String texto){
		AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
		mensagem.setTitle(titulo);
		mensagem.setMessage(texto);
		mensagem.setNeutralButton("OK", null);
		mensagem.show();
	}
}
