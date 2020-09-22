package com.wallace.resources.models.request;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class PratoRequest {

    @NotEmpty
    @Size(min = 3, max = 50)
    @Schema(description = "Nome do prato", required = true)
    private String nome;

    @NotEmpty
    @Size(min = 5, max = 100)
    @Schema(description = "Descrição do prato", required = true)
    private String descricao;

    @NotNull
    @Schema(description = "Valor do prato", required = true)
    private BigDecimal preco;

    public PratoRequest() {
    }

    public PratoRequest(String nome, String descricao, BigDecimal preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
