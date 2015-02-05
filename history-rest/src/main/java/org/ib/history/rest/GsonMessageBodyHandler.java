/*
 * Copyright (c) 2014 Giesecke & Devrient. All rights reserved.
 */
package org.ib.history.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;

/**
 * from: http://eclipsesource.com/blogs/2012/11/02/integrating-gson-into-a-jax-rs-based-application/
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class GsonMessageBodyHandler implements MessageBodyWriter<Object>, MessageBodyReader<Object> {

    private static final String UTF_8 = "UTF-8";

    private final Gson gson;

    public GsonMessageBodyHandler() {
        final GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.disableHtmlEscaping();
//        gsonBuilder.setPrettyPrinting();

        gsonBuilder.registerTypeAdapter(Optional.class, new GsonOptionalDeserializer<>());

        gson = gsonBuilder.create();
    }

    public GsonMessageBodyHandler(
            Optional<Map<Class<?>, Object>> typeAdapters,
            Optional<Map<Class<?>, Object>> typeHierarchyAdapters) {

        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.disableHtmlEscaping();
        gsonBuilder.setPrettyPrinting();

        if (typeAdapters.isPresent()) {
            for (Map.Entry<Class<?>, Object> typeAdapter : typeAdapters.get().entrySet()) {
                gsonBuilder.registerTypeAdapter(typeAdapter.getKey(), typeAdapter.getValue());
            }
        }

        if (typeHierarchyAdapters.isPresent()) {
            for (Map.Entry<Class<?>, Object> typeHierarchyAdapter : typeHierarchyAdapters.get().entrySet()) {
                gsonBuilder.registerTypeHierarchyAdapter(typeHierarchyAdapter.getKey(), typeHierarchyAdapter.getValue());
            }
        }

        gson = gsonBuilder.create();
    }

    private Gson getGson() {
        return gson;
    }

    @Override
    public boolean isReadable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        return true;
    }

    @Override
    public Object readFrom(final Class<Object> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, String> httpHeaders, final InputStream entityStream) throws IOException, WebApplicationException {
        try (InputStreamReader streamReader = new InputStreamReader(entityStream, UTF_8)) {
            final Type jsonType = type.equals(genericType) ? type : genericType;
            return getGson().fromJson(streamReader, jsonType);
        }
    }

    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        return true;
    }

    @Override
    public long getSize(final Object object, final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(final Object object, final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, Object> httpHeaders, final OutputStream entityStream) throws IOException, WebApplicationException {
        try (OutputStreamWriter writer = new OutputStreamWriter(entityStream, UTF_8)) {
            final Type jsonType = type.equals(genericType) ? type : genericType;
            getGson().toJson(object, jsonType, writer);
        }
    }
}
