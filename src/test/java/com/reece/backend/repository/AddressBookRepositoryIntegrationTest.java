package com.reece.backend.repository;

import com.reece.backend.model.entity.AddressBook;
import com.reece.backend.model.entity.CustomerContact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AddressBookRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Test
    public void testSavingAddressBookWithCustomerContacts() {
        //given
        AddressBook addressBook = new AddressBook();
        CustomerContact customerContact1 = new CustomerContact();
        customerContact1.setCustomerName("James");
        customerContact1.setPhoneNumber("888888888");
        customerContact1.setAddressBook(addressBook);

        CustomerContact customerContact2 = new CustomerContact();
        customerContact2.setCustomerName("Tom");
        customerContact2.setPhoneNumber("999999999");
        customerContact2.setAddressBook(addressBook);

        addressBook.setCustomerContacts(Arrays.asList(customerContact1,customerContact2));

        AddressBook addressBook1 = addressBookRepository.save(addressBook);

        assertThat(addressBook1.getCustomerContacts().size()).isEqualTo(2);
        assertThat(addressBook1.getId()).isNotNull().isGreaterThan(0L);
        assertThat(addressBookRepository.getById(addressBook1.getId())).isNotNull();
    }

    /*
    @Test
    public void testRepositoryGetById() {
        //given
        CustomerContact customerContact1 = new CustomerContact();
        customerContact1.setPatientName("Tom");
        customerContact1.setDateOfBirth(LocalDate.of(1981, 01, 01));
        customerContact1.setGender(Gender.INTERSEX);
        customerContact1.setCategory(Category.EMERGENCY);

        customerContact1 = entityManager.persist(customerContact1);
        entityManager.flush();

        // when
        CustomerContact customerContact = customerContactRepository.getOne(customerContact1.getId());

        //then
        assertThat(customerContact.getId()).isEqualTo(customerContact1.getId());
        assertThat(customerContact.getPatientName()).isEqualToIgnoringCase(customerContact1.getPatientName());
        assertThat(customerContact.getDateOfBirth()).isEqualTo(customerContact1.getDateOfBirth());
        assertThat(customerContact.getGender()).isEqualTo(customerContact1.getGender());
        assertThat(customerContact.getCategory()).isEqualTo(customerContact1.getCategory());
    }

    @Test
    public void testRepositoryUpdate() {
        //given
        CustomerContact customerContact1 = new CustomerContact();
        customerContact1.setPatientName("Hanks");
        customerContact1.setDateOfBirth(LocalDate.of(1988, 8, 8));
        customerContact1.setGender(Gender.MALE);
        customerContact1.setCategory(Category.OUTPATIENT);

        customerContact1 = entityManager.persist(customerContact1);
        entityManager.flush();

        String patientName = "Tom Hanks";
        customerContact1.setPatientName(patientName);
        customerContact1.setGender(Gender.INTERSEX);

        // when
        CustomerContact customerContact = customerContactRepository.save(customerContact1);

        //then
        assertThat(customerContact.getPatientName()).isEqualToIgnoringCase(patientName);
        assertThat(customerContact.getGender()).isEqualTo(Gender.INTERSEX);
    }


    @Test (expected = JpaObjectRetrievalFailureException.class)
    public void testRepositoryDelete() {
        //given
        CustomerContact customerContact1 = new CustomerContact();
        customerContact1.setPatientName("Jessie");
        customerContact1.setDateOfBirth(LocalDate.of(1980, 8, 8));
        customerContact1.setGender(Gender.FEMALE);
        customerContact1.setCategory(Category.OUTPATIENT);

        customerContact1 = entityManager.persist(customerContact1);
        entityManager.flush();

        // before delete
        assertThat(customerContactRepository.getOne(customerContact1.getId())).isNotNull();

        // when
        customerContactRepository.deleteById(customerContact1.getId());

        //after delete throws entity not found exception
        customerContactRepository.getOne(customerContact1.getId());
    }*/

}

