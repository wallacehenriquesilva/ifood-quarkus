package com.wallace.resources;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.wallace.configurations.CadastroLifeCycleManager;
import com.wallace.resources.models.request.PratoRequest;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@DBRider
//Para carregar datasets e inseri-los em nosso banco. Ele é feito em cima do DBUnit, então, podemos definir uma estratégia ao DBUnit
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE) //Dizemos para usar tudo como lowercase
@QuarkusTest
@QuarkusTestResource(CadastroLifeCycleManager.class)
        //Chama o lifecycle para subir o container do testsContainers
class PratoResourceTests {


    @Test
    @DataSet(value = "datasets/pratos-cenario-1.yml", cleanAfter = true)
    /**
     *
     *  @DataSet -> Da para configurarmos varias coisas, como o que sera executado no começo, no fim, etc.
     *  Podemos usar também o @ExpectedDataSet() passando o arquivo do dataset esperado após as ações do teste.
     */
    void getAllPratos() {
        final String result = RestAssured.given()
                .when().get("/api/v1/restaurantes/123/pratos")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().asString();
        //Valida o retorno se ele e exatamente igual ao que temos que salvar em um arquivo com o padrao
        //nome da classe . (ponto) nome do metodo . (ponto) approved.json
        Approvals.verifyJson(result);
    }

    @Test
    @DataSet("datasets/pratos-cenario-1.yml") //Dataset inicial do teste
    @ExpectedDataSet("datasets/responses/pratos-get-one.yml")
        //Valida de acordo com o dataset de retorno
    void getOneRestaurant() {
        final String result = RestAssured.given()
                .when()
                .get("/api/v1/restaurantes/123/pratos/123")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().asString();
    }


    @Test
    @DataSet(value = "datasets/pratos-cenario-1.yml", cleanAfter = true)
    @ExpectedDataSet("datasets/responses/pratos-create.yml")
        //Valida de acordo com o dataset de retorno se criou na base de dados o novo prato
        //A sequencia das informações na lista também é validada
    void createRestaurant() {
        final PratoRequest pratoRequest = new PratoRequest(
                "Prato 2",
                "Segundo prato",
                new BigDecimal("100.11")
        );

        RestAssured.given()
                .when()
                .header("Content-Type", "application/json")
                .body(pratoRequest)
                .post("/api/v1/restaurantes/123/pratos")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }


    @Test
    @DataSet(value = "datasets/pratos-cenario-1.yml", cleanAfter = true)
    @ExpectedDataSet("datasets/responses/pratos-update.yml")
        //Valida de acordo com o dataset de retorno se realmente alterou no banco
    void updatePrato() {
        final PratoRequest pratoRequest = new PratoRequest(
                "Prato 1 update",
                "Primeiro prato do restaurante atualizado",
                new BigDecimal("52.00")
        );

        RestAssured.given()
                .when()
                .header("Content-Type", "application/json")
                .body(pratoRequest)
                .put("/api/v1/restaurantes/123/pratos/123")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    @DataSet(value = "datasets/pratos-cenario-1.yml", cleanAfter = true)
    @ExpectedDataSet("datasets/responses/pratos-delete.yml")
    void deletePrato() {
        RestAssured.given()
                .when()
                .header("Content-Type", "application/json")
                .delete("/api/v1/restaurantes/123/pratos/123")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

    }

    //TODO -> FAZER TESTES DE ERRO

}
