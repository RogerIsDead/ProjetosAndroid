package model;

public class Veiculos {
    private Long cod;
    private String nome;
    private String detalhamento;
    private Double preco;
    private boolean disponivel;
    private String imgUrl;

    public Veiculos() {
    }

    public Veiculos(String nome, String detalhamento, Double valor, boolean quantidade, String imgUrl) {
        this.nome = nome;
        this.detalhamento = detalhamento;
        this.preco = valor;
        this.disponivel = quantidade;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return cod;
    }

    public void setId(Long cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDetalhamento() {
        return detalhamento;
    }

    public void setDetalhamento(String detalhamento) {
        this.detalhamento = detalhamento;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double valor) {
        this.preco = valor;
    }

    public boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
