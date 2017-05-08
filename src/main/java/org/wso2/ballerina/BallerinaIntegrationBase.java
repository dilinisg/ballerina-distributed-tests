package org.wso2.ballerina;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import org.wso2.carbon.automation.distributed.beans.InstanceUrls;
import org.wso2.carbon.automation.distributed.beans.Port;
import org.wso2.carbon.automation.distributed.commons.DeploymentConfigurationReader;
import org.wso2.carbon.automation.distributed.commons.DeploymentDataReader;
import org.wso2.carbon.automation.distributed.utills.ScriptExecutorUtil;
import org.wso2.carbon.automation.test.utils.http.client.HttpResponse;
import org.wso2.carbon.integration.common.utils.LoginLogoutClient;

import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dilinig on 5/5/17.
 */
public class BallerinaIntegrationBase {


//    private static final Log log = LogFactory.getLog(APIMIntegrationBaseTest.class);
    protected static String ballerinaURL;
/*    protected static String publisherURL;
    protected static String keyManagerURL;
    protected static String gateWayManagerURL;
    protected static String gateWayWorkerURL;
   *//* protected AutomationContext defaultContext, storeContext, publisherContext, keyManagerContext, gatewayContextMgt, gatewayContextWrk, backEndServer;
    protected TestUserMode userMode;
    protected APIMURLBean defaultUrls, storeUrls, publisherUrls, gatewayUrlsMgt, gatewayUrlsWrk, keyMangerUrl, backEndServerUrl;
    protected User user;*/
    protected HashMap<String, String> instanceMap;

    /**
     * This method will initialize test environment
     * based on user mode and configuration given at automation.xml
     *
     */
    protected void init(String pattern) throws Exception {
        // userMode = TestUserMode.SUPER_TENANT_ADMIN;
        setURLs(pattern);
    }

    protected void setURLs(String patternName) {

        HashMap<String, String> instanceMap = null;
        try {
            DeploymentConfigurationReader depconf = DeploymentConfigurationReader.readConfiguration();
            instanceMap = depconf.getDeploymentInstanceMap(patternName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.setProperty("jsonFilePath","/home/dilinig/poc_ballerina/ballerina-distributed-tests/src/test/resources/deployment.json");
        DeploymentDataReader dataJsonReader = new DeploymentDataReader();
        List<InstanceUrls> urlList = dataJsonReader.getInstanceUrlsList();
        for (InstanceUrls url : urlList) {
            if (instanceMap != null) {
                if (url.getLable().equals(instanceMap.get("ballerina"))){
                    ballerinaURL = getHTTPSUrl("pass-through-http", url.getHostIP(), url.getPorts(),"");
                    System.out.println("BAL-URL=======" + ballerinaURL);
                }
            }
        }
    }

    protected String getHTTPSUrl(String protocol, String hostIP, List<Port> ports, String context) {

        String Url = "http://" + hostIP + ":";
        for (Port port : ports) {
            if (port.getProtocol().equals(protocol)) {
                Url = Url + port.getPort() + context;
                break;
            }
        }
        return Url;
    }

    protected void setTestSuite(String testSuite) throws IOException {
        ScriptExecutorUtil.deployScenario(testSuite);
    }

    protected void unSetTestSuite(String testSuite) throws Exception {
        ScriptExecutorUtil.unDeployScenario(testSuite);
        //gatewayWebAppUrl = gatewayUrls.getWebAppURLNhttp();
    }



    /**
     * init the object with tenant domain, user key and instance of store,publisher and gateway
     * create context objects and construct URL bean
     *
     * @param domainKey - tenant domain key
     * @param userKey   - tenant user key
     * @throws APIManagerIntegrationTestException - if test configuration init fails
     */
/*    protected void init(String domainKey, String userKey) throws Exception {

        try {
            //create store server instance based configuration given at automation.xml
            storeContext = new AutomationContext(APIMIntegrationConstants.AM_PRODUCT_GROUP_NAME,
                    APIMIntegrationConstants.AM_STORE_INSTANCE, domainKey, userKey);
            storeUrls = new APIMURLBean(storeContext.getContextUrls());

            //create publisher server instance
            publisherContext = new AutomationContext(APIMIntegrationConstants.AM_PRODUCT_GROUP_NAME,
                    APIMIntegrationConstants.AM_PUBLISHER_INSTANCE, domainKey, userKey);
            publisherUrls = new APIMURLBean(publisherContext.getContextUrls());

            //create gateway server instance
            gatewayContextMgt = new AutomationContext(APIMIntegrationConstants.AM_PRODUCT_GROUP_NAME,
                    APIMIntegrationConstants.AM_GATEWAY_MGT_INSTANCE, domainKey, userKey);
            gatewayUrlsMgt = new APIMURLBean(gatewayContextMgt.getContextUrls());

            //            gatewayContextWrk =
            //                    new AutomationContext(APIMIntegrationConstants.AM_PRODUCT_GROUP_NAME,
            //                                          APIMIntegrationConstants.AM_GATEWAY_WRK_INSTANCE, domainKey, userKey);
            //            gatewayUrlsWrk = new APIMURLBean(gatewayContextWrk.getContextUrls());

            keyManagerContext = new AutomationContext(APIMIntegrationConstants.AM_PRODUCT_GROUP_NAME,
                    APIMIntegrationConstants.AM_KEY_MANAGER_INSTANCE, domainKey, userKey);
            keyMangerUrl = new APIMURLBean(keyManagerContext.getContextUrls());

            //            backEndServer = new AutomationContext(APIMIntegrationConstants.AM_PRODUCT_GROUP_NAME,
            //                                                  APIMIntegrationConstants.BACKEND_SERVER_INSTANCE, domainKey, userKey);
            //            backEndServerUrl = new APIMURLBean(backEndServer.getContextUrls());

            user = storeContext.getContextTenant().getContextUser();

        } catch (XPathExpressionException e) {
            log.error("Init failed", e);
            throw new Exception("APIM test environment initialization failed", e);
        }

    }*/

    /**
     * @param automationContext - automation context instance of given server
     * @return - created session cookie variable
     * @throws APIManagerIntegrationTestException - Throws if creating session cookie fails
     *//*
    protected String createSession(AutomationContext automationContext) throws APIManagerIntegrationTestException {
        LoginLogoutClient loginLogoutClient;
        try {
            loginLogoutClient = new LoginLogoutClient(automationContext);
            return loginLogoutClient.login();
        } catch (Exception e) {
            log.error("session creation error", e);
            throw new APIManagerIntegrationTestException("session creation error", e);
        }
    }
*/


   /* protected void verifyResponse(HttpResponse httpResponse) throws JSONException {
        Assert.assertNotNull(httpResponse, "Response object is null");
        log.info("Response Code : " + httpResponse.getResponseCode());
        log.info("Response Message : " + httpResponse.getData());
        Assert.assertEquals(httpResponse.getResponseCode(), HttpStatus.SC_OK, "Response code is not as expected");
        JSONObject responseData = new JSONObject(httpResponse.getData());
        Assert.assertFalse(responseData.getBoolean(APIMIntegrationConstants.API_RESPONSE_ELEMENT_NAME_ERROR),
                "Error message received " + httpResponse.getData());

    }*/



}
