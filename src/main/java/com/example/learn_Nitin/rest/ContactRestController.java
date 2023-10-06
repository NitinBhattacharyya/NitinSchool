package com.example.learn_Nitin.rest;

import com.example.learn_Nitin.model.Contact;
import com.example.learn_Nitin.model.Response;
import com.example.learn_Nitin.repository.ContactRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/contact")
public class ContactRestController {
    @Autowired
    ContactRepository contactRepository;

    @GetMapping("/getMessagesByStatus")
//    @ResponseBody
    public List<Contact> getMessagesByStatus(@RequestParam(name="status") String status)
    {
        return contactRepository.findByStatus(status);
    }

    @GetMapping("/getAllMsgsByStatus")
//    @ResponseBody
    public List<Contact> getAllMsgsByStatus(@RequestBody Contact contact){
        if(null != contact && null != contact.getStatus()){
            return contactRepository.findByStatus(contact.getStatus());
        }else{
            return List.of();
        }
    }

    @PostMapping("/saveMsg")
    public ResponseEntity<Response> saveMsg(@RequestHeader("invocationFrom") String invocationFrom, @Valid @RequestBody Contact contact)
    {
        log.info(String.format("Header invocationFrom= %s",invocationFrom));
        contactRepository.save(contact);
        Response response=new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message Saved Successfully");
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("isSaved","true")
                .body(response);
    }
}
