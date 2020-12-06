package com.wallace.resources;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.wallace.configurations.CadastroLifeCycleManager;
import com.wallace.resources.models.request.LocalizacaoRequest;
import com.wallace.resources.models.request.RestauranteRequest;
import com.wallace.utils.TokenUtils;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

@DBRider
//Para carregar datasets e inseri-los em nosso banco. Ele é feito em cima do DBUnit, então, podemos definir uma estratégia ao DBUnit
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE) //Dizemos para usar tudo como lowercase
@QuarkusTest
@QuarkusTestResource(CadastroLifeCycleManager.class)
        //Chama o lifecycle para subir o container do testsContainers
class RestauranteResourceTests {

    private String token; //Token que será utilizado pelas requisições

    @BeforeEach
    public void createToken() throws Exception {
        //Cria o token utilizando o TokenUtils, e usando o arquivo JWTProprietarioClaims.json como token.
        token = TokenUtils.generateTokenString("/JWTProprietarioClaims.json", null);
    }


    private RequestSpecification given() {
        return RestAssured.given()
                .header(new Header("Authorization", "Bearer " + token))
                .contentType(ContentType.JSON);
    }


    //TODO -> O CONTAINER SOBE NORMAL
    @Test
    @DataSet(value = "datasets/restaurantes-cenario-1.yml", cleanAfter = true)
    /**
     *
     *  @DataSet -> Da para configurarmos varias coisas, como o que sera executado no começo, no fim, etc.
     *  Podemos usar também o @ExpectedDataSet() passando o arquivo do dataset esperado após as ações do teste.
     */
    void getAllRestaurants() {
        final String result = given()
                .when().get("/api/v1/restaurantes")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().asString();
        //Valida o retorno se ele e exatamente igual ao que temos que salvar em um arquivo com o padrao
        //nome da classe . (ponto) nome do metodo . (ponto) approved.json
        Approvals.verifyJson(result);
    }

    @Test
    @DataSet(value = "datasets/restaurantes-cenario-1.yml", cleanAfter = true)
    void getOneRestaurant() {
        final String result = given()
                .when()
                .get("/api/v1/restaurantes/123")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().asString();
        Approvals.verifyJson(result);
    }


    @Test
    @DataSet(value = "datasets/restaurantes-cenario-1.yml", cleanAfter = true)
    void createRestaurant() {
        final LocalizacaoRequest localizacaoRequest = new LocalizacaoRequest(
                12354.55,
                12354.55
        );

        final RestauranteRequest restauranteRequest = new RestauranteRequest(
                "Wallace Silva",
                "12.352.123/1542-20",
                "Restaurante Update",
                localizacaoRequest
        );

        given()
                .when()
                .header("Content-Type", "application/json")
                .body(restauranteRequest)
                .post("/api/v1/restaurantes")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }


    @Test
    @DataSet(value = "datasets/restaurantes-cenario-1.yml", cleanAfter = true)
    void updateRestaurant() {
        final LocalizacaoRequest localizacaoRequest = new LocalizacaoRequest(
                12354.55,
                12354.55
        );

        final RestauranteRequest restauranteRequest = new RestauranteRequest(
                "Wallace Silva",
                "12.352.123/1542-20",
                "Restaurante Update",
                localizacaoRequest
        );

        given()
                .when()
                .header("Content-Type", "application/json")
                .body(restauranteRequest)
                .put("/api/v1/restaurantes/123")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    @DataSet(value = "datasets/restaurantes-cenario-1.yml", cleanAfter = true)
    void deleteRestaurant() {
        given()
                .when()
                .header("Content-Type", "application/json")
                .delete("/api/v1/restaurantes/123")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    //TODO -> FAZER TESTES DE ERRO


}
