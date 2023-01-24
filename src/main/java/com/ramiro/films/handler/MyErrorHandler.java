package com.ramiro.films.handler;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

public class MyErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {

        return response.getStatusCode() != HttpStatus.OK;
    }

    /***
     * This method is empty because the handle is in
     * @MyErrorHandler.handleError(URI url, HttpMethod method, ClientHttpResponse response)
     */
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        throw new IOException(
                String.format("Ocorreu o erro [StatusCode %d %s] na chamada [URL %S %s]."
                        , response.getStatusCode().value()
                        , response.getStatusText()
                        , method.toString()
                        , url.toString())
        );
    }

}
