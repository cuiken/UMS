package com.tp.functional.gui;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.tp.category.Smoke;
import com.tp.functional.BaseSeleniumTestCase;

public class UserFT extends BaseSeleniumTestCase {
	@BeforeClass
	public static void loginAsAdmin() {
		s.open("/logout.action");
		s.type(By.name("username"), "admin");
		s.type(By.name("password"), "admin");
		s.click(By.id("submit_btn"));
	}

	@AfterClass
	public static void logout() {
		s.open("/logout.action");
	}

	/**
	 * 浏览用户列表.
	 */
	@Test
	@Category(Smoke.class)
	public void viewUserList() {
		s.open("/account/user.action");
		WebElement table = s.findElement(By.id("contentTable"));
		assertEquals("admin", s.getTable(table, 0, 0));
		assertEquals("user", s.getTable(table, 1, 0));
	}

	@Test
	public void editUser() {
		s.open("/account/user!input.action?id=2");
		s.type(By.id("name"), "Kevin");
		s.type(By.id("plainPassword"), "user2");
		s.click(By.id("submit_btn"));

		assertTrue("没有成功消息", s.isTextPresent("保存用户成功"));
		WebElement table = s.findElement(By.id("contentTable"));
		assertEquals("Kevin", s.getTable(table, 1, 1));
	}

	@Test
	public void deleteUser() {
		s.open("/account/user!delete.action?id=2");
		assertTrue("没有成功消息", s.isTextPresent("删除用户成功"));
	}
}
