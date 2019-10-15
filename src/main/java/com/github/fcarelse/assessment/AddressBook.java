package com.github.fcarelse.assessment;

import com.github.cliftonlabs.json_simple.JsonArray;

import java.io.File;
import java.io.IOException;

public class AddressBook {
	private String filename;
	private JsonArray contacts;
	private File file;

	public AddressBook(){
		this.init();
	}

	public AddressBook(String filename) throws IOException {
		this.filename = filename;
		file = new File(filename);

		if(!file.exists()) {
			init();
			saveFile();
		}else{
			loadJSON(file.toString());
		}
	}

	private void loadJSON(String toString) {
		throw new Error("Method not defined!");
	}

	private void saveFile() {
		throw new Error("Method not defined!");
	}

	public void init() {
		contacts = new JsonArray();
		contacts.add(new Contact("Fred", "Flintstone"));
		contacts.add(new Contact("Bob", "Apple"));
		contacts.add(new Contact("Sam", "Wedge"));
		((Contact) contacts.get(0)).addInfo("email", "fred.flintstone@example.com");
		((Contact) contacts.get(0)).addInfo("phone", "0855567890");
		((Contact) contacts.get(1)).addInfo("email", "bob.apple@example.com");
		((Contact) contacts.get(2)).addInfo("email", "sam.wedge@example.com");
	}

	/**
	 * Lists contacts in address book.
	 * Argument 1 can be "first" or "last"
	 * Argument 2 can be "asc" or "desc"
	 * Sorting changes the order of the list in storage so that the indexing
	 * will match up with any following edit or delete request
	 *
	 * @param args
	 */
	public void list(String[] args) {
		throw new Error("Method not defined!");
	}

	/**
	 * Adds contact
	 * Argument 1 is the first name
	 * Argument 2 is the second name
	 * @param args
	 */
	public void add(String[] args) {
		throw new Error("Method not defined!");
	}

	/**
	 * Delete a contact from the address book
	 * Parameter 1 is the index
	 * @param args
	 */
	public void del(String[] args) {
		throw new Error("Method not defined!");
	}

	/**
	 * Edit Contact
	 * Parameter 1 is the index of the contact.
	 * Parameter 2 is either "first", "last" or a contact info field name.
	 * If no field exists then a new one is created.
	 * Parameter 3 is the new value to replace the old value.
	 *
	 * @param args
	 * @throws Exception if index out of bounds
	 */
	public void edit(String[] args) {
		throw new Error("Method not defined!");
	}

	/**
	 * View Contact
	 * Parameter 1 is the index of the contact.
	 *
	 * @param args
	 * @throws Exception if index out of bounds
	 */
	public void view(String[] args) {
		throw new Error("Method not defined!");
	}
}
