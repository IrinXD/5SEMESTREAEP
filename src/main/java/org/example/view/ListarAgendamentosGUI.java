package org.example.view;

import org.example.model.Agendamento;
import org.example.model.Usuario;
import org.example.service.AgendamentoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListarAgendamentosGUI extends JFrame {
    private Usuario usuarioLogado;
    private AgendamentoService agendamentoService;
    private JTable agendamentosTable;
    private DefaultTableModel tableModel;

    public ListarAgendamentosGUI(Usuario usuario) {
        this.usuarioLogado = usuario;
        agendamentoService = new AgendamentoService();

        setTitle("Meus Agendamentos");
        setSize(800, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // exibir os agendamentos
        String[] columnNames = {"ID", "Tipo Lixo", "Volume", "Data/Hora", "Endereço", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        agendamentosTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(agendamentosTable);
        panel.add(scrollPane, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton cancelarButton = new JButton("Cancelar Agendamento Selecionado");
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarAgendamento();
            }
        });
        bottomPanel.add(cancelarButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);
        carregarAgendamentos();
    }

    private void carregarAgendamentos() {
        tableModel.setRowCount(0);
        List<Agendamento> agendamentos = agendamentoService.obterAgendamentosDoUsuario(usuarioLogado.getId());
        for (Agendamento ag : agendamentos) {
            tableModel.addRow(new Object[]{
                    ag.getId(),
                    ag.getTipoLixo(),
                    ag.getVolumeLixo(),
                    ag.getDataHoraColeta(),
                    ag.getEnderecoColeta(),
                    ag.getStatus()
            });
        }
    }

    private void cancelarAgendamento() {
        int selectedRow = agendamentosTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento para cancelar.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int agendamentoId = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja cancelar o agendamento ID: " + agendamentoId + "?", "Confirmar Cancelamento", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (agendamentoService.cancelarAgendamento(agendamentoId, usuarioLogado.getId())) {
                JOptionPane.showMessageDialog(this, "Agendamento cancelado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarAgendamentos();
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível cancelar o agendamento.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}