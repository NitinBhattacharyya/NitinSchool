package com.example.learn_Nitin.service;

import com.example.learn_Nitin.constants.NitinSchoolConstants;
import com.example.learn_Nitin.model.Contact;
import com.example.learn_Nitin.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
//        contact.setCreatedBy(NitinSchoolConstants.ANONYMOUS);
//        contact.setCreatedAt(LocalDateTime.now());
//        int result=contactRepository.saveContactMsg(contact);
        //once the save operation is completed by spring data jpa it is going to return the same object of
        //pojo class along with the primary key value that got generated and stored into the database
        Contact savedContact=contactRepository.save(contact);
//        if(result>0)
//        {
//            isSaved=true;
//        }
        if(savedContact!=null && savedContact.getContactId()>0)
        {
            isSaved=true;
        }
        //TODO - Need to persist the data into the DB table
        log.info(contact.toString());
        return isSaved;
    }

    public List<Contact> findMsgsWithOpenStatus() {
//        List<Contact> contactMsgs=contactRepository.findMsgsWithStatus(NitinSchoolConstants.OPEN);
        List<Contact> contactMsgs=contactRepository.findByStatus(NitinSchoolConstants.OPEN);
        return contactMsgs;
    }
    public Page<Contact> findMsgsWithOpenStatus(int pageNum, String sortField, String sortDir){
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        Page<Contact> msgPage = contactRepository.findByStatusWithQuery(
                NitinSchoolConstants.OPEN,pageable);
        //The below application of @NamedQuery will fail to apply sorting,so its better to use @Query
        //The below is the reason why
        /*
        After a brief investigation, this is a current limitation of using named queries. As Spring Data JPA uses the named queries by name (as the name suggests), we don't really access the query internals. The latter is needed to apply a Sort instance to the query as we effectively have to extend it with an ORDER BY clause.

That's why the lookup of the named query fails and the query lookup falls back to the deriving strategy. I can see that this is suboptimal and needs to be improved. Note that you should find a warning in your logs that indicates the method taking a Pageable not getting any sort criteria applied.

While we certainly could apply a quick fix here to just throw an exception in this case I think we can even do better and implement support for Sort on named queries in general. There already is code that will extract the original query from a JPA Query instance if the persistence provider supports that (Hibernate, Eclipselink, OpenJPA do). That means we could actually extract the original query in such scenarios and basically treat the query obtained as if it was manually defined in @Query.

Suggested workaround for now: use @Query on the repository interface (MHO that's the more logical place to declare queries anyway as that's where they get executed and don't pollute the domain model)
         */
//        Page<Contact> msgPage=contactRepository.findOpenMsgs(NitinSchoolConstants.OPEN,pageable);
//        Page<Contact> msgPage=contactRepository.findOpenMsgsNative(NitinSchoolConstants.OPEN,pageable);
        return msgPage;
    }

    public boolean updateMsgStatus(int contactId, String updatedBy) {
        boolean isUpdated=false;
//        int result=contactRepository.updateMsgStatus(contactId,NitinSchoolConstants.CLOSE,updatedBy);
        //First we need to fetch the details and bind it to a pojo class using the primary key contactID
        //if contactID is not present it may return null so using Optional
//        Optional<Contact> contact=contactRepository.findById(contactId);
        //contact.ifPresent() will check if contact is null or not
//        contact.ifPresent(contact1 -> {
//            contact1.setStatus(NitinSchoolConstants.CLOSE);
////            contact1.setUpdatedBy(updatedBy);
////            contact1.setUpdatedAt(LocalDateTime.now());
//        });
//        Contact updatedContact=contactRepository.save(contact.get());
        //The above is not required since we wrote a custom query to update directly
//        int rowsAffected=contactRepository.updateStatusById(NitinSchoolConstants.CLOSE,contactId);
        int rowsAffected=contactRepository.updateMsgStatus(NitinSchoolConstants.CLOSE,contactId);
//        int rowsAffected=contactRepository.updateMsgStatusNative(NitinSchoolConstants.CLOSE,contactId);
//        if(updatedContact!=null && updatedContact.getUpdatedBy()!=null)
//        {
//            isUpdated=true;
//        }
        if(rowsAffected>0)isUpdated=true;
//        if(result>0)
//        {
//            isUpdated=true;
//        }
        return isUpdated;
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
