package com.msh.mrfix.controllers;

import com.msh.mrfix.helpers.RetrieveUtil;
import com.msh.mrfix.models.Service;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ServiceControllerTest {

    @Test
    public void givenCorrectDataForServiceUpdate_serviceIsUpdated_thenRetrievedUpdatedService()
            throws ClientProtocolException, IOException {

        // Given
        String name = "Example for test";
        String description = "Example for test";
        String city = "Valencia";
        Boolean available = true;

        HttpUriRequest request = RequestBuilder.put()
                .setUri("http://localhost:8080/v1/admin/services/1")
                .setHeader(HttpHeaders.ACCEPT, "application/json")
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setEntity(new StringEntity("{\"name\":\""+name+"\",\"description\":\""+description+"\",\"city\":\""+city+"\",\"available\":\""+available+"\"}"
                        , ContentType.APPLICATION_JSON))
                .build();

        // When
        HttpResponse response = HttpClientBuilder.create().build().execute( request );

        // Then
        Service resource = RetrieveUtil.retrieveResourceFromResponse(
                response, Service.class);

        assertThat( "Example for test", Matchers.is(resource.getName()));
    }

}