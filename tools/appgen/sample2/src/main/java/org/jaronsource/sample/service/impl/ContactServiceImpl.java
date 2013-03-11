package org.jaronsource.sample.service.impl;

import org.jaronsource.sample.dao.ContactDao;
import org.jaronsource.sample.domain.Contact;
import org.jaronsource.sample.service.ContactService;
import com.ccesun.framework.core.service.SearchFormSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import com.ccesun.framework.core.dao.support.IDao;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl extends SearchFormSupportService<Contact, Integer> implements ContactService {

	@Autowired
	private ContactDao contactDao;
	
	@Override
	public IDao<Contact, Integer> getDao() {
		return contactDao;
	}

}