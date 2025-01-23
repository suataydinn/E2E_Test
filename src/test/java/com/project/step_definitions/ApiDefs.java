package com.project.step_definitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import com.project.pages_api.ApiPage;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class ApiDefs {


    ApiPage apiPage = new ApiPage();

    private JSONObject bookingBody;
    private Response response;
    private String authToken;

}
