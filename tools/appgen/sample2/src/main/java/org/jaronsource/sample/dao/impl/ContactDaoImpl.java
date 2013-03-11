package org.jaronsource.sample.dao.impl;

import org.jaronsource.sample.domain.Contact;
import org.jaronsource.sample.dao.ContactDao;
import com.ccesun.framework.core.dao.support.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class ContactDaoImpl extends GenericDao<Contact, Integer> implements ContactDao {

}