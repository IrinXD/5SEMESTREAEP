package org.example.service;

import org.example.model.PontoDeColeta;
import org.example.repository.PontoDeColetaRepository;

import java.util.List;

public class PontoDeColetaService {
    private PontoDeColetaRepository pontoDeColetaRepository;

    public PontoDeColetaService() {
        this.pontoDeColetaRepository = new PontoDeColetaRepository();
    }

    public void cadastrarNovoPonto(String nome, String endereco, String horarioFuncionamento, List<String> tiposLixoAceitos) {
        PontoDeColeta novoPonto = new PontoDeColeta(0, nome, endereco, horarioFuncionamento, tiposLixoAceitos);
        pontoDeColetaRepository.cadastrarPontoDeColeta(novoPonto);
    }

    public List<PontoDeColeta> listarTodosPontosDeColeta() {
        return pontoDeColetaRepository.listarTodosPontos();
    }

    public List<PontoDeColeta> buscarPontosPorTipo(String tipoLixo) {
        return pontoDeColetaRepository.buscarPontosPorTipoLixo(tipoLixo);
    }
}
