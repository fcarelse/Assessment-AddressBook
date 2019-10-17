package com.github.fcarelse.assessment;

import java.io.IOException;

public class AddressBookTest {
	AddressBook testBook;

	/**
	 *
	 */
	public AddressBookTest(){
		try{
			testBook = new AddressBook("test.json");
		}catch(IOException e){
			throw new AssertionError("Could not create new AddressBook in test.json");
		}
	}
}
