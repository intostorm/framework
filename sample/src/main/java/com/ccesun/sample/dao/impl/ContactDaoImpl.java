package com.ccesun.sample.dao.impl;

import com.ccesun.sample.domain.Contact;
import com.ccesun.sample.dao.ContactDao;
import com.ccesun.framework.core.dao.support.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class ContactDaoImpl extends GenericDao<Contact, Integer> implements ContactDao {

}