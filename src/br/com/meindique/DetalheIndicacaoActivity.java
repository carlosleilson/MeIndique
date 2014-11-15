package br.com.meindique;

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
import android.widget.TextView;
import android.os.Build;

public class DetalheIndicacaoActivity extends ActionBarActivity {
	private TextView tvEstadoCidadeDetalhe,tvAssuntoDetalhe,tvMensagemDetalhe;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalhe_indicacao);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);		
		tvEstadoCidadeDetalhe = (TextView) findViewById(R.id.tvEstadoCidadeDetalhe);
		tvAssuntoDetalhe = (TextView) findViewById(R.id.tvAssuntoDetalhe);
		tvMensagemDetalhe = (TextView) findViewById(R.id.tvMensagemDetalhe);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		String [] Indicacao = getLista(getIntent().getExtras().getInt("position"));
		//for (String itens : Indicacao) {
			tvEstadoCidadeDetalhe.setText(Indicacao[4].trim());
			tvAssuntoDetalhe.setText(Indicacao[0].trim());
			tvMensagemDetalhe.setText(Indicacao[1].trim());
			
		//}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalhe_indicacao, menu);
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
			View rootView = inflater.inflate(
					R.layout.fragment_detalhe_indicacao, container, false);
			return rootView;
		}
	}
	
	public void indicar(View v){
		startActivity(new Intent(this,IndicacaoActivity.class));
	}
	public String[] getLista(int position){
		Log.i("Posição.:", ""+position++);
		//String urlGet = "http://meindique.esy.es/meIndique/detalheIdicacao.php?id="+position++;
			String urlGet =  ServerHost.HOST.getHost()+"meIndique/detalheIdicacao.php?id="+position++;
		String dado = null;
		String [] dados = null;
		int count = 0;
		try {
			dado = ConexaoHttpClient.executaHttpGet(urlGet).toString();//.replaceAll("\\s+", "");
			Log.i("Indicacao", dado);
			char caracter_lido = 'n';
			String nome = "";
			dados = new String[countStringByChar(dado,'#')];
			for(int i=0; caracter_lido!='^';i++){
				caracter_lido = dado.charAt(i);
				if(caracter_lido!='#'){
					nome += (char) caracter_lido;
				}else{
					dados[count] = nome;
					nome = "";
					count++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Error.: ",e.getMessage());
		}
		return dados;
	}	
	
	private int countStringByChar(String string, char flag) {
		int count = 0;
		char caracter_lido = 'n';
		for (int i = 0; caracter_lido != '^'; i++) {
			caracter_lido = string.charAt(i);
			if (caracter_lido == '#') {
				count++;
			}
		}
		return count;
	}

	public void mensagemExibir(String titulo, String texto){
		AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
		mensagem.setTitle(titulo);
		mensagem.setMessage(texto);
		mensagem.setNeutralButton("OK",null);
		mensagem.show();
	 }			
}
