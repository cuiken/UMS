package com.tp.repository;

import static org.junit.Assert.*;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

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

//	@PersistenceContext
//	private EntityManager em;
	
	@Autowired
	private SessionFactory sessionFactory;

//	@Test
//	public void allClassMapping() throws Exception {
//		Metamodel model = em.getEntityManagerFactory().getMetamodel();
//
//		assertTrue("No entity mapping found", model.getEntities().size() > 0);
//
//		for (EntityType entityType : model.getEntities()) {
//			String entityName = entityType.getName();
//			em.createQuery("select o from " + entityName + " o").getResultList();
//			logger.info("ok: " + entityName);
//
//		}
//	}
	
	@Test
	public void allClassMapping()throws Exception{
		Session session = sessionFactory.openSession();

		try {
			Map metadata = sessionFactory.getAllClassMetadata();
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
