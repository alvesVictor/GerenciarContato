package br.com.trabalho.fatec.gerenciarcontato.Bean;

import java.io.Serializable;

/**
 * Created by Victor on 12/11/2017.
 */

public class ContatoBean implements Serializable {
    private Integer id;
    private String nome;

    public ContatoBean(String nome, String site, String telefone, String endereco, String foto, String email) {
        this.nome = nome;
        this.site = site;
        this.telefone = telefone;
        this.endereco = endereco;
        this.foto = foto;
        this.email = email;
    }

    private String site;
    private String telefone;
    private String endereco;
    private String foto;
    private String email;

    public ContatoBean(Integer id, String nome, String site, String telefone, String endereco, String foto, String email) {
        this.id = id;
        this.nome = nome;
        this.site = site;
        this.telefone = telefone;
        this.endereco = endereco;
        this.foto = foto;
        this.email = email;
    }
    public ContatoBean(){

    }

    @Override
    public String toString() {
        return "Nome: "+ nome + "    " +"Telefone: "+ telefone;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
