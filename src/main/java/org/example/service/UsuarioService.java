package org.example.service;

import org.example.model.Usuario;
import org.example.repository.UsuarioRepository;

public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public boolean realizarCadastro(String nome, String email, String senha, String endereco) {
        if (usuarioRepository.buscarUsuarioPorEmail(email) != null) {
            System.out.println("Erro: Já existe um usuário com este e-mail.");
            return false;
        }
        Usuario novoUsuario = new Usuario(0, nome, email, senha, endereco);
        usuarioRepository.cadastrarUsuario(novoUsuario);
        return novoUsuario.getId() > 0;
    }

    public Usuario realizarLogin(String email, String senha) {
        Usuario usuario = usuarioRepository.buscarUsuarioPorEmail(email);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            System.out.println("Login bem-sucedido para: " + usuario.getNome());
            return usuario;
        } else {
            System.out.println("Erro: E-mail ou senha inválidos.");
            return null;
        }
    }

    public Usuario buscarUsuarioPorId(int id) {
        return usuarioRepository.buscarUsuarioPorId(id);
    }
}