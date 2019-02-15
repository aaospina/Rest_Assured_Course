package com.responseextraction.rootpath;

import io.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.*; //Added manually

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class RootPathExamples2 {

    static HashMap<String, Object> parameters = new HashMap<String, Object>();

    @BeforeClass
    public static void init(){
        RestAssured.baseURI = "https://query.yahooapis.com";
        RestAssured.basePath = "/v1/public";
        RestAssured.rootPath="query.results.rate";

        parameters.put("q","select * from yahoo.finance.xchange where pair in (\"USDTHB\", \"USDINR\",\"USDCAD\",\"USDAUD\",\"USDEUR\",\"USDBRL\")");
        parameters.put("format", "json");
        parameters.put("env", "store://datatables.org/alltableswithkeys");

    }

    @AfterClass
    public static void tearDown(){
        RestAssured.reset();
    }

    // 2) Assert on single Name
    @Test
    public void test002(){
        given()
                .parameters(parameters)
                .when()
                .get("/yql")
                .then()
                .body("Name", hasItem("USD/INR"));

    }

    // 3) Assert on Multiple Names
    @Test
    public void test003(){
        given()
                .parameters(parameters)
                .when()
                .get("/yql")
                .then()
                .body("Name", hasItems("USD/INR", "USD/THB", "USD/BRL"));

    }

    // 4) Assert Multiple assertions in single test
    @Test
    public void test004(){
        given()
                .parameters(parameters)
                .when()
                .get("/yql")
                .then()
                .body("Name", hasItem("USD/INR"))
                .body("id", hasItem("USDCAD"))
                .body("Name", hasItems("USD/INR", "USD/THB", "USD/BRL"));

    }
}
