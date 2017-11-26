package com.spothero.resources.rate;

import com.spothero.Program;
import org.custommonkey.xmlunit.XMLAssert;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class RateResourceJerseyTest extends JerseyTest {
    @Test
    public void testJson() throws Exception {
        String expected = "{ \"start\" : \"2017-11-26T12:00:00\", \"end\" : \"2017-11-26T13:00:00\", \"price\" : 20.00 }";

        String actual = target("rate")
                .queryParam("start", "2017-11-26T12:00")
                .queryParam("end", "2017-11-26T13:00")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);

        JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT);
    }

    @Test
    public void testXml() throws Exception {
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><rate><end>2017-11-26T13:00:00</end><price>20.00</price><start>2017-11-26T12:00:00</start></rate>";

        String actual = target("rate")
                .queryParam("start", "2017-11-26T12:00")
                .queryParam("end", "2017-11-26T13:00")
                .request(MediaType.APPLICATION_XML)
                .get(String.class);

        XMLAssert.assertXMLEqual(expected, actual);
    }

    @Test
    public void testMissingQueryParams() throws Exception {
        String expected = "{ \"code\" : \"MISSING_DATE_PARAMETERS\", \"description\" : \"Start and end date query parameters are required.\" }";

        Response response = target("rate")
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertEquals(400, response.getStatus());
        JSONAssert.assertEquals(expected, response.readEntity(String.class), JSONCompareMode.STRICT);
    }

    @Test
    public void testInvalidFormatQueryParams() throws Exception {
        String expected = "{ \"code\" : \"DATE_BAD_FORMAT\", \"description\" : \"Date parameters must be in ISO-8601 format.\" }";

        Response response = target("rate")
                .queryParam("start", "foo")
                .queryParam("end", "bar")
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertEquals(400, response.getStatus());
        JSONAssert.assertEquals(expected, response.readEntity(String.class), JSONCompareMode.STRICT);
    }

    @Override
    protected Application configure() {
        return Program.createConfiguration();
    }
}
