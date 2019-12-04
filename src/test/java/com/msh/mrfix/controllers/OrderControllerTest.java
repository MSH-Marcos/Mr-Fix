package com.msh.mrfix.controllers;

import com.msh.mrfix.helpers.RetrieveUtil;
import com.msh.mrfix.models.Order;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderControllerTest {

    @Test
    public void givenCorrectDataForOrderCreation_orderIsCreated_thenRetrievedCreatedOrder()
            throws ClientProtocolException, IOException {

        // Given
        int userId = 1;
        int serviceId = 1;

        HttpUriRequest request = RequestBuilder.post()
                .setUri("http://localhost:8080/v1/orders")
                .setHeader(HttpHeaders.ACCEPT, "application/json")
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setEntity(new StringEntity("{\"userId\":\""+userId+"\",\"serviceId\":\""+serviceId+"\"}", ContentType.APPLICATION_JSON))
                .build();

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute( request );

        // Then
        Order resource = RetrieveUtil.retrieveResourceFromResponse(
                response, Order.class);

        assertThat( 45.2f, Matchers.is( resource.getPrice() ) );
    }

}