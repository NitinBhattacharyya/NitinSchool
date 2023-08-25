package com.example.learn_Nitin.repository;

import com.example.learn_Nitin.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}
