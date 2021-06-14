package com.reece.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import java.io.Serializable;
import javax.persistence.*;

@Data
@Entity
@Table(name = "customer_contacts")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@UUID", scope = CustomerContact.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CustomerContact implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_book_id")
	@JsonIgnore
	private AddressBook addressBook;

	@Column(name = "name")
	private String customerName;

	@Column(name = "phone")
	private String phoneNumber;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CustomerContact )) return false;
		return id != null && id.equals(((CustomerContact) o).getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Version
	@JsonIgnore
	private Integer version;
}
