package com.github.fcarelse.assessment;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;

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
			JsonArray data = (JsonArray) Jsoner.deserialize(fileReader);
			contacts = new JsonArray();
			for(Object contact: data.toArray()){
				contacts.add(new Contact((JsonObject) contact));
			}
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

	/**
	 * Initializes contacts in address book with default data.
	 */
	public void init() {
		contacts = new JsonArray();
		contacts.add(new Contact("Fred", "Flintstone"));
		contacts.add(new Contact("Bob", "Apple"));
		contacts.add(new Contact("Sam", "Wedge"));
		((Contact) contacts.get(0)).addInfo("email", "fred.flintstone@example.com");
		((Contact) contacts.get(0)).addInfo("phone", "0855567890");
		((Contact) contacts.get(1)).addInfo("email", "bob.apple@example.com");
		((Contact) contacts.get(2)).addInfo("email", "sam.wedge@example.com");
		saveFile(filename);
	}

	/**
	 * Alternative list method without arguments
 	 */
	public void list() {
		String[] noArgs = {};
		list(noArgs);
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
		if(args.length>1 && (args[1].toLowerCase().equals("first") || args[1].toLowerCase().equals("last"))) {
			System.out.println("Sorting "+args[1].toLowerCase());
			boolean first = args[1].toLowerCase().equals("first");
			if(args.length>2 && args[2].toLowerCase().equals("desc")){
				System.out.printf("Sorting by %s Descending\n", first? "First Name": "Last Name");
				contacts.sort(first? firstDesc: lastDesc);
			} else {
				System.out.printf("Sorting by %s Ascending\n", first? "First Name": "Last Name");
				contacts.sort(first? firstAsc: lastAsc);
			}
			saveFile(filename);
		}
		int index = 1;
		for(Object contact: contacts.toArray()){
			System.out.printf("%d: %s %s\n", index++, ((JsonObject) contact).get("first"), ((JsonObject) contact).get("last"));
		}
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
	public void del(String[] args) throws Exception {
		if(args.length < 2) throw new Exception("Need a contact index for the del command");
		contacts.remove(Integer.parseInt(args[1])-1);
		saveFile(filename);
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
	public void edit(String[] args) throws Exception {
		if(args.length < 2) throw new Exception("Need a contact index for the edit command");
		if(args.length == 2) {
			view(args);
			System.out.println("Use the command: edit <index> <info> <field> <value>");
			System.out.println("Where <info> is 'first', 'last' or 'info' for First name, Last name or Contact information, respectively");
			System.out.println("Leave out the value if you want to remove that field's contact information value");
			return;
		}
		Contact contact = (Contact) contacts.get(Integer.parseInt(args[1])-1);
		if(args.length < 4) throw new Exception("Need to specify 'first', 'last', or 'info'");
		switch(args[2]){
			case "first":
				contact.setFirst(args[3]);
				break;
			case "last":
				contact.setLast(args[3]);
				break;
			case "info":
				if(args.length < 4) throw new Exception("Need a field to edit contact info");
				if(args.length<5) { // If there is no value to assign then remove info
					contact.delInfo(args[3]);
				}else{ // Otherwise update the info.
					contact.addInfo(args[3],args[4]);
				}
		}
		saveFile(filename);
	}

	/**
	 * View Contact
	 * Parameter 1 is the index of the contact.
	 *
	 * @param args
	 * @throws Exception if index out of bounds
	 */
	public void view(String[] args) throws Exception{
		if(args.length < 2) throw new Exception("Need a contact index for the view command");
		Contact contact = ((Contact) contacts.get(Integer.parseInt(args[1])-1));
		System.out.printf("First name: %s\n", contact.getFirst());
		System.out.printf("Last name: %s\n", contact.getLast());
		if(contact.info.isEmpty()){
			System.out.println("No contact information.");
		} else {
			System.out.println("Contact Information: ");
			for(Object key: contact.info.keySet().toArray()) {
				System.out.printf("%s: %s\n", key, contact.info.get(key));
			}
		}
	}

	public static Comparator firstAsc = new Comparator() {
		@Override
		public int compare(Object contact1, Object contact2) {
			return ((Contact) contact1).getFirst().compareTo(((Contact) contact2).getFirst());
		}
	};

	public static Comparator firstDesc = new Comparator() {
		@Override
		public int compare(Object contact1, Object contact2) {
			return ((Contact) contact2).getFirst().compareTo(((Contact) contact1).getFirst());
		}
	};

	public static Comparator lastAsc = new Comparator() {
		@Override
		public int compare(Object contact1, Object contact2) {
			return ((Contact) contact1).getLast().compareTo(((Contact) contact2).getLast());
		}
	};

	public static Comparator lastDesc = new Comparator() {
		@Override
		public int compare(Object contact1, Object contact2) {
			return ((Contact) contact2).getLast().compareTo(((Contact) contact1).getLast());
		}
	};
}
