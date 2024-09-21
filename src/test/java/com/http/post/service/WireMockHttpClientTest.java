package com.http.post.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.After;
import org.junit.Before;

import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static java.lang.String.format;

public abstract class WireMockHttpClientTest {

    private static final int PORT = 8080;
    protected static final String BASE_URL = format("http://localhost:%d",PORT);
    protected static final String ENDPOINT = "/api/test";
    protected static final String CONTENT_TYPE = "application/json";
    protected static final String GET_MSG = "{\"message\": \"success\"}";

    private WireMockServer wireMockServer;

    @Before
    public void setUp() {
        wireMockServer = new WireMockServer(options().port(PORT));
        wireMockServer.start();

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(ENDPOINT))
                        .withHeader("Content-Type", containing(CONTENT_TYPE))
                            .willReturn(WireMock.aResponse()
                                    .withBody("{\"message\": \"success\"}")
                                    .withHeader("Content-Type", "application/json")
                                    .withStatus(200)));

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(ENDPOINT + "?param1=value1"))
                .willReturn(WireMock.aResponse()
                        .withBody("{\"message\": \"success\"}")
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)));
    }
    @After
    public void tearDown() {
        wireMockServer.stop();
    }
}
