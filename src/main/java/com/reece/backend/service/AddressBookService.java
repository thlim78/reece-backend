package com.reece.backend.service;

import com.reece.backend.model.entity.AddressBook;
import com.reece.backend.model.entity.CustomerContact;
import com.reece.backend.repository.AddressBookRepository;
import com.reece.backend.repository.CustomerContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Created by tolim on 10/08/2018.
 */
public interface AddressBookService {
    AddressBook create(List<CustomerContact> customerContacts);
    AddressBook addCustomerContacts(Long addressBookId, List<CustomerContact> customerContacts);
    AddressBook deleteCustomerContactsByIds(Long addressBookId, List<Long> customerContactIds);
    AddressBook updateCustomerContacts(Long addressBookId, List<CustomerContact> customerContacts);
    AddressBook get(Long addressBookId);
    List<AddressBook> getUniqueCustomerContacts();

    Boolean exists(Long id);

    @Service
    @Transactional
    class Default implements AddressBookService {

        @Autowired
        private AddressBookRepository addressBookRepository;

        @Autowired
        private CustomerContactRepository customerContactRepository;

        public Default() {
        }

        public AddressBook create(List<CustomerContact> customerContacts) {
            AddressBook addressBook = new AddressBook();
            customerContacts.stream().forEach(contact -> {
                contact.setAddressBook(addressBook);
                addressBook.addCustomerContact(contact);
            });
            return addressBookRepository.save(addressBook);
        }

        public AddressBook addCustomerContacts(Long addressBookId, List<CustomerContact> customerContacts) {
            AddressBook addressBook = get(addressBookId);
            customerContacts.stream().forEach(contact -> {
                contact.setAddressBook(addressBook);
                addressBook.addCustomerContact(contact);
            });
            return addressBookRepository.save(addressBook);
        }

        public AddressBook deleteCustomerContactsByIds (Long addressBookId, List<Long> customerContactIds) {
            customerContactRepository.deleteAllById(customerContactIds);
            return get(addressBookId);
        }

        public AddressBook updateCustomerContacts(Long addressBookId, List<CustomerContact> customerContacts) {
            //todo: not implemented yet
            return new AddressBook();
        }

        public AddressBook get(Long addressBookId) {
            return addressBookRepository.getById(addressBookId);
        }

        //
        public List<AddressBook> getUniqueCustomerContacts() {
            //todo: not implemented yet
            return Collections.emptyList();
        }

        public Boolean exists(Long id) {
            return addressBookRepository.existsById(id);
        }
    }
}
