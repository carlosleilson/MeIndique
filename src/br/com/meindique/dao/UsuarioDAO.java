package br.com.meindique.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import br.com.meindique.bens.Usuario;
import br.com.meindique.util.DataBase;

public class UsuarioDAO extends DataBase {
	private final String TABLE = "usuario";

	// public UsuarioDAO(Context context) {
	// super(context);
	// }

	public UsuarioDAO(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
	}

	public void insert(Usuario usuario) throws Exception {
		ContentValues values = new ContentValues();

		values.put("nome", usuario.getNome());
		values.put("senha", usuario.getSenha());
		values.put("email", usuario.getEmail());
		
		getDatabase().insert(TABLE, null, values);
	}

	public void update(Usuario usuario) throws Exception {
		ContentValues values = new ContentValues();

		values.put("nome", usuario.getNome());
		values.put("senha", usuario.getSenha());
		values.put("email", usuario.getEmail());

		getDatabase().update(TABLE, values, "id = ?",
				new String[] { "" + usuario.getId() });
	}

	public Usuario findById(Integer id) {

		String sql = "SELECT * FROM " + TABLE + " WHERE id = ?";
		String[] selectionArgs = new String[] { "" + id };
		Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
		cursor.moveToFirst();

		return montaUsuario(cursor);
	}

	public List<Usuario> findAll() throws Exception {
		List<Usuario> retorno = new ArrayList<Usuario>();
		String sql = "SELECT * FROM " + TABLE;
		Cursor cursor = getDatabase().rawQuery(sql, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			retorno.add(montaUsuario(cursor));
			cursor.moveToNext();
		}
		return retorno;
	}

	public Usuario montaUsuario(Cursor cursor) {
		if (cursor.getCount() == 0) {
			return null;
		}
		Integer id = cursor.getInt(cursor.getColumnIndex("id"));
		String nome = cursor.getString(cursor.getColumnIndex("nome"));
		String senha = cursor.getString(cursor.getColumnIndex("senha"));
		String email = cursor.getString(cursor.getColumnIndex("email"));

		return new Usuario(id, nome, senha, email);

	}

	/**
	 * @param usuario
	 * @param senha
	 * @return
	 */
	public Usuario findByLogin(String usuario, String senha) {
		String sql = "SELECT * FROM " + TABLE
				+ " WHERE usuario = ? AND senha = ?";
		String[] selectionArgs = new String[] { usuario, senha };
		Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
		cursor.moveToFirst();

		return montaUsuario(cursor);
	}
}
