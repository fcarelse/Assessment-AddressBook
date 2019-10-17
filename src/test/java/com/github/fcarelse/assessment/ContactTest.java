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

	/**
	 * Ensure that the constructor puts first and last values in place.
	 */
	@Test
	void testConstructor(){
		contact = new Contact("Bob", "Smith");
		Assert.assertEquals(contact.get("first"), "Bob");
		Assert.assertEquals(contact.get("last"), "Smith");
		Assert.assertEquals(contact.first, "Bob");
		Assert.assertEquals(contact.last, "Smith");
	}

	/**
	 * Ensure that the addInfo method writes to contact.info
	 */
	@Test
	void testAddInfo(){
		contact.addInfo("Phone", "012345678");
		Assert.assertEquals(contact.info.get("Phone"), "012345678");
	}

	/**
	 * Ensure that the getInfo method reads from contact.info
	 */
	@Test
	void testGetInfo(){
		contact.info.put("email","bob@abc.com");
		Assert.assertEquals(contact.getInfo("Email"), "bob@abc.com");
	}

	/**
	 * Ensure setFirst puts the first name into both the object property first and the JsonObject key-value system
 	 */
	@Test
	void testSetFirst(){
		contact = new Contact("Bob", "Smith");
		contact.setFirst("Bill");
		Assert.assertEquals(contact.get("first"), "Bill");
		Assert.assertEquals(contact.first, "Bill");
	}

	/**
	 * Ensure setLast puts the last name into both the object property last and the JsonObject key-value system
	 */
	@Test
	void testSetLast(){
		contact = new Contact("Bob", "Smith");
		contact.setLast("Burr");
		Assert.assertEquals(contact.get("last"), "Burr");
		Assert.assertEquals(contact.last, "Burr");
	}

}
