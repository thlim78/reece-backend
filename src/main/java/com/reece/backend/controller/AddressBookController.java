package com.reece.backend.controller;

import com.reece.backend.exception.EntityNotFoundException;
import com.reece.backend.model.entity.AddressBook;
import com.reece.backend.model.entity.CustomerContact;
import com.reece.backend.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/addressBooks")
@Slf4j
public class AddressBookController {

	private final AddressBookService addressBookService;

	public AddressBookController(AddressBookService addressBookService) {
			this.addressBookService = addressBookService;
	}

	@PostMapping("/")
	public AddressBook create(@RequestBody List<CustomerContact> customerContacts) {
		return addressBookService.create(customerContacts);
	}

	@PostMapping("/{addressBookId}/contacts")
	public AddressBook addCustomerContacts(@PathVariable Long addressBookId, @RequestBody List<CustomerContact> customerContacts) {
		validateRequest(addressBookId);
		return addressBookService.addCustomerContacts(addressBookId, customerContacts);
	}

	@DeleteMapping("/{addressBookId}/contacts")
	public AddressBook deleteCustomerContacts(@PathVariable Long addressBookId, @RequestBody List<Long> customerContactIds) {
		validateRequest(addressBookId);
		return addressBookService.deleteCustomerContactsByIds(addressBookId, customerContactIds);
	}

	@GetMapping("/{addressBookId}")
	public AddressBook get(@PathVariable Long addressBookId) {
		validateRequest(addressBookId);
		return addressBookService.get(addressBookId);
	}

	@GetMapping("/")
	public List<AddressBook> getUniqueCustomerContacts() {
		//todo: not yet implemented
		return addressBookService.getUniqueCustomerContacts();
	}


	@PostMapping("/batchInsert")
	public List<AddressBook> addCustomerContactsInMultipleAddressBooks(@RequestBody List<AddressBook> addressBooks) {
		//validate all addressBook entries
		addressBooks
				.stream()
				.forEach(addressBook -> validateRequest(addressBook.getId()));

		return addressBooks
				.stream()
				.map(addressBook -> addressBookService.addCustomerContacts(addressBook.getId(), addressBook.getCustomerContacts()))
				.collect(Collectors.toList());
	}

	@PutMapping("/batchUpdate")
	public List<AddressBook> updateCustomerContactsInMultipleAddressBooks(@RequestBody List<AddressBook> addressBooks) {
		//validate all addressBook entries
		addressBooks
				.stream()
				.forEach(addressBook -> validateRequest(addressBook.getId()));

		//todo: not yet implemented
		return addressBooks
				.stream()
				.map(addressBook -> addressBookService.updateCustomerContacts(addressBook.getId(), addressBook.getCustomerContacts()))
				.collect(Collectors.toList());
	}


	@DeleteMapping("/batchDelete")
	public List<AddressBook> deleteCustomerContactsInMultipleAddressBooks(@RequestBody List<AddressBook> addressBooks) {
		//validate all addressBook entries
		addressBooks
				.stream()
				.forEach(addressBook -> validateRequest(addressBook.getId()));

		return addressBooks
				.stream()
				.map(addressBook -> {
					List<Long> customerContactIds = addressBook.getCustomerContacts().stream().map(contact -> contact.getId()).collect(Collectors.toList());
					return addressBookService.deleteCustomerContactsByIds(addressBook.getId(), customerContactIds);
				})
				.collect(Collectors.toList());
	}

	private void validateRequest(Long addressBookId) {
		if (!addressBookService.exists(addressBookId)) {
			throw new EntityNotFoundException("addressBooks", addressBookId);
		}
	}
}
