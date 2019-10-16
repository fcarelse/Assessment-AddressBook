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
	 *
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
	 * Print help info
	 */
	private static void printHelp(String[] args){
		String command;
		if(args.length < 2) {
			System.out.println("Enter parameters: help <command> \n"+
							"Commands are: list, add, del, edit and init");
			return;
		}
		else {
			command = args[2];
			try{
				useCommandLine(args);
			} catch(Exception e){
				System.exit(0);
			}
		}

		String help = "";
		System.out.println(help);
	}
}
