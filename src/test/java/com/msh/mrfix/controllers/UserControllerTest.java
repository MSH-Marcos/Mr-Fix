package com.msh.mrfix.controllers;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Test
    public void givenUserDoesNotExists_whenUserInfoIsRetrieved_then401IsReceived()
            throws ClientProtocolException, IOException {

        // Given
        String name = RandomStringUtils.randomAlphabetic( 8 );
        String password = RandomStringUtils.randomAlphanumeric(8);

        HttpUriRequest request = RequestBuilder.get()
                .setUri("http://localhost:8080/v1/users/login")
                .setHeader(HttpHeaders.ACCEPT, "application/json")
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setEntity(new StringEntity("{\"name\":\""+name+"\",\"password\":\""+password+"\"}", ContentType.APPLICATION_JSON))
                .build();

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Then
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_UNAUTHORIZED));
    }

    @Test
    public void givenUserDoesExists_whenUserInfoIsRetrieved_then200IsReceived()
            throws ClientProtocolException, IOException {

        // Given
        String name = "Marcos";
        String password = "123";

        HttpUriRequest request = RequestBuilder.get()
                .setUri("http://localhost:8080/v1/users/login")
                .setHeader(HttpHeaders.ACCEPT, "application/json")
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setEntity(new StringEntity("{\"name\":\""+name+"\",\"password\":\""+password+"\"}", ContentType.APPLICATION_JSON))
                .build();

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Then
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void withoutGivenUser_whenUserInfoIsRetrieved_then400IsReceived()
            throws ClientProtocolException, IOException {

        // Given
        HttpUriRequest request = RequestBuilder.get()
                .setUri("http://localhost:8080/v1/users/login")
                .setHeader(HttpHeaders.ACCEPT, "application/json")
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Then
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_BAD_REQUEST));
    }
}