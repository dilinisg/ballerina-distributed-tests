package org.wso2.ballerina.deployment.routing;

/**
 * Created by dilinig on 5/4/17.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.wso2.ballerina.deployment.Base.BallerinaBaseTest;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ContentBasedRouting extends BallerinaBaseTest{


    // HTTP GET requests. Routing messages based on the header value.
    @Test
    private void sendGetToNYSE() throws Exception {

        String hbrUrl = ballerinaURL + "/hbr";

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(hbrUrl);

        // add request headers
        request.setHeader("name","nyse");
        request.setHeader(HttpHeaders.CONTENT_TYPE, "text/plain");

        HttpResponse response = client.execute(request);

        System.out.println("\n=====Sending 'GET' request to URL : " + hbrUrl + " =======");
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        HttpEntity entity = response.getEntity();
        String json = EntityUtils.toString(entity);
        System.out.println("Response : " + json);

        JSONObject obj = new JSONObject(json);;
        assertEquals(obj.getString("exchange"),"nyse");

    }

    @Test
    private void sendGetToNASDAQ() throws Exception {

        String hbrUrl = ballerinaURL + "/hbr";

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(hbrUrl);

        // add request headers
        request.setHeader("name","nasdaq");
        request.setHeader(HttpHeaders.CONTENT_TYPE, "text/plain");

        HttpResponse response = client.execute(request);

        System.out.println("\n=====Sending 'GET' request to URL : " + hbrUrl + " =======");
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        HttpEntity entity = response.getEntity();
        String json = EntityUtils.toString(entity);
        System.out.println("Response : " + json);

        JSONObject obj = new JSONObject(json);;
        assertEquals(obj.getString("exchange"),"nasdaq");

    }

    // HTTP POST request. Routing messages based on the message content
    @Test
    private void sendPost() throws Exception {

        String url = ballerinaURL + "/nyseStocks";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        //urlParameters.add(new BasicNameValuePair("name","nyse"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        System.out.println("\n=====Sending 'POST' request to URL : " + url + " =======");
        //System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        HttpEntity entity = response.getEntity();
        String json = EntityUtils.toString(entity);
        System.out.println("Response : " + json);

        JSONObject obj = new JSONObject(json);;
        assertEquals(obj.getString("exchange"),"nyseee");

    }

    @Test
    private void testMethod1() throws Exception {

        assertFalse(true);
    }

    @Test
    private void testMethod2() throws Exception {

        assertTrue(true);
    }

    @Test
    private void testMethod3() throws Exception {

        assertTrue(true);
    }

    @Test
    private void testMethod4() throws Exception {

        assertTrue(true);
    }

    @Test
    private void testMethod5() throws Exception {

        assertTrue(true);
    }
}
