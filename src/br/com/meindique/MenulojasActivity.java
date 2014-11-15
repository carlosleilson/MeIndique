package br.com.meindique;

import java.util.regex.Pattern;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.meindique.util.ConexaoHttpClient;
import br.com.meindique.util.ServerHost;

public class MenulojasActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menulojas);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		ArrayAdapter<String> listasDeLojas = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getLista());
		ListView listaLojas = (ListView)  findViewById(R.id.listaLojas);		
		listaLojas.setAdapter(listasDeLojas);
		listaLojas.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				String nome = (String) parent.getItemAtPosition(position);
				startActivity(new Intent(MenulojasActivity.this,MenuIndicacaoActivity.class).putExtra("position", getListaID(nome)));
			}
		});		

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		Log.i("ListaLojas", "inicializado.....");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menulojas, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			startActivity(new Intent(MenulojasActivity.this,CadastrarLojaActivity.class));
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
			View rootView = inflater.inflate(R.layout.fragment_menulojas,
					container, false);
			return rootView;
		}
	}
	public String[] getLista(){
		//String urlGet = "http://meindique.esy.es/meIndique/listaLoja.php";
		String urlGet =  ServerHost.HOST.getHost()+"meIndique/listaLoja.php";
		String dado = null;
		String [] dados = null;
		int count = 0;
		try {
			dado = ConexaoHttpClient.executaHttpGet(urlGet).toString();//.replaceAll("\\s+", "");
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
		if(dados==null){
			dados = new String[1];
			dados[0]="Sem lista";
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
	
	public void indicar(View v){
		startActivity(new Intent(MenulojasActivity.this,CadastrarLojaActivity.class));
	}
	
	public String getListaID(String nomeLoja){
		//String urlGet = "http://meindique.esy.es/meIndique/listaLoja.php";
		String urlGet =  ServerHost.HOST.getHost()+"meIndique/listaLojaId.php?nomeLoja="+nomeLoja;
		String dado = null;
		try {
			dado = ConexaoHttpClient.executaHttpGet(urlGet).toString();//.replaceAll("\\s+", "");
			dado = Pattern.compile("[^0-9]").matcher(dado).replaceAll("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dado;
	}	
	
	public void mensagemExibir(String titulo, String texto){
		AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
		mensagem.setTitle(titulo);
		mensagem.setMessage(texto);
		mensagem.setNeutralButton("OK",null);
		mensagem.show();
	 }			

}
