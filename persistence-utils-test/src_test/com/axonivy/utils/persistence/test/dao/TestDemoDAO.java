package com.axonivy.utils.persistence.test.dao;

import com.axonivy.utils.persistence.demo.tool.test.dao.DemoDAO;
import com.axonivy.utils.persistence.test.mock.SimplePersistenceContext;


public class TestDemoDAO extends DemoDAO {
	private static final TestDemoDAO instance = new TestDemoDAO();

	private TestDemoDAO() {
	}

	public static TestDemoDAO getInstance() {
		return instance;
	}

	@Override
	public String getPersistenceUnitName() {
		return SimplePersistenceContext.JPA_DEMO_TEST;
	}
}
