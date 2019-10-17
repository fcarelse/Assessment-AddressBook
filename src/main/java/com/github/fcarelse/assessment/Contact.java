package com.github.fcarelse.assessment;

import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class Contact extends JsonObject {
	protected String first; // First name
	protected String last; // Last name
	JsonObject info; // Contact Information

	/**
	 * Constructor of initial contact using First name and Last name
	 * @param first
	 * @param last
	 */
	public Contact(String first, String last){
		info = new JsonObject();
		this.first = first;
		this.last = last;
	}

	/**
	 * Constructor of contact from a JsonObject that was previously contact
	 * @param contact
	 */
	public Contact(JsonObject contact) {
		this.setFirst((String) contact.get("first"));
		this.setLast((String) contact.get("last"));
		this.info = (JsonObject) contact.get("info");
	}

	/**
	 * Gets contact info based on info type field requested
	 *
	 * @param field
	 * @return
	 */
	public String getInfo(String field) {
		// Clean the field name to ensure map keys do not conflict or diverge.
		field = cleanField(field);
		// Returns the value in the "info" Map otherwise returns an empty string for safety.
		return info.containsKey(field)?
						(String) info.get(field) :
						"";
	}

	/**
	 * Add contact info to contact
	 *
	 * @param field
	 * @param value
	 */
	public void addInfo(String field, String value){
		// Clean the field name to lowercase letters and digits.
		field = cleanField(field);
		value = cleanValue(value);
		info.put(field, value);
	}

	/**
	 * Delete a piece of contact information
	 *
	 * @param field
	 * @throws Exception
	 */
	public void delInfo(String field) throws Exception {
		field = cleanField(field);
		if(info.containsKey(field)) info.remove(field);
		else throw new Exception("Cannot find field " + field);
	}

	/**
	 * Function for enforcing field naming rules
	 * @param field
	 * @return String
	 */
	public static String cleanField(String field){
		// Force lowercase and only letters, numbers, dash and underscore
		return field.toLowerCase().replace("[^a-z0-9_-]","").toString();
	}

	/**
	 * Function for enforcing value constraints and converting escaped whitespaces to single space
	 * @param value
	 * @return String
	 */
	private String cleanValue(String value) {
		return value
						// Change any block of whitespace characters to single space character
						.replaceAll("\\s+"," ")
						// Remove any backslash character that is preceding a space character
						.replaceAll("\\\\(?= )","");
	}

	/**
	 * Method to convert self to JSON string
	 * @return
	 */
	@Override
	public String toJson() {
		final StringWriter writable = new StringWriter();
		try {
			this.toJson(writable);
		} catch (final IOException e) {
		}
		return writable.toString();
	}

	/**
	 * Method to convert self to JSON string and pipe into writer
	 * @param writer
	 * @throws IOException
	 */
	@Override
	public void toJson(Writer writer) throws IOException {

		final JsonObject json = new JsonObject();
		json.put("first", this.getFirst());
		json.put("last", this.getLast());
		json.put("info", info);
		json.toJson(writer);

	}

	public String getFirst() {
		return first;
	}

	/**
	 * Setter for First Name. Copies value to the JsonObject part of self
	 * @param first
	 */
	public void setFirst(String first) {
		this.first = first;
		this.put("first", first);
	}


	public String getLast() {
		return last;
	}

	/**
	 * Setter for Last Name. Copies value to the JsonObject part of self
	 * @param last
	 */
	public void setLast(String last) {
		this.last = last;
		this.put("last", last);
	}
}
