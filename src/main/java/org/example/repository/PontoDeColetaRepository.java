package org.example.repository;

import org.example.model.PontoDeColeta;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PontoDeColetaRepository {

    public void cadastrarPontoDeColeta(PontoDeColeta ponto) {
        String sqlPonto = "INSERT INTO pontos_de_coleta (nome, endereco, horario_funcionamento) VALUES (?, ?, ?)";
        String sqlTipoLixo = "INSERT INTO pontos_lixo_aceitos (id_ponto, tipo_lixo) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtPonto = conn.prepareStatement(sqlPonto, Statement.RETURN_GENERATED_KEYS)) {

            stmtPonto.setString(1, ponto.getNome());
            stmtPonto.setString(2, ponto.getEndereco());
            stmtPonto.setString(3, ponto.getHorarioFuncionamento());
            stmtPonto.executeUpdate();

            ResultSet rs = stmtPonto.getGeneratedKeys();
            if (rs.next()) {
                ponto.setId(rs.getInt(1));


                try (PreparedStatement stmtTipoLixo = conn.prepareStatement(sqlTipoLixo)) {
                    for (String tipo : ponto.getTiposLixoAceitos()) {
                        stmtTipoLixo.setInt(1, ponto.getId());
                        stmtTipoLixo.setString(2, tipo);
                        stmtTipoLixo.addBatch();
                    }
                    stmtTipoLixo.executeBatch();
                }
            }
            System.out.println("Ponto de coleta cadastrado com sucesso: " + ponto.getNome());
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar ponto de coleta: " + e.getMessage());
        }
    }

    public List<PontoDeColeta> listarTodosPontos() {
        List<PontoDeColeta> pontos = new ArrayList<>();
        String sql = "SELECT p.*, GROUP_CONCAT(pla.tipo_lixo) as tipos_aceitos " +
                "FROM pontos_de_coleta p LEFT JOIN pontos_lixo_aceitos pla ON p.id = pla.id_ponto " +
                "GROUP BY p.id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String endereco = rs.getString("endereco");
                String horarioFuncionamento = rs.getString("horario_funcionamento");
                String tiposAceitosStr = rs.getString("tipos_aceitos");

                List<String> tiposAceitos = new ArrayList<>();
                if (tiposAceitosStr != null && !tiposAceitosStr.isEmpty()) {
                    tiposAceitos.addAll(Arrays.asList(tiposAceitosStr.split(",")));
                }

                PontoDeColeta ponto = new PontoDeColeta(id, nome, endereco, horarioFuncionamento, tiposAceitos);
                pontos.add(ponto);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pontos de coleta: " + e.getMessage());
        }
        return pontos;
    }

    public List<PontoDeColeta> buscarPontosPorTipoLixo(String tipoLixo) {
        List<PontoDeColeta> pontos = new ArrayList<>();
        String sql = "SELECT p.*, GROUP_CONCAT(pla.tipo_lixo) as tipos_aceitos " +
                "FROM pontos_de_coleta p JOIN pontos_lixo_aceitos pla ON p.id = pla.id_ponto " +
                "WHERE pla.tipo_lixo = ? GROUP BY p.id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tipoLixo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String endereco = rs.getString("endereco");
                String horarioFuncionamento = rs.getString("horario_funcionamento");
                String tiposAceitosStr = rs.getString("tipos_aceitos");

                List<String> tiposAceitos = new ArrayList<>();
                if (tiposAceitosStr != null && !tiposAceitosStr.isEmpty()) {
                    tiposAceitos.addAll(Arrays.asList(tiposAceitosStr.split(",")));
                }

                PontoDeColeta ponto = new PontoDeColeta(id, nome, endereco, horarioFuncionamento, tiposAceitos);
                pontos.add(ponto);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pontos por tipo de lixo: " + e.getMessage());
        }
        return pontos;
    }
}
