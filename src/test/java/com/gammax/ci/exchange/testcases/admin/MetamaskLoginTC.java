package com.gammax.ci.exchange.testcases.admin;

import com.gammax.ci.gammax.pages.Homepage;
import com.gammax.ci.gammax.pages.Loginpage;
import com.gammax.ci.gammax.pages.MetaMaskNotificationPage;
import com.gammax.ci.gammax.pages.MetamaskRegistrationPage;
import com.gammax.ci.gammax.testbase.Base;
import com.relevantcodes.extentreports.LogStatus;

import java.util.Arrays;

import org.testng.annotations.Test;

public class MetamaskLoginTC extends Base {

    static final String TEST_ID ="T1";

    Loginpage loginPage=new Loginpage();
    Homepage homePage = new Homepage();
    MetaMaskNotificationPage metapage = new MetaMaskNotificationPage();

    MetamaskRegistrationPage registrationPage= new MetamaskRegistrationPage();

    Base playbookbase=new Base();

    @Test
    public void verify_gammaxlogin() throws Exception {

        setTestId(TEST_ID);

        loginPage.LoginPageDriverRef(driver);
        homePage.HomePageDriverRef(Base.driver);
        registrationPage.MetamaskDriverRef(driver);

        extentTest.log(LogStatus.INFO, "TC Summary : Verify User is able to login to metamask app");
        registrationPage.switchToMetaMask();
        extentTest.log(LogStatus.INFO, "Switch to Metamask window");
        registrationPage.MetamaskDriverRef(driver);
        registrationPage.clickImportAnExistingWallet();
        extentTest.log(LogStatus.INFO, "Click on Import an Existing Wallet");
        takeScreenShot();
        registrationPage.clickIAgree();
        extentTest.log(LogStatus.INFO, "Click on I Agree");
        takeScreenShot();
        registrationPage.enterScretRecoryPhrase(Arrays.asList(new String[]{"cat","tourist","inside","robot","blossom","provide","elephant","antenna","small","bonus","mesh","better"}));
        extentTest.log(LogStatus.INFO, "Enter Secret Recovery Phrase as \"cat\",\"tourist\",\"inside\",\"robot\",\"blossom\",\"provide\",\"elephant\",\"antenna\",\"small\",\"bonus\",\"mesh\",\"better\"");
        takeScreenShot();
        registrationPage.clickConfirmSecretPhrase();
        extentTest.log(LogStatus.INFO, "Click on Confirm Secret Phrase");
        registrationPage.enterPassword("19961996s@K");
        extentTest.log(LogStatus.INFO, "Enter Password :: 19961996s@K");
        registrationPage.clickIUnderstandChkBox();
        extentTest.log(LogStatus.INFO, "Click on I UnderStand");
        takeScreenShot();
        registrationPage.clickImportMyWallet();
        extentTest.log(LogStatus.INFO, "Click on Import My Wallet");
        takeScreenShot();
        registrationPage.clickGotIt();
        extentTest.log(LogStatus.INFO, "Click on Got It");
        takeScreenShot();
        registrationPage.clickNext();
        extentTest.log(LogStatus.INFO, "Click on Next");
        takeScreenShot();
        registrationPage.clickDone();
        extentTest.log(LogStatus.INFO, "Click on Done");
        takeScreenShot();
        registrationPage.clickX();
        extentTest.log(LogStatus.INFO, "Click on X");
        registrationPage.clickNetwork();
        extentTest.log(LogStatus.INFO, "Click on Network");
        takeScreenShot();
        registrationPage.clickAddNetwork();
        extentTest.log(LogStatus.INFO, "Click on Add Network");
        takeScreenShot();
        registrationPage.clickAdvanced();
        extentTest.log(LogStatus.INFO, "Click on Advanced");
        takeScreenShot();
        registrationPage.clickShowTestNetworks();
        extentTest.log(LogStatus.INFO, "Click on Show Test Networks");
        takeScreenShot();
        registrationPage.clickSave();
        extentTest.log(LogStatus.INFO, "Click on Save");
        takeScreenShot();
        registrationPage.clickNetwork();
        extentTest.log(LogStatus.INFO, "Click on Network");
        takeScreenShot();
        registrationPage.clickGoerliTestNetwork();
        extentTest.log(LogStatus.INFO, "Click on Goerli Test Network");
        takeScreenShot();
        registrationPage.clickSave();
        extentTest.log(LogStatus.INFO, "Click on Save");
        takeScreenShot();
        registrationPage.switchToGammaX();
        extentTest.log(LogStatus.INFO, "Switch to GAMMAX Window");
        takeScreenShot();
        homePage.clickConnectWallet();
        extentTest.log(LogStatus.INFO, "Click on Connect Wallet");
        takeScreenShot();
        homePage.HomePageDriverRef(Base.driver);
        homePage.clickGetStarted();
        extentTest.log(LogStatus.INFO, "Click on Get Started");
        takeScreenShot();
        homePage.clickMetaMask();
        extentTest.log(LogStatus.INFO, "Click on MetaMask");
        takeScreenShot();
        homePage.switchToMetaMaskNotification();
        extentTest.log(LogStatus.INFO, "Switch to Metamask Notofication");
        takeScreenShot();
        metapage.MetaMaskNotificationPage(Base.driver);
        metapage.clickNext();
        extentTest.log(LogStatus.INFO, "Click on NEXT");
        takeScreenShot();
        metapage.clickConnect();
        extentTest.log(LogStatus.INFO, "Click On Connect");
        takeScreenShot();
        metapage.clickSign();
        extentTest.log(LogStatus.INFO, "Click on SignIN");
        metapage.switchToGammaX();
        extentTest.log(LogStatus.INFO, "Clicked on <b>Connect Wallet</b> button successfully");
        takeScreenShot();

    }
}
