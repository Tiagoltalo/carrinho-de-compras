package br.com.trabalho.carrinhodecompras.model;

import java.util.List;
import java.util.ArrayList;

public class Usuario {
    private static List<Usuario> usuarios = new ArrayList<>();
    private int id;
    private String nome;
    private String senha;

    public Usuario (int id, String nome, String senha) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
    }

    public static void adicionarUsuarioALista (Usuario usuario) {
        usuarios.add(usuario);
    }

    public static int buscarUltimoId (List<Usuario> usuarios) {
        int ultimoId = 1;

        for (Usuario usuario : usuarios) {
            if (ultimoId < usuario.getId()) {
                ultimoId = usuario.getId();
            }
        }

        return ultimoId;
    }

    public int getId () { return this.id; }
    public String getNome () { return this.nome; }
    public String getSenha () { return this.senha; }
    public static List<Usuario> getUsuarios () { return usuarios; }
}
