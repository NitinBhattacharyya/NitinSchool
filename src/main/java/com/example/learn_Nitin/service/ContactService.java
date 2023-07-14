package com.example.learn_Nitin.service;

import com.example.learn_Nitin.constants.NitinSchoolConstants;
import com.example.learn_Nitin.model.Contact;
import com.example.learn_Nitin.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;

@Slf4j
@Service
//@RequestScope
//@SessionScope
//@ApplicationScope
public class ContactService {
    /*
     * Save Contact Details into DB
     * @param contact
     * @return boolean
     */
//    private int counter=0;

//    public ContactService() {
//        System.out.println("Contact Service Bean initialized");
//    }
    @Autowired
    ContactRepository contactRepository;
    public boolean saveMessageDetails(Contact contact) {
        boolean isSaved = false;
        contact.setStatus(NitinSchoolConstants.OPEN);
        contact.setCreatedBy(NitinSchoolConstants.ANONYMOUS);
        contact.setCreatedAt(LocalDateTime.now());
        int result=contactRepository.saveContactMsg(contact);
        if(result>0)
        {
            isSaved=true;
        }
        //TODO - Need to persist the data into the DB table
        log.info(contact.toString());
        return isSaved;
    }
//    public int getCounter()
//    {
//        return counter;
//    }
//    public void setCounter(int counter)
//    {
//        this.counter=counter;
//    }
}
