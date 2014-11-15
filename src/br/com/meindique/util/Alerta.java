package br.com.meindique.util;

import android.app.AlertDialog;
import android.content.Context;

public class Alerta {
	public void mensagem(String titulo, String texto,Object context){
		AlertDialog.Builder mensagem = new AlertDialog.Builder((Context) context);
		mensagem.setTitle(titulo);
		mensagem.setMessage(texto);
		mensagem.setNeutralButton("OK", null);
		mensagem.show();
	}
}
