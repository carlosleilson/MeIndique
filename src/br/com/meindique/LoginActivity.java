package br.com.meindique;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import br.com.meindique.Controller.UsuarioController;
import br.com.meindique.bens.Usuario;
import br.com.meindique.dao.LoginDao;
import br.com.meindique.util.ConexaoHttpClient;
import br.com.meindique.util.ServerHost;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	 private UsuarioController usuarioController; 
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"carlosleilson@gmail.com:123456", "eder@vulgocom.com.br:123456" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	
	//banco login
//	private LoginDao loginDb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		//facebook();
		try {
			testaInicializacao();
		} catch (Exception e) {
			mensagem("Error","Erro inicializando banco de dados");
			e.printStackTrace();
		}
		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	public void testaInicializacao() throws Exception {
		usuarioController = UsuarioController.getInstance(this);
		if (usuarioController.findAll().isEmpty()) {
			Usuario usuario = new Usuario(null, "Carlos Leilson ", "123456","carlosleilson@gmail.com");
			usuarioController.insert(usuario);
			usuario = new Usuario(null, "Gonoronte", "123456","eder@vulgocom.com.br");
			usuarioController.insert(usuario);			
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
//		    HttpClient httpclient = new DefaultHttpClient();
//	        HttpPost httppost = new HttpPost(
//	                "YOUR_ADDRESS_HERE.COM");
//	        String str = null;
//	        String username = login[0];
//	        String password = login[1];
//
//	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//	        nameValuePairs.add(new BasicNameValuePair("username", username));
//	        nameValuePairs.add(new BasicNameValuePair("password", password));
//	        try {
//	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//	        } catch (UnsupportedEncodingException e1) {
//	            e1.printStackTrace();
//	            return false;
			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}
			
			try {
//				for (Usuario usuario : usuarioController.findAll()) {
//					if (usuario.getEmail().toString().equals(mEmail)) {
//						return  usuario.getSenha().toString().equals(mPassword);
//					}
//				}
				return usuario(mEmailView,mPasswordView);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// TODO: register the new account here.
			return false;
		}

		private boolean usuario(EditText mEmailView, EditText mPasswordView) {
			String [] login = new String[1];
			//String urlPost = "http://meindique.esy.es/meIndique/usuarios.php";
			//String urlGet = "http://meindique.esy.es/meIndique/usuarios.php?email="+mEmailView.getText().toString()+"&senha="+mPasswordView.getText().toString();
			
			String urlPost = ServerHost.HOST.getHost()+"meIndique/usuarios.php";
			String urlGet = ServerHost.HOST.getHost()+"meIndique/usuarios.php?email="+mEmailView.getText().toString()+"&senha="+mPasswordView.getText().toString();
			ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
			parametrosPost.add(new BasicNameValuePair("email", mEmailView.getText().toString()));
			parametrosPost.add(new BasicNameValuePair("senha", mPasswordView.getText().toString()));
			String msg = null;
			try {
				//msg = ConexaoHttpClient.executaHttpPost(urlPost,parametrosPost).toString().replaceAll("\\s+", "");
				msg = ConexaoHttpClient.executaHttpGet(urlGet).toString().replaceAll("\\s+", "");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(msg.equals("0"))
				return false;
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);
			if (success == true) {
			     //Do whatever your app does after login
				startActivity(new Intent(LoginActivity.this, MenulojasActivity.class));
			} else {
			    //Let user know login has failed
				mPasswordView.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
//			if (success) {
//				finish();
//			} else {
//				mPasswordView
//						.setError(getString(R.string.error_incorrect_password));
//				mPasswordView.requestFocus();
//			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
			startActivity(new Intent(LoginActivity.this, CadastroLoginActivity.class));
		}
	}
	
	public void cadastrar(View v){
		startActivity(new Intent(LoginActivity.this, CadastroLoginActivity.class));
	}
	
	public void mensagem(String titulo, String texto){
		AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
		mensagem.setTitle(titulo);
		mensagem.setMessage(texto);
		mensagem.setNeutralButton("OK", null);
		mensagem.show();
	}	
}
