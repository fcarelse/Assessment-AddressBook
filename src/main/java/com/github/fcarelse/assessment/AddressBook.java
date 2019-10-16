package com.github.fcarelse.assessment;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AddressBook {
	private JsonArray contacts;
	private String filename = "default.json";
	private File file = new File(filename);

	public AddressBook(){
		if(!file.exists()) {
			init();
			saveFile(filename);
		}else{
			loadFile(filename);
		}
	}

	public AddressBook(String filename) throws IOException {
		this.filename = filename;
		file = new File(filename);

		if(!file.exists()) {
			init();
		}else{
			loadFile(filename);
		}
	}

	private void loadFile(String filename){
		try (FileReader fileReader = new FileReader(filename)) {
			contacts = (JsonArray) Jsoner.deserialize(fileReader);

//			// need dozer to copy object to staff, json_simple no api for this?
//			Mapper mapper = new DozerBeanMapper();
//
//			// JSON to object
//			contacts = mapper.map(deserialize, JsonArray.class);

		}catch(IOException e){
			System.out.println("Failed to load from file");
		}catch(JsonException e){
			System.out.println("Failed to unserialize from file");
		}
	}

	private void saveFile(String filename) {
		String json = Jsoner.serialize(contacts);
		json = Jsoner.prettyPrint(json);
		try (FileWriter fileWriter = new FileWriter(filename)) {
			Jsoner.serialize(contacts, fileWriter);
		}catch(IOException e){
			System.out.println("Failed to save to file");
		}
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
		saveFile(filename)
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
		int index = 1;
		for(Object contact: contacts.toArray()){
			System.out.printf("%d: %s %s\n", index++, ((JsonObject) contact).get("first"), ((JsonObject) contact).get("last"));
		}
		saveFile(filename);
	}

	/**
	 * Adds contact
	 * Argument 1 is the first name
	 * Argument 2 is the second name
	 * @param args
	 */
	public void add(String[] args) throws Exception{
		if(args.length < 3) throw new Exception("Need 2 arguments for the add command");
		contacts.add(new Contact(args[1], args[2]));
		saveFile(filename);
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
