package com.icptechno.admincore.connector;

public interface APIClient {

    <T> T postForObject(final String uri, Object request, Class<T> responseType);

//    <T> T getForObject(final String uri, final Object request, final Class<T> responseType, final String param);
}
