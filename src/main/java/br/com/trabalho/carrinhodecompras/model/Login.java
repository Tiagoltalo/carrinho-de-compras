package br.com.trabalho.carrinhodecompras.model;

import java.util.List;

public class Login {
    private final List<Usuario> usuarios = Usuario.getUsuarios();

    public Login () {}

    public Usuario validarLogin (String nome, String senha) {
        for (Usuario usuario : usuarios) {
            if(nome.equals(usuario.getNome()) && senha.equals(usuario.getSenha())) {
                return usuario;
            }
        }

        return null;
    }
}
