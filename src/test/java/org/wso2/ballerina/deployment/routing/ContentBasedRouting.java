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
import org.testng.annotations.Test;
import org.wso2.ballerina.deployment.Base.BallerinaBaseTest;

public class ContentBasedRouting extends BallerinaBaseTest{


    // HTTP GET request. Routing messages based on the header value.
    @Test
    private void sendGet() throws Exception {

        String hbrUrl = ballerinaURL + "/hbr";

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(hbrUrl);

        // add request headers
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
