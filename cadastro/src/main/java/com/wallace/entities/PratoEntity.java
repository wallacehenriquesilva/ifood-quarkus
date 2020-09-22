package com.wallace.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "prato")
public class PratoEntity extends PanacheEntityBase {

    //TODO -> Criar categoria de prato

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String nome;

    public String descricao;

    public BigDecimal preco;

    @ManyToOne
    public RestauranteEntity restaurante;

    @CreationTimestamp
    public LocalDateTime dataCriacao;

    @UpdateTimestamp
    public LocalDateTime dataAtualizacao;


}
