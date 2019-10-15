package com.github.fcarelse.assessment;

import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class Contact extends JsonObject {
	private String first;
	private String last;
	JsonObject info;

	public Contact(String first, String last){
		info = new JsonObject();
		this.first = first;
		this.last = last;
	}

	public String[] allInfo() {
		throw new Error("Method not defined!");
	}

	/**
	 * Gets contact info based on info type requested
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

	public void addInfo(String field, String value){
		// Clean the field name to lowercase letters and digits.
		field = cleanField(field);
		value = cleanValue(value);
		info.put(field, value);
	}

	public void delInfo(String field) {
		throw new Error("Method not defined!");
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
	 * Function for enforcing value
	 * @param value
	 * @return String
	 */
	private String cleanValue(String value) {
		// Remove any newline characters
		return value.replace("\\n","");
	}

	public JsonObject toJsonObject(){
		JsonObject json = new JsonObject();
		json.put("first", getFirst());
		json.put("last", getLast());
		json.put("info", info);
		return json;
	}

	@Override
	public String toJson() {
		final StringWriter writable = new StringWriter();
		try {
			this.toJson(writable);
		} catch (final IOException e) {
		}
		return writable.toString();
	}

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

	public void setFirst(String first) {
		this.first = first;
	}


	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}
}
