package org.example.model;

import java.time.LocalDateTime;

public class Agendamento {
    private int id;
    private int idUsuario;
    private String tipoLixo;
    private String volumeLixo;
    private LocalDateTime dataHoraColeta;
    private String enderecoColeta;
    private String status;


    public Agendamento(int id, int idUsuario, String tipoLixo, String volumeLixo, LocalDateTime dataHoraColeta, String enderecoColeta, String status) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.tipoLixo = tipoLixo;
        this.volumeLixo = volumeLixo;
        this.dataHoraColeta = dataHoraColeta;
        this.enderecoColeta = enderecoColeta;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipoLixo() {
        return tipoLixo;
    }

    public void setTipoLixo(String tipoLixo) {
        this.tipoLixo = tipoLixo;
    }

    public String getVolumeLixo() {
        return volumeLixo;
    }

    public void setVolumeLixo(String volumeLixo) {
        this.volumeLixo = volumeLixo;
    }

    public LocalDateTime getDataHoraColeta() {
        return dataHoraColeta;
    }

    public void setDataHoraColeta(LocalDateTime dataHoraColeta) {
        this.dataHoraColeta = dataHoraColeta;
    }

    public String getEnderecoColeta() {
        return enderecoColeta;
    }

    public void setEnderecoColeta(String enderecoColeta) {
        this.enderecoColeta = enderecoColeta;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Agendamento{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", tipoLixo='" + tipoLixo + '\'' +
                ", volumeLixo='" + volumeLixo + '\'' +
                ", dataHoraColeta=" + dataHoraColeta +
                ", enderecoColeta='" + enderecoColeta + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
