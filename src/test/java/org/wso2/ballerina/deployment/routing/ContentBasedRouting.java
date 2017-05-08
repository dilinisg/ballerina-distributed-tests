package org.wso2.ballerina.deployment.routing;

/**
 * Created by dilinig on 5/4/17.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.ballerina.BallerinaIntegrationBase;

public class ContentBasedRouting extends BallerinaIntegrationBase{

    private final String USER_AGENT = "Mozilla/5.0";
    String url;

    public static void main(String[] args) throws Exception {

        ContentBasedRouting http = new ContentBasedRouting();

        System.out.println("Testing 1 - Send Http GET request");
        http.sendGet();

        System.out.println("\nTesting 2 - Send Http POST request");
        http.sendPost();

    }

    @BeforeClass(alwaysRun = true) public void initialize() throws Exception {
        try {
            super.init("pattern2");
            url = super.ballerinaURL;
            System.out.println("url===" + url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // HTTP GET request
    @Test
    private void sendGet() throws Exception {

        String hbrUrl = url + "/hbr";

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(hbrUrl);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);
        request.addHeader("name","nyse");

        HttpResponse response = client.execute(request);

        System.out.println("\nSending 'GET' request to URL : " + hbrUrl);
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());

    }

    // HTTP POST request
    @Test
    private void sendPost() throws Exception {

        String url = "http://localhost:40794/nyseStocks";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        //urlParameters.add(new BasicNameValuePair("name","nyse"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        System.out.println("\nSending 'POST' request to URL : " + url);
        //System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());

    }
}
