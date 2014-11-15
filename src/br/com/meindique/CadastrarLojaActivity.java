package br.com.meindique;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import br.com.meindique.util.ConexaoHttpClient;
import br.com.meindique.util.ServerHost;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.os.Build;

public class CadastrarLojaActivity extends ActionBarActivity {
	private EditText etCategoria,etLoja,etGPS,etTelefone,etEndereco,etArea;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_loja);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastrar_loja, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_cadastrar_loja,
					container, false);
			return rootView;
		}
	}

	public void gravar(View v){
		etCategoria = (EditText) findViewById(R.id.etCategoria);
		etLoja = (EditText) findViewById(R.id.etLoja);
		etGPS = (EditText) findViewById(R.id.etGPS);
		etTelefone = (EditText) findViewById(R.id.etTelefone);
		etEndereco = (EditText) findViewById(R.id.etEndereco);
		etArea = (EditText) findViewById(R.id.etArea);
		startActivity(new Intent(this,IndicacaoActivity.class).putExtra("idIndicar", salvar(etCategoria.getText().toString(), etLoja.getText().toString(), etGPS.getText().toString(), etTelefone.getText().toString(), etEndereco.getText().toString(), etArea.getText().toString())));
	}
	
	private String salvar(String categoria, String nomeLoja, String localGPS,String telefone, String endereco, String area) {
		String urlGet =  ServerHost.HOST.getHost()+"meIndique/gravarLojas.php?categoria="+categoria.trim()+"&nomeLoja="+nomeLoja.trim()+"&telefone="+telefone.trim()+"&endereco="+endereco.trim()+"&area="+area.trim();
		
		String msg = null;
		try {
			msg = ConexaoHttpClient.executaHttpGet(urlGet).toString();//.replaceAll("\\s+", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(msg.equals("0")){
			mensagem("Erro", "Erro ao gravar");
			startActivity(new Intent(this, MenulojasActivity.class));
		}	
		return Pattern.compile("[^0-9]").matcher(msg).replaceAll("");
	}
	
	public void mensagem(String titulo, String texto){
		AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
		mensagem.setTitle(titulo);
		mensagem.setMessage(texto);
		mensagem.setNeutralButton("OK", null);
		mensagem.show();
	}	
}
