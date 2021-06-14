package com.reece.backend.repository;

import com.reece.backend.model.entity.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by tolim on 9/08/2018.
 */
public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {

}
