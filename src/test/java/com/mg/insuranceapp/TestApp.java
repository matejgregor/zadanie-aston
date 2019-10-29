package com.mg.insuranceapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

import static com.mg.insuranceapp.enums.InsuranceAdditional.CANCELATION;
import static com.mg.insuranceapp.enums.InsuranceAdditional.SPORT;
import static com.mg.insuranceapp.enums.InsuranceTarif.EXTRA;
import static com.mg.insuranceapp.enums.InsuranceType.SHORT_TERM;
import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestApp {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Test
    public void testCalculationOk() {
        final UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("/calculation")
                .queryParam("insuranceType", SHORT_TERM)
                .queryParam("startDate", "2019-08-01")
                .queryParam("endDate", "2019-08-01")
                .queryParam("insuranceTarif", EXTRA)
                .queryParam("peopleAmount", 1)
                .queryParam("insuranceAdditional", SPORT, CANCELATION)
                .build();
        BigDecimal d = template.getForObject(uri.toUri(), BigDecimal.class);
        assertEquals(valueOf(4.32), d);
    }

    @Test
    public void testCalculationFail() {
        final UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("/calculation")
                .queryParam("insuranceType", SHORT_TERM)
                .queryParam("startDate", "2019-08-01")
                .queryParam("insuranceTarif", EXTRA)
                .queryParam("peopleAmount", 1)
                .queryParam("insuranceAdditional", SPORT, CANCELATION)
                .build();
        try {
            template.getForObject(uri.toUri(), Object.class);
        } catch (final Exception e) {
            assertEquals("In case of short term insurance end date should be nonempty!", e.getMessage());
        }
    }

}
