package com.reece.backend.service;

import com.reece.backend.model.entity.AddressBook;
import com.reece.backend.model.entity.CustomerContact;
import com.reece.backend.repository.AddressBookRepository;
import com.reece.backend.repository.CustomerContactRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AddressBookServiceImplIntegrationTest {

    @TestConfiguration
    static class AddressBookServiceImplTestContextConfiguration {

        @Bean
        public AddressBookService addressBookService() {
            return new AddressBookService.Default();
        }
    }

    @MockBean
    AddressBookRepository addressBookRepository;

    @MockBean
    CustomerContactRepository customerContactRepository;

    @Autowired
    private AddressBookService addressBookService;

    @Before
    public void setUp() {

    }

    @Test
    public void testCreateAddressBookWithCustomerContacts() {
        //given
        AddressBook addressBook = new AddressBook();
        addressBook.setId(1L);
        CustomerContact customerContact1 = new CustomerContact();
        customerContact1.setCustomerName("Tom");
        customerContact1.setPhoneNumber("999999999");
        customerContact1.setAddressBook(addressBook);

        CustomerContact customerContact2 = new CustomerContact();
        customerContact2.setCustomerName("James");
        customerContact2.setPhoneNumber("888888888");
        customerContact2.setAddressBook(addressBook);

        addressBook.addCustomerContact(customerContact1);
        addressBook.addCustomerContact(customerContact2);

        when(addressBookRepository.save(any(AddressBook.class)))
                .thenReturn(addressBook);

        //when
        AddressBook addressBook1 = addressBookService.create(Arrays.asList(customerContact1, customerContact2));

        //then
        assertThat(addressBook1.getId()).isEqualTo(1L);
        assertThat(addressBook1.getCustomerContacts().size()).isEqualTo(2);
    }

    /*
    @Test
    public void testServiceGetAdmission() {
        //given
        Long id = 1L;

        CustomerContact expected = new CustomerContact();
        expected.setId(id);
        expected.setPatientName("James");
        expected.setDateOfBirth(LocalDate.of(1979, 01, 01));
        expected.setGender(Gender.MALE);
        expected.setCategory(Category.EMERGENCY);

        when(customerContactRepository.getOne(id)).thenReturn(expected);

        // when
        CustomerContact actual = addressBookService.get(id);

        // then
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getPatientName()).isEqualToIgnoringCase(expected.getPatientName());
        assertThat(actual.getDateOfBirth()).isEqualTo(expected.getDateOfBirth());
        assertThat(actual.getGender()).isEqualTo(expected.getGender());
        assertThat(actual.getCategory()).isEqualTo(expected.getCategory());
    }
    */
}
