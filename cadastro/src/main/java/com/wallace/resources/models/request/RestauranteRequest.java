package com.wallace.resources.models.request;

import com.wallace.entities.RestauranteEntity;
import com.wallace.resources.models.request.validation.ValidModel;
import com.wallace.resources.models.request.validation.ValidRequestModel;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ValidModel
public class RestauranteRequest implements ValidRequestModel {

    @NotEmpty
    @Pattern(regexp = "^[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}\\/[0-9]{4}-[0-9]{2}$", message = "CPNJ inválido.")
    //Usamos o @Schema para documentar a o swagger
    @Schema(description = "CNPJ do restaurante", required = true)
    private String cnpj;

    @NotEmpty
    @Size(min = 3, max = 50)
    @Schema(description = "Nome do restaurante", required = true)
    private String nome;

    @NotEmpty
    @Size(min = 3, max = 50)
    @Schema(description = "Nome do proprietário do restaurante", required = true)
    private String proprietario;

    @NotNull
    @Schema(description = "Localização do restaurante", required = true)
    private LocalizacaoRequest localizacao;


    public RestauranteRequest() {
    }

    public RestauranteRequest(String proprietario, String cnpj, String nome, LocalizacaoRequest localizacao) {
        this.proprietario = proprietario;
        this.cnpj = cnpj;
        this.nome = nome;
        this.localizacao = localizacao;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalizacaoRequest getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(LocalizacaoRequest localizacao) {
        this.localizacao = localizacao;
    }


    /*
    Criação do validador, que vai validar assim que recebermos uma requisição, se o CNPJ já esta cadastrado.
     */
    @Override
    public boolean isValid(ConstraintValidatorContext validatorContext) {

        if (RestauranteEntity.find("cnpj", cnpj).count() > 0) {
            validatorContext.disableDefaultConstraintViolation();

            validatorContext.buildConstraintViolationWithTemplate("CNPJ já cadastrado")
                    .addPropertyNode("cnpj")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
