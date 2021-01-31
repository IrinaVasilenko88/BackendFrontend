package ru.netology.test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;

import java.sql.DriverManager;
import java.sql.SQLException;

import static io.restassured.RestAssured.given;

public class ApiTest {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    val firstCardBalance;
    val secondCardBalance;
    int sum = 5000;

    @Test
    void shouldMakeTransfer() throws SQLException {
        given()
                .spec(requestSpec)
                .body(DataHelper.getAuthInfo())
                .when()
                .post("/api/auth")
                .then() //
                .statusCode(200);
        val codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        val runner = new QueryRunner();
        try
                (val conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass"))
        {
            val code = runner.query(conn, codeSQL, new ScalarHandler<String>());
            return code;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();

        }
    }
}