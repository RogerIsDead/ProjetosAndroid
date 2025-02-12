package model;

import androidx.annotation.NonNull;

public class Cartao {

    private Long idCartao;
    private Long idUsuario;
    private String nomeTitular;
    private String numeroCartao;
    private int codSeguranca;
    private String dataVencimento;

    public Cartao(String nomeTitular, Long idUsuario, String numeroCartao, int codSeguranca, String dataVencimento) {
        this.nomeTitular = nomeTitular;
        this.idUsuario = idUsuario;
        this.numeroCartao = numeroCartao;
        this.codSeguranca = codSeguranca;
        this.dataVencimento = dataVencimento;
    }

    public Cartao() {
    }



    public Long getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(Long idCartao) {
        this.idCartao = idCartao;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public int getCodSeguranca() {
        return codSeguranca;
    }

    public void setCodSeguranca(int codSeguranca) {
        this.codSeguranca = codSeguranca;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @NonNull
    @Override
    public String toString() {
        return "Final do cartão: " + numeroCartao.substring(numeroCartao.length() - 4);
    }
}
