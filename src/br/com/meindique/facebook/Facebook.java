package br.com.meindique.facebook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import br.com.meindique.R;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class Facebook extends Activity {
	 
//	private static final String APP_ID = String.valueOf(R.string.app_id);//"ID_SUA_APP";
//	  
//	private static final String ACCESS_EXPIRES = "access_expires";
//	private static final String ACCESS_TOKEN = "access_token";
//	 
//	private Facebook facebook;
//	private SharedPreferences prefs;	
//	
//	public void onfacebook(){
//	    facebook = new Facebook(APP_ID);
//	    prefs = getPreferences(MODE_PRIVATE);
//	    // Carrega a accessToken pra saber se o usuÃ¡rio
//	    // jÃ¡ se autenticou.
//	    loadAccessToken();
//	 
//	    // Caso nÃ£o tenha se autenticado, abre o login
//	    if(!facebook.isSessionValid()) {
//	 
//	      // Chama a tela de login do facebook
//	      facebook.authorize(this, new String[] {"publish_stream"}, new DialogListener() {
//	            // Login com sucesso, salva o accessToken
//	            public void onComplete(Bundle values) {
//	              saveAccessToken();
//	            }
//	            // Os mÃ©todos abaixo devem ser 
//	            // implementados para tratatmento dos
//	            // fluxos alternativos
//	            public void onFacebookError(
//	              FacebookError error) {}
//	     
//	            public void onError(DialogError e) {}
//	     
//	            public void onCancel() {}
//	          });
//	    }
//	}	
//	
//	 public void onActivityResult(
//			    int requestCode, int resultCode, Intent data) {
//			    super.onActivityResult(
//			      requestCode, resultCode, data);
//			    // A API do Facebook exige essa chamada para 
//			    // concluir o processo de login.
//			    facebook.authorizeCallback(
//			      requestCode, resultCode, data);
//			  }
//			   
//			  // Evento de clique do botÃ£o para atualizar o status
////			  public void updateStatusClick(View v){
////			    EditText edt = (EditText)
////			      findViewById(R.id.editText1);
////			    updateStatus(edt.getText().toString()); 
////			  }
//			 
//			  private RequestListener requestListener = 
//			    new RequestListener() {
//			      public void onMalformedURLException(
//			        MalformedURLException e, Object state) {
//			        showToast("URL mal formada");
//			      }
//			      public void onIOException(
//			        IOException e, Object state) {
//			        showToast("Problema de comunicaÃ§Ã£o");
//			      }
//			      public void onFileNotFoundException(
//			        FileNotFoundException e, Object state) {
//			        showToast("Recurso nÃ£o existe");
//			      }
//			      public void onFacebookError(
//			        FacebookError e, Object state) {
//			        showToast("Erro no Facebook: "+ 
//			          e.getLocalizedMessage());
//			      }
//			      public void onComplete(
//			        String response, Object state) {
//			        showToast("Status atualizado");
//			      }
//			    };
//			 
//			  // MÃ©todo que efetivamente atualiza o status
//			  private void updateStatus(String status) {
//			    AsyncFacebookRunner runner = 
//			      new AsyncFacebookRunner(facebook);
//			   
//			    Bundle params = new Bundle(); 
//			    params.putString("message", status);
//			    runner.request("me/feed", params, "POST", 
//			      requestListener, null);
//			  }
//			  
//			  private void showToast(final String s){
//			    final Context ctx = this;
//			    runOnUiThread(new Runnable() {
//			      public void run() {
//			        Toast.makeText(ctx, s, 
//			          Toast.LENGTH_SHORT).show();
//			      }
//			    });
//			  }
//			 
//			  private void saveAccessToken() {
//			    SharedPreferences.Editor editor = prefs.edit();
//			    editor.putString(
//			      ACCESS_TOKEN, facebook.getAccessToken());
//			    editor.putLong(
//			      ACCESS_EXPIRES, facebook.getAccessExpires());
//			    editor.commit();
//			  }
//			 
//			  private void loadAccessToken() {
//			    String access_token = 
//			      prefs.getString(ACCESS_TOKEN, null);
//			    long expires = prefs.getLong(ACCESS_EXPIRES, 0);
//			    if(access_token != null) {
//			      facebook.setAccessToken(access_token);
//			    }
//			    if(expires != 0) {
//			      facebook.setAccessExpires(expires);
//			    }
//			  }	
}
