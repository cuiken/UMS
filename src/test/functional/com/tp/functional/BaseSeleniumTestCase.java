package com.tp.functional;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.test.selenium.Selenium2;
import com.test.selenium.SeleniumSnapshotRule;
import com.test.selenium.WebDriverFactory;

public class BaseSeleniumTestCase extends BaseFunctionalTestCase {
	protected static Selenium2 s;

	//出错时截屏的规则
	@Rule
	public TestRule snapshotrule = new SeleniumSnapshotRule(s);

	@BeforeClass
	public static void init() throws Exception {
		createSeleniumOnce();
		loginAsUserIfNecessary();
	}

	/**
	 * 创建Selenium，仅创建一次.
	 */
	protected static void createSeleniumOnce() throws Exception {
		if (s == null) {
			//根据配置创建Selenium driver.
			String driverName = propertiesLoader.getProperty("selenium.driver");

			WebDriver driver = WebDriverFactory.createDriver(driverName);

			s = new Selenium2(driver, baseUrl);
			s.setStopAtShutdown();
		}
	}

	/**
	 * 登录管理员, 如果用户还没有登录.
	 */
	protected static void loginAsUserIfNecessary() {
		s.open("/account/user.action");

		if ("登录页".equals(s.getTitle())) {
			s.type(By.name("username"), "user");
			s.type(By.name("password"), "user");
			s.check(By.name("rememberMe"));
			s.click(By.id("submit_btn"));
			s.waitForTitleContains("文件列表");
		}
	}
}
