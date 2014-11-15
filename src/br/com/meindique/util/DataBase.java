package br.com.meindique.util;

import br.com.meindique.dao.UsuarioDAO;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
	private final static int VERSAO = 1;
	private final static String NOME = "MeIndique.db";
	private static final String CREATE = "CREATE TABLE usuario (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, email TEXT NOT NULL, telefone TEXT, senha VARCHAR( 32 ) NOT NULL);";
	protected SQLiteDatabase database;

	public DataBase(Context context, String name, CursorFactory factory, int version) {
		super(context, NOME, factory, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    db.execSQL("DROP TABLE IF EXISTS usuario" );
	      this.onCreate(db);
	}

	public SQLiteDatabase getDatabase() {
		if (database == null) {
			database = getWritableDatabase();
		}
		return database;
	}

}
