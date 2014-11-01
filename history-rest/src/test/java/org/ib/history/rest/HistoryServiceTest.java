package org.ib.history.rest;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.ib.history.commons.data.CountryData;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class HistoryServiceTest {

    protected final static String ENDPOINT_ADDRESS = "http://localhost:7777/rest";

    private static Server server;

    @BeforeClass
    public static void initialize() throws Exception {
        configureLogging();
        startServer();
    }

    private static void configureLogging() {
        final ConsoleAppender console = new ConsoleAppender();
        final String PATTERN = "%d [%p|%c|%C{1}] %m%n";
        console.setLayout(new PatternLayout(PATTERN));
        console.setThreshold(Level.INFO);
        console.activateOptions();
        Logger.getRootLogger().addAppender(console);
    }

    private static void startServer() throws Exception {
        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
        sf.setResourceClasses(HistoryService.class);

        sf.setProviders(getProviders());
        sf.setResourceProvider(HistoryService.class, new SingletonResourceProvider(new HistoryService(), true));
        sf.setAddress(ENDPOINT_ADDRESS);
        sf.getInInterceptors().add(new LoggingInInterceptor());
        sf.getOutInterceptors().add(new LoggingOutInterceptor());

        server = sf.create();
    }

    protected static List getProviders() {
        final List<Object> providers = new ArrayList<Object>();
        providers.add(new GsonMessageBodyHandler(Optional.<Map<Class<?>, Object>>empty(), Optional.<Map<Class<?>, Object>>empty()));
        return providers;
    }

    @AfterClass
    public static void destroy() throws Exception {
        server.stop();
        server.destroy();
    }



//    @Test
    public void testPing() {
        WebClient client = WebClient.create(ENDPOINT_ADDRESS + "/ping", getProviders());

        Response response = client.get();
        assertEquals("pong", response.readEntity(String.class));
    }

//    @Test
    public void testCountry() {
        WebClient client = WebClient.create(ENDPOINT_ADDRESS + "/test/country", getProviders());

        Response response = client.get();
        CountryData countryData = response.readEntity(CountryData.class);
        assertEquals("England", countryData.getName());
    }

    @Test
    public void testCountries() {
        WebClient client = WebClient.create(ENDPOINT_ADDRESS + "/test/countries", getProviders());

        Response response = client.get();
//        CountryData countryData = response.readEntity(CountryData.class);
//        assertEquals("England", countryData.getName());
    }
}
