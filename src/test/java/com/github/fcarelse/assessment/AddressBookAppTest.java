package com.github.fcarelse.assessment;

import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class AddressBookAppTest {
	@Test
	public void testUseCommandLine() throws Exception {
		// create and configure mock
		AddressBook aBook = Mockito.mock(AddressBook.class);
		AddressBookApp.addressBook = aBook;

		// Test calling of functions in AddressBook based on arguments
		String[] initArgs = {"init"};
		AddressBookApp.useCommandLine(initArgs);
		verify(aBook, times(1)).init();
		verifyNoMoreInteractions(aBook);

		String[] addArgs = {"add","first","last"};
		AddressBookApp.useCommandLine(addArgs);
		verify(aBook, times(1)).add(addArgs);
		verifyNoMoreInteractions(aBook);

		String[] listArgs = {"list","first","asc"};
		AddressBookApp.useCommandLine(listArgs);
		verify(aBook, times(1)).list(listArgs);
		verifyNoMoreInteractions(aBook);

		String[] editArgs = {"edit","1","first","bob"};
		AddressBookApp.useCommandLine(editArgs);
		verify(aBook, times(1)).edit(editArgs);
		verifyNoMoreInteractions(aBook);

		String[] viewArgs = {"view","1"};
		AddressBookApp.useCommandLine(viewArgs);
		verify(aBook, times(1)).view(viewArgs);
		verifyNoMoreInteractions(aBook);

		String[] delArgs = {"del","1"};
		AddressBookApp.useCommandLine(delArgs);
		verify(aBook, times(1)).del(delArgs);
		verifyNoMoreInteractions(aBook);

	}

}
