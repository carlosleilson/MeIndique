package br.com.meindique;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import br.com.meindique.dao.LoginDao;
import br.com.meindique.util.ConexaoHttpClient;
import br.com.meindique.util.ServerHost;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;

public class CadastroLoginActivity extends ActionBarActivity {
	//banco login
	private LoginDao loginDb = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro_login);
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
		getMenuInflater().inflate(R.menu.cadastro_login, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_cadastro_login,
					container, false);
			return rootView;
		}
	}
	
	public void gravar(View v){
		
		EditText email = (EditText) findViewById(R.id.etaEmail);
		EditText telefone = (EditText) findViewById(R.id.etCadastraLoginTelefone);
		EditText nome = (EditText) findViewById(R.id.etCadastroLoginNome);
		EditText senha = (EditText) findViewById(R.id.etCadastroLoginSenha);
		
		if(cadastroIsNull(email,telefone,nome,senha)){
			//startActivity(new Intent(this, this.getClass()));
			mensagem("Erro", "Algum campo em branco");
		}else{
			//loginDb.gravar(email.getText().toString(), telefone.getText().toString(), nome.getText().toString(), senha.getText().toString(),this);
			salvar(email.getText().toString(), telefone.getText().toString(), nome.getText().toString(), senha.getText().toString());
		}
		
	}

	private void salvar(String email, String telefone, String nome,String senha) {
		String [] login = new String[1];
		String urlPost =  ServerHost.HOST.getHost()+"meIndique/gravarUsuario.php";
		String urlGet =  ServerHost.HOST.getHost()+"meIndique/gravarUsuario.php?email="+email.trim()+"&telefone="+telefone.trim()+"&nome="+nome.trim()+"&senha="+senha.trim();
		ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
		parametrosPost.add(new BasicNameValuePair("email", email.trim()));
		parametrosPost.add(new BasicNameValuePair("telefone", telefone.trim()));
		parametrosPost.add(new BasicNameValuePair("nome", nome.trim()));
		parametrosPost.add(new BasicNameValuePair("senha", senha.trim()));
		String msg = null;
		try {
			//msg = ConexaoHttpClient.executaHttpPost(urlPost,parametrosPost).toString().replaceAll("\\s+", "");
			msg = ConexaoHttpClient.executaHttpGet(urlGet).toString().replaceAll("\\s+", "");
			Log.i("SErver.:",msg );
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Error.: ",e.getMessage());
		}
		if(msg.equals("1")){
			mensagem("Alerta mensagem android", "gravado com sucesso");
			startActivity(new Intent(this, LoginActivity.class));
		}else
			mensagem("Erro", "Erro ao gravar");
	}
	
	private boolean cadastroIsNull(EditText email,EditText telefone,EditText nome,EditText senha) {
		View focusView = null;
		// valida e-mail
		if (TextUtils.isEmpty(email.getText().toString())) {
			email.setError(getString(R.string.error_field_required));
			focusView = email;
			return true;
		} else if (!email.getText().toString().contains("@")) {
			email.setError(getString(R.string.error_invalido_email_cadastro_login));
			focusView = email;
			return true;
		}
		
		// valida as senha
		if (TextUtils.isEmpty(senha.getText().toString())) {
			senha.setError(getString(R.string.error_field_required));
			focusView = senha;
			return true;
		} else if (senha.getText().toString().length() < 4) {
			senha.setError(getString(R.string.error_invalido_senha_cadastro_login));
			focusView = senha;
			return true;
		}
		return false;
	}
	
	public void voltar(View v){
		startActivity(new Intent(this, LoginActivity.class));
	}
	
	public void mensagem(String titulo, String texto){
		AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
		mensagem.setTitle(titulo);
		mensagem.setMessage(texto);
		mensagem.setNeutralButton("OK", null);
		mensagem.show();
	}		
}
