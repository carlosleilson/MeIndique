package br.com.meindique.dao;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LoginDao extends Activity{
	private SQLiteDatabase bandoDados = null;
	private Cursor cursor;
	
	public LoginDao(SQLiteDatabase classe) {
		try{
			if(classe != null)
				bandoDados =  classe;
			else
				bandoDados = openOrCreateDatabase("MeIndique", MODE_WORLD_READABLE, null);
			String sql = "CREATE TABLE IF NOT EXISTS login"
					+"( id INTEGER PRIMERY KEY, email TEXT,"
					+"telefone TEXT, nome TEXT, senha TEXT);";
			bandoDados.execSQL(sql);
			Log.i("Banco Criado", "Banco criado com sucesso.");
		}catch(Exception error){
			Log.e("Error do banco", "Erro ao criar ou ler o banco: "+error.getMessage());
		}		
	}
	
	public void gravar(String email, String telefone, String nome, String senha,Object classe){
		
		String sql = "INSERT INTO login (email, telefone, nome, senha) values ('"
				+email.toString()+"','"
				+telefone.toString()+"','"
				+nome.toString()+"','"
				+senha.toString()+"')";
		try{
			bandoDados.execSQL(sql);
			Log.i("Alerta", "Cadastrado com sucdesso");
		}catch(Exception error){
			Log.e("Error do banco", "Erro ao gravar dados: "+error.getMessage());
		}		
	}
	
	public void alterar(String id, String email, String telefone, String nome, String senha){
		
		String sql = "UPDATE login SET email'"+email.toString()
				+"', telefone'"+telefone.toString()
				+"', nome'"+nome.toString()
				+"', senha'"+senha.toString()
				+"' WHERE id='"+id+"'";
		try{
			bandoDados.execSQL(sql);
			Log.i("Alerta", "editado com sucdesso");
		}catch(Exception error){
			Log.e("Error do banco", "Erro ao gravar dados: "+error.getMessage());
		}		
	}	

	public void remover(String... id){
		//String sql = "DELETE FROM login WHERE ID = '"+id+"'";
		try{
			//bandoDados.execSQL(sql);
			bandoDados.delete("login", "id", id);
			Log.i("Alerta", "Excluido com sucdesso");
		}catch(Exception error){
			Log.e("Error do banco", "Erro ao gravar dados: "+error.getMessage());
		}		
	}
	
	public boolean buscarDados(){
		try{
			cursor = bandoDados.query("login", 
					new String [] {"id","email", "telefone", "nome", "senha"}, 
					null,//selection, 
					null,//selectionArgs, 
					null,//groupBy, 
					null,//having, 
					null//"nome"//orderBy
					);
			
			
			if(cursor.getCount()!=0){
				cursor.moveToFirst();//posina no primeiro registro.
				return true;
			}else{
				return false;
			}
		}catch(Exception error){
			Log.e("Error do banco", "Erro ao busca dado no banco: "+error.getMessage());
			return false;
		}
	}
	
	public void mostrarRegistroAnteriores(){
		try{
			cursor.moveToPrevious();
		}catch(Exception error){
			Log.e("Error navegar banco", "Erro, não foi possível ir ao primeiro registro: "+error.getMessage());
		}
	}
	
	public void mostraProximoResgistro(){
		try{
			cursor.moveToNext();
		}catch(Exception error){
			Log.e("Error navegar banco", "Erro, não foi possível ir ao proximo registro: "+error.getMessage());
		}		
	}
	
	private void fecharBanco(){
		try{
			bandoDados.close();
		}catch(Exception error){
			Log.e("Error do banco", "Erro ao fechar o banco: "+error.getMessage());
		}		
	}
	
	public Cursor getCursor() {
		return cursor;
	}
	
	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}

	public String[] login(String[] dummyCredentials) {
		
		try{
			cursor = bandoDados.query("login", 
					new String [] {"id","email", "telefone", "nome", "senha"}, 
					null,//selection, 
					null,//selectionArgs, 
					null,//groupBy, 
					null,//having, 
					null//"nome"//orderBy
					);
			
			return cursor.getColumnNames();
		
		}catch(Exception error){
			Log.e("Error do banco", "Erro ao busca dado no banco: "+error.getMessage());
		}		
		return null;
	}
}

