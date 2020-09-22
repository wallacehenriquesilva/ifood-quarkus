package com.wallace.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table(name = "restaurante")
public class RestauranteEntity extends PanacheEntityBase {
    /**
     * No panache, não precisa colocar como private,
     * podemos deixar como public, que o panache vai em tempo
     * de compilação modificar o bytecode para private e
     * criar os getters e setters.
     */

    //TODO -> Criar tipo

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String proprietario;

    public String cnpj;

    public String nome;

    @OneToOne(cascade = CascadeType.ALL)
    public LocalizacaoEntity localizacao;

    @CreationTimestamp
    public LocalDateTime dataCriacao;

    @UpdateTimestamp
    public LocalDateTime dataAtualizacao;


}
