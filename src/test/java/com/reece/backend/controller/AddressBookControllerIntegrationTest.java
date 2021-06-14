package com.reece.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reece.backend.model.entity.AddressBook;
import com.reece.backend.model.entity.CustomerContact;
import com.reece.backend.service.AddressBookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration
@WebMvcTest(controllers = AddressBookController.class)
public class AddressBookControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AddressBookService service;

    @Test
    public void testCreateNewAddressBookWithCustomerContacts_thenStatusOK() throws Exception {

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

        //when
        when(service.create(any(List.class)))
                .thenReturn(addressBook);

        //then
        mvc.perform(post("/addressBooks/")
                .content(asJsonString(Arrays.asList(customerContact1, customerContact2)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerContacts", hasSize(2)))
                .andExpect(jsonPath("$.customerContacts[0].customerName", is(customerContact1.getCustomerName())))
                .andExpect(jsonPath("$.customerContacts[0].phoneNumber", is(customerContact1.getPhoneNumber())));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
    @Test
    public void testGetAdmission() throws Exception {

        Long id = 1L;

        CustomerContact customerContact1 = new CustomerContact();
        customerContact1.setId(id);
        customerContact1.setPatientName("James");
        customerContact1.setDateOfBirth(LocalDate.of(1979, 01, 01));
        customerContact1.setGender(Gender.MALE);
        customerContact1.setCategory(Category.EMERGENCY);

        given(service.get(id)).willReturn(customerContact1);

        mvc.perform(get("/admissions/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientName", is(customerContact1.getPatientName())))
                .andExpect(jsonPath("$.gender", is(customerContact1.getGender().getDescription())))
                .andExpect(jsonPath("$.category", is(customerContact1.getCategory().getDescription())));
    }

    @Test
    public void testCreateAValidAdmission() throws Exception {

        Long id = 1L;

        CustomerContact customerContact1 = new CustomerContact();
        customerContact1.setId(id);
        customerContact1.setPatientName("James");
        customerContact1.setDateOfBirth(LocalDate.of(1979, 01, 01));
        customerContact1.setGender(Gender.MALE);
        customerContact1.setCategory(Category.EMERGENCY);
        customerContact1.setSource("Hospital A");

        given(service.create(customerContact1)).willReturn(customerContact1);

        String json = "{\"@UUID\":\"67792bbb-0446-4a75-bfce-db3bbf342b96\",\"id\":1,\"dateAdmission\":null,\"patientName\":\"James\",\"dateOfBirth\":\"1979-01-01\",\"gender\":\"Male\",\"category\":\"Emergency\",\"source\":\"Hospital A\"}";

        mvc.perform(post("/admissions/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientName", is(customerContact1.getPatientName())))
                .andExpect(jsonPath("$.gender", is(customerContact1.getGender().getDescription())))
                .andExpect(jsonPath("$.category", is(customerContact1.getCategory().getDescription())))
                .andExpect(jsonPath("$.source", is(customerContact1.getSource())));
    }

    @Test
    public void testCreateAInvalidAdmissionReturnsBadRequestStatus() throws Exception {

        Long id = 1L;

        CustomerContact customerContact1 = new CustomerContact();
        customerContact1.setId(id);
        customerContact1.setPatientName("James");
        customerContact1.setDateOfBirth(LocalDate.of(1979, 01, 01));
        customerContact1.setGender(Gender.MALE);
        customerContact1.setCategory(Category.EMERGENCY);

        given(service.create(customerContact1)).willReturn(customerContact1);

        String jsonInvalidCategory = "{\"@UUID\":\"67792bbb-0446-4a75-bfce-db3bbf342b96\",\"id\":1,\"dateAdmission\":null,\"patientName\":\"James\",\"dateOfBirth\":\"1979-01-01\",\"gender\":\"Male\",\"category\":\"Invalid Category\",\"source\":null}";

        mvc.perform(post("/admissions/")
                .content(jsonInvalidCategory)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }



    @Test
    public void testUpdateAValidAdmission() throws Exception {

        Long id = 1L;

        CustomerContact customerContact1 = new CustomerContact();
        customerContact1.setId(id);
        customerContact1.setPatientName("Jessie");
        customerContact1.setDateOfBirth(LocalDate.of(1979, 01, 01));
        customerContact1.setGender(Gender.FEMALE);
        customerContact1.setCategory(Category.NORMAL);

        given(service.exists(id)).willReturn(true);
        doNothing().when(service).update(id, customerContact1);

        String jsonUpdated = "{\"@UUID\":\"67792bbb-0446-4a75-bfce-db3bbf342b96\",\"id\":1,\"dateAdmission\":null,\"patientName\":\"Jessie\",\"dateOfBirth\":\"1979-01-01\",\"gender\":\"Female\",\"category\":\"Normal\",\"source\":null}";

        mvc.perform(put("/admissions/" + id)
                .content(jsonUpdated)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testControllerDeleteAValidAdmission() throws Exception {

        Long id = 1L;

        given(service.exists(id)).willReturn(true);
        doNothing().when(service).delete(id);

        mvc.perform(delete("/admissions/"+ id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    */
}
