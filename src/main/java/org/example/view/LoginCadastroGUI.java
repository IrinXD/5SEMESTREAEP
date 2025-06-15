package org.example.view;

import org.example.model.Usuario;
import org.example.service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginCadastroGUI extends JFrame {
    private UsuarioService usuarioService;
    private JTextField emailLoginField;
    private JPasswordField senhaLoginField;
    private JTextField nomeCadastroField;
    private JTextField emailCadastroField;
    private JPasswordField senhaCadastroField;
    private JTextField enderecoCadastroField;

    public LoginCadastroGUI() {
        usuarioService = new UsuarioService();

        setTitle("Software de Controle de Lixo - Login/Cadastro");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Painel de Login
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        loginPanel.setBorder(BorderFactory.createTitledBorder("Login"));

        loginPanel.add(new JLabel("Email:"));
        emailLoginField = new JTextField();
        loginPanel.add(emailLoginField);

        loginPanel.add(new JLabel("Senha:"));
        senhaLoginField = new JPasswordField();
        loginPanel.add(senhaLoginField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fazerLogin();
            }
        });
        loginPanel.add(loginButton);
        loginPanel.add(new JLabel("")); // Espaçamento

        // Painel de Cadastro
        JPanel cadastroPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        cadastroPanel.setBorder(BorderFactory.createTitledBorder("Cadastro"));

        cadastroPanel.add(new JLabel("Nome:"));
        nomeCadastroField = new JTextField();
        cadastroPanel.add(nomeCadastroField);

        cadastroPanel.add(new JLabel("Email:"));
        emailCadastroField = new JTextField();
        cadastroPanel.add(emailCadastroField);

        cadastroPanel.add(new JLabel("Senha:"));
        senhaCadastroField = new JPasswordField();
        cadastroPanel.add(senhaCadastroField);

        cadastroPanel.add(new JLabel("Endereço:"));
        enderecoCadastroField = new JTextField();
        cadastroPanel.add(enderecoCadastroField);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario();
            }
        });
        cadastroPanel.add(cadastrarButton);
        cadastroPanel.add(new JLabel(""));

        mainPanel.add(loginPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(cadastroPanel);

        add(mainPanel);
        setVisible(true);
    }

    private void fazerLogin() {
        String email = emailLoginField.getText();
        String senha = new String(senhaLoginField.getPassword());

        Usuario usuarioLogado = usuarioService.realizarLogin(email, senha);

        if (usuarioLogado != null) {
            JOptionPane.showMessageDialog(this, "Login bem-sucedido! Bem-vindo, " + usuarioLogado.getNome() + "!");
            new UserDashboardGUI(usuarioLogado);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Email ou senha inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cadastrarUsuario() {
        String nome = nomeCadastroField.getText();
        String email = emailCadastroField.getText();
        String senha = new String(senhaCadastroField.getPassword());
        String endereco = enderecoCadastroField.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || endereco.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos de cadastro são obrigatórios!", "Erro de Cadastro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (usuarioService.realizarCadastro(nome, email, senha, endereco)) {
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso! Agora você pode fazer login.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            // Limpa os campos de cadastro após o sucesso
            nomeCadastroField.setText("");
            emailCadastroField.setText("");
            senhaCadastroField.setText("");
            enderecoCadastroField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Falha no cadastro. Verifique se o e-mail já está em uso.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }
}