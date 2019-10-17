package com.github.fcarelse.assessment;

import java.io.IOException;
import java.util.Scanner;

/******************************************************************************
 *  Compilation:  javac AddressBookApp.java
 *  Execution:    java AddressBookApp
 *
 *
 ******************************************************************************/

public class AddressBookApp {
	// Boolean indicator if using command line arguments or if using menu prompt for commands
	private static boolean usingPrompt = false;
	private static Scanner console = new Scanner(System.in);
	protected static AddressBook addressBook;

	/**
	 * Entry point of execution of the program
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			// initialize the address book;
			addressBook = new AddressBook("AddressBook.json");
		}catch(IOException error){
			// alert about issue with address book
			System.out.println("Failed to initialize address book");
			System.exit(0);
		}
		// if no arguments then use internal prompt to receive commands.
		if(args.length < 1) usePrompt();
		else try{
			useCommandLine(args);
		} catch(Exception e){
			usePrompt();
		}
	}

	/**
	 * Execute command from parameters
	 * @param args
	 */
	protected static void useCommandLine (String[] args){
		// Collect the command and convert to lowercase to make it case insensitive.
		if(args.length < 1) printMenu();
		String command = args[0].toLowerCase();
		try{
			switch(command){
				case "menu": case "": printMenu();
					break;
				case "help": printHelp(args);
					break;
				case "init": addressBook.init();
					addressBook.list();
					break;
				case "list": addressBook.list(args);
					break;
				case "add": addressBook.add(args);
					break;
				case "del": addressBook.del(args);
					break;
				case "edit": addressBook.edit(args);
					break;
				case "view": addressBook.view(args);
					break;
				case "quit": case "exit":
					System.exit(0);
				default: throw new Exception("Unrecognized command: " + command);
			}
		}catch(Exception issue){
			System.out.println("Error: " + issue.getMessage());
		}
	}

	/**
	 * Prompt for user commands
	 */
	protected static void usePrompt(){
		usingPrompt = true;
		// Read from the console
		String[] args;
		String command = "menu";
		printMenu();
		// Loop until commanded to exit the program
		while(command != "quit" && command != "exit" && command != ""){
			System.out.print("Enter a command ( default 'menu'): ");
			args = console.nextLine().split("\\s+");
			if(args.length < 1) System.exit(0);
			else {
				command = args[0];
				try{
					useCommandLine(args);
				} catch(Exception e){
					System.exit(0);
					return;
				}
			}
		}
	}

	/**
	 * Print the menu
	 */
	private static void printMenu(){
		System.out.println("Address Book usage: <command> <options...>");
		System.out.println("Commands");
		System.out.println("menu : Show this menu");
		System.out.println("help : Help with a command");
		System.out.println("init : Reset data");
		System.out.println("list : List contacts");
		System.out.println("add  : Add contact");
		System.out.println("del  : Remove contact");
		System.out.println("edit : Edit contact");
		System.out.println("view : View contact");
		if(usingPrompt) {
			System.out.println("quit : Exit the system");
		}
	}

	/**
	 * Print help info for specific commands
	 */
	private static void printHelp(String[] args){
		if(args.length < 2) {
			System.out.println("Enter parameters: help <command> \n"+
							"Commands are: list, add, del, edit and init");
			return;
		}
		else {
			switch(args[1]){
				case "help": System.out.println("E.G. Type: \"help init\" to show help about the init command");
					break;
				case "init":
					System.out.println("Init: This command resets data and creates 3 default contacts with default contact information in each");
					break;
				case "list":
					System.out.println("List: This command lists out all Contacts. The index of the contact is used for viewing and editing");
					System.out.println("List: You can add an argument of \"first\" or \"last\" to sort by first name or last name respectively");
					System.out.println("List: You can add another argument of \"desc\" if you want the sort to be descending in order");
					System.out.println("List: Make a note to the change in the index as the contacts are moved in storage");
					break;
				case "add":
					System.out.println("Add: Add a new contact. Provide first name and last name as 2 parameters");
					break;
				case "del":
					System.out.println("Del: Delete a contact. Provide index of contact from last List command.");
					break;
				case "edit":
					System.out.println("Edit: Edit a contact. Provide index of contact from last List command.");
					System.out.println("Edit: Next argument is either \"first\", \"last\" or \"info\".");
					System.out.println("Edit: If editting first name or last name the next parameter is the value to assign.");
					System.out.println("Edit: If editting contact info the next parameter is the contact information field name.");
					System.out.println("Edit: If editting contact info by field name, if the next parameter is left out the contact information is removed.");
					System.out.println("Edit: If editting contact info the parameter after the field name is the value to assign.");
					System.out.println("Edit: For assigning values you can use \"\\\" before a space character to keep the space character.");
					System.out.println("Edit: E.G. \"33\\ East\\ Boulevard\" will be saved as \"33 East Boulevard\".");
					break;
				case "view":
					System.out.println("View: View a contact. Provide index of contact from last List command.");
					break;
				case "quit": case "exit":
					System.out.println("Quit: End the program and return to system.");
				default:
					System.out.println("Help: Unrecognized command. Commands are 'init', 'list', 'add', 'del', 'edit', 'view' and 'quit'");
			}
		}

		String help = "";
		System.out.println(help);
	}
}
