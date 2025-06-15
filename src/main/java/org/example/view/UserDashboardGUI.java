package org.example.view;

import org.example.model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserDashboardGUI extends JFrame {
    private Usuario usuarioLogado;

    public UserDashboardGUI(Usuario usuario) {
        this.usuarioLogado = usuario;

        setTitle("Bem-vindo, " + usuario.getNome() + " - Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 linhas, 1 coluna, espaçamento
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Olá, " + usuario.getNome() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(welcomeLabel);

        JButton agendarColetaButton = new JButton("Agendar Nova Coleta");
        agendarColetaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgendamentoGUI(usuarioLogado).setVisible(true);
            }
        });
        panel.add(agendarColetaButton);

        JButton meusAgendamentosButton = new JButton("Meus Agendamentos e Cancelar");
        meusAgendamentosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListarAgendamentosGUI(usuarioLogado).setVisible(true);
            }
        });
        panel.add(meusAgendamentosButton);

        JButton pontosColetaButton = new JButton("Visualizar Pontos de Coleta");
        pontosColetaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PontosColetaGUI().setVisible(true);
            }
        });
        panel.add(pontosColetaButton);

        JButton logoutButton = new JButton("Sair (Logout)");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(panel, "Tem certeza que deseja sair?", "Confirmar Logout", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new LoginCadastroGUI();
                    dispose();
                }
            }
        });
        panel.add(logoutButton);

        add(panel);
        setVisible(true);
    }
}