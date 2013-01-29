package com.tp.repository;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.EntityPersister;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.tp.spring.SpringTxTestCase;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class JpaMappingTest extends SpringTxTestCase {

	private static Logger logger = LoggerFactory.getLogger(JpaMappingTest.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Test
	public void allClassMapping() throws Exception {
		Session session = sessionFactory.openSession();

		try {
			Map metadata = sessionFactory.getAllClassMetadata();

			assertTrue("No entity mapping found", metadata.values().size() > 0);

			for (Object o : metadata.values()) {
				EntityPersister persister = (EntityPersister) o;
				String className = persister.getEntityName();
				Query q = session.createQuery("from " + className + " c");
				q.iterate();
				logger.debug("ok: " + className);
			}
		} finally {
			session.close();
		}
	}
}
