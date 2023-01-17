package com.axonivy.utils.persistence.test.dao;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.PersistenceException;

import org.junit.jupiter.api.Test;

import com.axonivy.utils.persistence.daos.ConstrainedDAO;
import com.axonivy.utils.persistence.entities.Constrained;
import com.axonivy.utils.persistence.test.DemoTestBase;

import ch.ivyteam.ivy.environment.IvyTest;

@IvyTest
public class GenericDAOTest extends DemoTestBase {

	private class ConstrainedHolder {
		public Constrained constrained;
	}

	@Test
	public void testBasicFunctionality() {
		ConstrainedDAO dao = ConstrainedDAO.getInstance();

		Constrained entity1 = new Constrained();
		entity1.setName("Test");
		entity1 = dao.save(entity1);

		ConstrainedHolder holder = new ConstrainedHolder();
		holder.constrained = new Constrained();
		holder.constrained.setName("Test");


		assertThrows(PersistenceException.class, () -> {
			holder.constrained = dao.save(holder.constrained);
			fail("Expected exception");
		});

		LOG.info("Saved Entity Ids: {0} {1}", entity1.getId(), holder.constrained.getId());
	}
}
