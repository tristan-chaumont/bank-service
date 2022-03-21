package org.miage.bankservice;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.miage.bankservice.boundary.AccountRepository;
import org.miage.bankservice.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

@TestPropertySource(locations = "classpath:application.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountResourceTests {

    @LocalServerPort
    int port;

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void setupContext() {
        RestAssured.port = port;
        accountRepository.deleteAll();
    }

    @Test
    void debitAccount_NotFound_NotExistingAccount() {
        Response response = when().get("/accounts/0/debit/10")
                .then().statusCode(HttpStatus.SC_OK).extract().response();

        String jsonAsString = response.asString();
        assertThat(jsonAsString, containsString("false"));
    }

    @Test
    void debitAccount_BadRequest_EmptyBalance() {
        Account account = new Account(UUID.randomUUID().toString(),
                "TEST",
                0);
        accountRepository.save(account);
        Response response = when().get("/accounts/TEST/debit/10")
                .then().statusCode(HttpStatus.SC_OK).extract().response();
        String jsonAsString = response.asString();
        assertThat(jsonAsString, containsString("false"));
    }

    @Test
    void debitAccount_Ok() {
        Account account = new Account(UUID.randomUUID().toString(),
                "TEST",
                100);
        accountRepository.save(account);
        Response response = when().get("/accounts/TEST/debit/10")
                .then().statusCode(HttpStatus.SC_OK).extract().response();
        String jsonAsString = response.asString();
        assertThat(jsonAsString, containsString("true"));
    }
}
