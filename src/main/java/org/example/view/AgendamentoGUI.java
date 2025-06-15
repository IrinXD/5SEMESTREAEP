package org.example.view;

import org.example.model.Agendamento;
import org.example.model.Usuario;
import org.example.service.AgendamentoService;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoGUI extends JFrame {
    private Usuario usuarioLogado;
    private AgendamentoService agendamentoService;
    private JComboBox<String> tipoLixoComboBox;
    private JTextField volumeLixoField;
    private JDateChooser dateChooser;
    private JComboBox<String> hourComboBox;
    private JComboBox<String> minuteComboBox;

    public AgendamentoGUI(Usuario usuario) {
        this.usuarioLogado = usuario;
        agendamentoService = new AgendamentoService();

        setTitle("Agendar Coleta Domiciliar");
        setSize(450, 350);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Tipo de Lixo:"));
        String[] tiposLixo = {"Pilhas", "Medicamentos", "Baterias", "Recicláveis"};
        tipoLixoComboBox = new JComboBox<>(tiposLixo);
        panel.add(tipoLixoComboBox);

        panel.add(new JLabel("Volume do Lixo (Ex: 5kg, Médio):"));
        volumeLixoField = new JTextField();
        panel.add(volumeLixoField);

        panel.add(new JLabel("Data da Coleta:"));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        panel.add(dateChooser);

        panel.add(new JLabel("Hora da Coleta:"));
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

        String[] hours = new String[10];
        for (int i = 0; i < 10; i++) {
            hours[i] = String.format("%02d", 9 + i);
        }
        hourComboBox = new JComboBox<>(hours);
        timePanel.add(hourComboBox);

        timePanel.add(new JLabel(":"));

        List<String> minutesList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            minutesList.add(String.format("%02d", i));
        }
        minuteComboBox = new JComboBox<>(minutesList.toArray(new String[0]));
        timePanel.add(minuteComboBox);

        panel.add(timePanel);

        JButton agendarButton = new JButton("Agendar Coleta");
        agendarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agendarColeta();
            }
        });
        panel.add(agendarButton);

        add(panel);
    }

    private void agendarColeta() {
        String tipoLixo = (String) tipoLixoComboBox.getSelectedItem();
        String volumeLixo = volumeLixoField.getText();

        Date selectedDate = dateChooser.getDate();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma data para a coleta.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int hour = Integer.parseInt((String) hourComboBox.getSelectedItem());
        int minute = Integer.parseInt((String) minuteComboBox.getSelectedItem());

        LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalTime localTime = LocalTime.of(hour, minute);
        LocalDateTime dataHoraColeta = LocalDateTime.of(localDate, localTime);

        if (tipoLixo.isEmpty() || volumeLixo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tipo de lixo e volume são obrigatórios!", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // não permitir agendar para o passado
        if (dataHoraColeta.isBefore(LocalDateTime.now())) {
            JOptionPane.showMessageDialog(this, "Não é possível agendar coletas para o passado.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Agendamento novoAgendamento = agendamentoService.agendarNovaColeta(
                usuarioLogado.getId(), tipoLixo, volumeLixo, dataHoraColeta
        );

        if (novoAgendamento != null) {
            JOptionPane.showMessageDialog(this, "Agendamento realizado com sucesso!\n" + agendamentoService.gerarComprovante(novoAgendamento), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            volumeLixoField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Falha ao agendar coleta. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}