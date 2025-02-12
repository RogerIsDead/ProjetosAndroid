package model;

import androidx.annotation.NonNull;

import java.util.Date;

public class Reserva {

    private Long idReserva;
    private Long idUsuario;
    private Long idVeiculo;
    private Long idCartao;
    private float valor;
    private String dataRetirada;
    private String dataDevolcucao;

    public Reserva(Long idUsuario, Long idVeiculo, Long idCartao, float valor, String dataRetirada, String dataDevolcucao) {
        this.idUsuario = idUsuario;
        this.idVeiculo = idVeiculo;
        this.idCartao = idCartao;
        this.valor = valor;
        this.dataRetirada = dataRetirada;
        this.dataDevolcucao = dataDevolcucao;
    }

    public Reserva() {
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public Long getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(Long idCartao) {
        this.idCartao = idCartao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(String dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public String getDataDevolcucao() {
        return dataDevolcucao;
    }

    public void setDataDevolcucao(String dataDevolcucao) {
        this.dataDevolcucao = dataDevolcucao;
    }

    @NonNull
    @Override
    public String toString() {
        return "Reserva: " + idReserva + "\n" + "Data da reserva:" + dataRetirada + "\n" + "Data de devolução:" + dataDevolcucao;
    }
}
