package com.example.learn_Nitin.repository;

import com.example.learn_Nitin.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path = "roles")
public interface RolesRepository extends JpaRepository<Roles,Integer> {
    Roles getByRoleName(String studentRole);
}
