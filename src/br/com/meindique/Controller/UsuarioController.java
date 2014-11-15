package br.com.meindique.Controller;

import java.util.List;

import android.content.Context;
import br.com.meindique.bens.Usuario;
import br.com.meindique.dao.UsuarioDAO;

public class UsuarioController {
	private final static int VERSAO = 1;
	private final static String NOME = "MeIndique";
	private static UsuarioDAO usuarioDAO;
	private static UsuarioController instance;

	public static UsuarioController getInstance(Context context) {
		if (instance == null) {
			instance = new UsuarioController();
			usuarioDAO = new UsuarioDAO(context,NOME,null,VERSAO);
		}
		return instance;
	}

	public void insert(Usuario usuario) throws Exception {
		usuarioDAO.insert(usuario);
	}

	public void update(Usuario usuario) throws Exception {
		usuarioDAO.update(usuario);
	}

	public List<Usuario> findAll() throws Exception {
		return usuarioDAO.findAll();
	}

	public boolean validaLogin(String usuario, String senha) throws Exception {
		Usuario user = usuarioDAO.findByLogin(usuario, senha);
		if (user == null || user.getNome() == null || user.getSenha() == null) {
			return false;
		}
		String informado = usuario + senha;
		String esperado = user.getNome() + user.getSenha();
		if (informado.equals(esperado)) {
			return true;
		}
		return false;

	}
}
