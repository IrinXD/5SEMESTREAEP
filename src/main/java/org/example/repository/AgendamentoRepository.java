package org.example.repository;

import org.example.model.Agendamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoRepository {

    public void agendarColeta(Agendamento agendamento) {
        String sql = "INSERT INTO agendamentos (id_usuario, tipo_lixo, volume_lixo, data_hora_coleta, endereco_coleta, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, agendamento.getIdUsuario());
            stmt.setString(2, agendamento.getTipoLixo());
            stmt.setString(3, agendamento.getVolumeLixo());
            stmt.setTimestamp(4, Timestamp.valueOf(agendamento.getDataHoraColeta()));
            stmt.setString(5, agendamento.getEnderecoColeta());
            stmt.setString(6, agendamento.getStatus());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                agendamento.setId(rs.getInt(1));
            }
            System.out.println("Agendamento realizado com sucesso! ID: " + agendamento.getId());
        } catch (SQLException e) {
            System.err.println("Erro ao agendar coleta: " + e.getMessage());
        }
    }

    public List<Agendamento> listarAgendamentosPorUsuario(int idUsuario) {
        List<Agendamento> agendamentos = new ArrayList<>();
        String sql = "SELECT * FROM agendamentos WHERE id_usuario = ? ORDER BY data_hora_coleta DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                agendamentos.add(new Agendamento(
                        rs.getInt("id"),
                        rs.getInt("id_usuario"),
                        rs.getString("tipo_lixo"),
                        rs.getString("volume_lixo"),
                        rs.getTimestamp("data_hora_coleta").toLocalDateTime(),
                        rs.getString("endereco_coleta"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar agendamentos do usuário: " + e.getMessage());
        }
        return agendamentos;
    }

    public Agendamento buscarAgendamentoPorId(int idAgendamento) {
        String sql = "SELECT * FROM agendamentos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAgendamento);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Agendamento(
                        rs.getInt("id"),
                        rs.getInt("id_usuario"),
                        rs.getString("tipo_lixo"),
                        rs.getString("volume_lixo"),
                        rs.getTimestamp("data_hora_coleta").toLocalDateTime(),
                        rs.getString("endereco_coleta"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar agendamento por ID: " + e.getMessage());
        }
        return null;
    }

    public boolean cancelarAgendamento(int idAgendamento, int idUsuario) {
        String sql = "UPDATE agendamentos SET status = 'Cancelado' WHERE id = ? AND id_usuario = ? AND status = 'Agendado'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAgendamento);
            stmt.setInt(2, idUsuario);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Agendamento " + idAgendamento + " cancelado com sucesso.");
                return true;
            } else {
                System.out.println("Não foi possível cancelar o agendamento " + idAgendamento + ". Verifique se o ID está correto ou se o agendamento já foi processado/cancelado.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cancelar agendamento: " + e.getMessage());
        }
        return false;
    }

    public void atualizarStatusAgendamento(int idAgendamento, String novoStatus) {
        String sql = "UPDATE agendamentos SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoStatus);
            stmt.setInt(2, idAgendamento);
            stmt.executeUpdate();
            System.out.println("Status do agendamento " + idAgendamento + " atualizado para: " + novoStatus);
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status do agendamento: " + e.getMessage());
        }
    }
}