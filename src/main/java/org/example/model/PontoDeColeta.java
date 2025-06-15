package org.example.model;

import java.util.List;
import java.util.ArrayList;

public class PontoDeColeta {
    private int id;
    private String nome;
    private String endereco;
    private String horarioFuncionamento;
    private List<String> tiposLixoAceitos;


    public PontoDeColeta(int id, String nome, String endereco, String horarioFuncionamento, List<String> tiposLixoAceitos) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.horarioFuncionamento = horarioFuncionamento;
        this.tiposLixoAceitos = tiposLixoAceitos;
    }

    // Construtor sem tipos de lixo, para facilitar a criação e adicionar depois (manutenção)
    public PontoDeColeta(int id, String nome, String endereco, String horarioFuncionamento) {
        this(id, nome, endereco, horarioFuncionamento, new ArrayList<>());
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public List<String> getTiposLixoAceitos() {
        return tiposLixoAceitos;
    }

    public void setTiposLixoAceitos(List<String> tiposLixoAceitos) {
        this.tiposLixoAceitos = tiposLixoAceitos;
    }

    public void adicionarTipoLixo(String tipoLixo) {
        if (!this.tiposLixoAceitos.contains(tipoLixo)) {
            this.tiposLixoAceitos.add(tipoLixo);
        }
    }

    @Override
    public String toString() {
        return "PontoDeColeta{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", horarioFuncionamento='" + horarioFuncionamento + '\'' +
                ", tiposLixoAceitos=" + tiposLixoAceitos +
                '}';
    }
}