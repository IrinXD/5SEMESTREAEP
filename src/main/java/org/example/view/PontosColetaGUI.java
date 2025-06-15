package org.example.view;

import org.example.model.PontoDeColeta;
import org.example.service.PontoDeColetaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PontosColetaGUI extends JFrame {
    private PontoDeColetaService pontoDeColetaService;
    private JTable pontosTable;
    private DefaultTableModel tableModel;

    public PontosColetaGUI() {
        pontoDeColetaService = new PontoDeColetaService();

        setTitle("Pontos de Coleta Disponíveis");
        setSize(700, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"ID", "Nome", "Endereço", "Horário", "Tipos Aceitos"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        pontosTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(pontosTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
        carregarPontosDeColeta();
    }

    private void carregarPontosDeColeta() {
        tableModel.setRowCount(0);
        List<PontoDeColeta> pontos = pontoDeColetaService.listarTodosPontosDeColeta();
        for (PontoDeColeta ponto : pontos) {
            tableModel.addRow(new Object[]{
                    ponto.getId(),
                    ponto.getNome(),
                    ponto.getEndereco(),
                    ponto.getHorarioFuncionamento(),
                    String.join(", ", ponto.getTiposLixoAceitos())
            });
        }
        if (pontos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum ponto de coleta cadastrado.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}