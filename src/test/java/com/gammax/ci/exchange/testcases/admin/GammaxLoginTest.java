package com.gammax.ci.exchange.testcases.admin;

import com.gammax.ci.gammax.pages.Homepage;
import com.gammax.ci.gammax.pages.Loginpage;
import com.gammax.ci.gammax.testbase.Base;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;

public class GammaxLoginTest extends Base {
	
	static final String TEST_ID ="kl7txdayak55oyttc7q7h3cytqg6kwl2";

    Loginpage loginPage=new Loginpage();
	Homepage homePage = new Homepage();
	Base playbookbase=new Base();
	
	@Test
	public void verify_MetamaskLogin() throws InterruptedException {
		
		setTestId(TEST_ID);
		
		extentTest.log(LogStatus.INFO, " Verify able to connect to Metamask");
		loginPage.LoginPageDriverRef(driver);
		homePage.HomePageDriverRef(Base.driver);

		String titleOfPage = loginPage.pageTitle();
		extentTest.log(LogStatus.INFO, "Verified : Page title is displayed as <b>"+titleOfPage+"</b>");
		
	}

}
