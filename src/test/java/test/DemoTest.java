package test;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import pages.AddUser;
import pages.HomePage;
import pages.Login;

public class DemoTest {
	private static RemoteWebDriver driver;
	private static Logger LOGGER = Logger.getGlobal();

	@BeforeClass
	public static void init() {
		System.setProperty("webdriver.chrome.driver",
				"src/test/resources/resources/chromedriver-87-4280/chromedriver.exe");
		ChromeOptions cOptions = new ChromeOptions();
		cOptions.setHeadless(false);
		cOptions.setCapability("profile.default_content_setting_values.cookes", 2);
		cOptions.setCapability("network.cookie.cookieBehavior", 2);
		cOptions.setCapability("profiles.block_third_party_cookies", true);
		driver = new ChromeDriver(cOptions);
		driver.manage().window().setSize(new Dimension(1366, 768));
	}

	@Before
	public void setup() {
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		try {
			driver.get(HomePage.URL);
		} catch (TimeoutException e) {
			System.out.println("Page: " + HomePage.URL + " did not load within 30 seconds!");
		}
	}

	@AfterClass
	public static void tearDown() {
		LOGGER.warning("Closing webdriver instance!");

		driver.quit();

		LOGGER.info("Webdriver closed successfully!");
	}

	@Test
	public void createUser() throws InterruptedException {
		LOGGER.warning("Connecting to The Demo Site....");

		String uName = "Test";
		String pWord = "Test1";

		HomePage nav = PageFactory.initElements(driver, HomePage.class);

		AddUser user = PageFactory.initElements(driver, AddUser.class);

		Login passInfo = PageFactory.initElements(driver, Login.class);

		nav.navUserPage();

		user.createUser(uName, pWord);

		nav.navloginPage();

		passInfo.loginInfo(uName, pWord);

		driver.getPageSource().contains("Successful");

	}

}
