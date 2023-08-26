package com.example.learn_Nitin.service;

import com.example.learn_Nitin.constants.NitinSchoolConstants;
import com.example.learn_Nitin.model.Person;
import com.example.learn_Nitin.model.Roles;
import com.example.learn_Nitin.repository.PersonRepository;
import com.example.learn_Nitin.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createNewPerson(Person person) {
        boolean isSaved=false;
        Roles role=rolesRepository.getByRoleName(NitinSchoolConstants.STUDENT_ROLE);
        person.setRoles(role);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        Person exists=personRepository.readByEmail(person.getEmail());
        if(exists==null)person=personRepository.save(person);
        else person=null;
        if(person!=null && person.getPersonID()>0)
        {
            isSaved=true;
        }
        return isSaved;
    }
}
