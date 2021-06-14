package com.reece.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "address_books")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@UUID", scope = AddressBook.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AddressBook implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@OneToMany(
			mappedBy = "addressBook",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<CustomerContact> customerContacts = new ArrayList<>();

	public void addCustomerContact(CustomerContact customerContact) {
		customerContacts.add(customerContact);
		customerContact.setAddressBook(this);
	}

	public void removeCustomerContact(CustomerContact customerContact) {
		customerContacts.remove(customerContact);
		customerContact.setAddressBook(null);
	}

	@Version
	@JsonIgnore
	private Integer version;
}
