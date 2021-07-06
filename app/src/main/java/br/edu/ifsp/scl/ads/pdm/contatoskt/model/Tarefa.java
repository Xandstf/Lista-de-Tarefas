package br.edu.ifsp.scl.ads.pdm.contatoskt.model;

import java.io.Serializable;

public class Tarefa implements Serializable {
    private String titulo;
    private String descricao;
    private String autor;
    private String criada;
    private String termina;
    private boolean completa;
    private String responsavel;

    public Tarefa() {
    }

    public Tarefa(String titulo, String descricao, String autor, String criada, String termina, boolean completa, String responsavel) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.autor = autor;
        this.criada = criada;
        this.termina = termina;
        this.completa = completa;
        this.responsavel = responsavel;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCriada() {
        return criada;
    }

    public void setCriada(String criada) {
        this.criada = criada;
    }

    public String getTermina() {
        return termina;
    }

    public void setTermina(String termina) {
        this.termina = termina;
    }

    public boolean isCompleta() {
        return completa;
    }

    public void setCompleta(boolean completa) {
        this.completa = completa;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", autor='" + autor + '\'' +
                ", criada='" + criada + '\'' +
                ", termina='" + termina + '\'' +
                ", completa=" + completa +
                ", responsavel='" + responsavel + '\'' +
                '}';
    }
}
