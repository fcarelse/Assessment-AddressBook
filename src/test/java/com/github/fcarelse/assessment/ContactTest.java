package com.github.fcarelse.assessment;

import com.github.fcarelse.assessment.Contact;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ContactTest {

	Contact contact;

	@Before
	void beforeTesting(){
		contact = new Contact("Bob", "Smith");
	}

	@Test
	void testAddInfo(){
		contact.addInfo("Phone", "012345678");
		Assert.assertEquals(contact.info.get("Phone"), "012345678");
	}

	@Test
	void testGetInfo(){
		contact.info.put("email","bob@abc.com");
		Assert.assertEquals(contact.getInfo("Email"), "bob@abc.com");
	}

	@Test
	void testAllInfo(){

	}

}
