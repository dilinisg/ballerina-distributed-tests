package org.wso2.ballerina.deployment.Base;

/**
 * Created by dilinig on 5/8/17.
 */

import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.wso2.ballerina.BallerinaIntegrationBase;

import java.io.IOException;

public class BallerinaBaseTest extends BallerinaIntegrationBase{

    /**
     * Base test class for all Ballerina test cases.
     */

    @BeforeSuite(alwaysRun = true)
    public void createEnvironment(ITestContext ctx) throws Exception {
        super.setTestSuite(ctx.getCurrentXmlTest().getSuite().getName());
        super.init(ctx.getCurrentXmlTest().getSuite().getName());
    }

    @BeforeClass(alwaysRun = true)
    public void init(ITestContext ctx) throws Exception {

    }

    @AfterSuite(alwaysRun = true)
    public void deleteEnvironment(ITestContext ctx) throws Exception {
        super.unSetTestSuite(ctx.getCurrentXmlTest().getSuite().getName());
    }

}
