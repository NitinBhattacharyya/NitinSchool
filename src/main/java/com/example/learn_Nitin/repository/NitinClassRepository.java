package com.example.learn_Nitin.repository;

import com.example.learn_Nitin.model.NitinClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NitinClassRepository extends JpaRepository<NitinClass,Integer> {
}
