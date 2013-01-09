package com.tp.service.account;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.tp.dao.account.UserDao;
import com.tp.entity.account.User;
import com.tp.service.ServiceException;
import com.tp.service.account.ShiroDbRealm.ShiroUser;
import com.tp.utils.ShiroTestUtils;

public class AccountServiceTest {

	@InjectMocks
	private AccountManager accountService;

	@Mock
	private UserDao mockUserDao;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ShiroTestUtils.mockSubject(new ShiroUser(3L, "foo", "Foo"));
	}

	@Test
	public void saveUser() {
		User admin = new User();
		admin.setId(1L);

		User user = new User();
		user.setId(2L);
		user.setPlainPassword("123");

		//正常保存用户.
		accountService.saveUser(user);

		//保存超级管理用户抛出异常.
		try {
			accountService.saveUser(admin);
			fail("expected ServicExcepton should be thrown");
		} catch (ServiceException e) {
			//expected exception
		}
		Mockito.verify(mockUserDao, Mockito.never()).delete(1L);
	}

}
