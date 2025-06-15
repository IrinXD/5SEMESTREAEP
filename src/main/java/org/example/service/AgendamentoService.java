package org.example.service;

import org.example.model.Agendamento;
import org.example.model.Usuario;
import org.example.repository.AgendamentoRepository;
import org.example.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;

public class AgendamentoService {
    private AgendamentoRepository agendamentoRepository;
    private UsuarioRepository usuarioRepository;

    public AgendamentoService() {
        this.agendamentoRepository = new AgendamentoRepository();
        this.usuarioRepository = new UsuarioRepository();
    }

    public Agendamento agendarNovaColeta(int idUsuario, String tipoLixo, String volumeLixo, LocalDateTime dataHoraColeta) {
        Usuario usuario = usuarioRepository.buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            System.out.println("Erro: Usuário não encontrado para agendamento.");
            return null;
        }


        Agendamento novoAgendamento = new Agendamento(
                0, idUsuario, tipoLixo, volumeLixo, dataHoraColeta, usuario.getEndereco(), "Agendado"
        );
        agendamentoRepository.agendarColeta(novoAgendamento);
        return novoAgendamento;
    }

    public List<Agendamento> obterAgendamentosDoUsuario(int idUsuario) {
        return agendamentoRepository.listarAgendamentosPorUsuario(idUsuario);
    }

    public boolean cancelarAgendamento(int idAgendamento, int idUsuario) {
        Agendamento agendamento = agendamentoRepository.buscarAgendamentoPorId(idAgendamento);
        if (agendamento == null || agendamento.getIdUsuario() != idUsuario) {
            System.out.println("Erro: Agendamento não encontrado ou você não tem permissão para cancelá-lo.");
            return false;
        }
        if ("Cancelado".equals(agendamento.getStatus()) || "Concluído".equals(agendamento.getStatus())) {
            System.out.println("Erro: Agendamento já está cancelado ou concluído e não pode ser alterado.");
            return false;
        }
        return agendamentoRepository.cancelarAgendamento(idAgendamento, idUsuario);
    }

    public String gerarComprovante(Agendamento agendamento) {
        if (agendamento == null) {
            return "Erro: Agendamento inválido para gerar comprovante.";
        }
        StringBuilder comprovante = new StringBuilder();
        comprovante.append("--- COMPROVANTE DE AGENDAMENTO ---\n");
        comprovante.append("ID do Agendamento: ").append(agendamento.getId()).append("\n");
        comprovante.append("Tipo de Lixo: ").append(agendamento.getTipoLixo()).append("\n");
        comprovante.append("Volume de Lixo: ").append(agendamento.getVolumeLixo()).append("\n");
        comprovante.append("Data e Hora da Coleta: ").append(agendamento.getDataHoraColeta()).append("\n");
        comprovante.append("Endereço de Coleta: ").append(agendamento.getEnderecoColeta()).append("\n");
        comprovante.append("Status: ").append(agendamento.getStatus()).append("\n");
        comprovante.append("----------------------------------\n");
        return comprovante.toString();
    }

    // Metodo para simular a conclusão de um agendamento (manutenção futura)
    public void concluirAgendamento(int idAgendamento) {
        Agendamento agendamento = agendamentoRepository.buscarAgendamentoPorId(idAgendamento);
        if (agendamento != null && "Agendado".equals(agendamento.getStatus())) {
            agendamentoRepository.atualizarStatusAgendamento(idAgendamento, "Concluído");
            System.out.println("Agendamento " + idAgendamento + " marcado como Concluído.");
        } else {
            System.out.println("Não foi possível concluir o agendamento " + idAgendamento + ". Verifique o ID ou status.");
        }
    }
}