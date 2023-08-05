package com.example.learn_Nitin.Controller;

import com.example.learn_Nitin.model.Contact;
import com.example.learn_Nitin.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
public class ContactController {
    private final ContactService contactService;
//    private static Logger log= LoggerFactory.getLogger(ContactController.class);
    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping("/contact")
    public String displayContactPage(Model model)
    {
        //indicating to thymeleaf that this page will hold data belong to contact object
        //and any validation that I define inside the contact bean should be performed by the
        //thymeleaf and spring mvc whenever the user is submitting the data
        model.addAttribute("contact",new Contact());
        return "contact.html";
    }
//    @RequestMapping(value="/saveMsg",method=POST)
//    public ModelAndView saveMessage(@RequestParam String name, @RequestParam String mobileNum, @RequestParam String email, @RequestParam String subject, @RequestParam String message)
//    {
//        log.info("Name : " + name);
//        log.info("Mobile Number : " + mobileNum);
//        log.info("Email Address : " + email);
//        log.info("Subject : " + subject);
//        log.info("Message : " + message);
//        return new ModelAndView("redirect:/contact");
//    }

    //First we put @Valid so that spring knows that it has to do validations based on the annotations mentioned in contact.java
    // followed by @ModelAttribute("contact") which says there is a model attribute with name "contact" that is binded with the object contact
    //If there are any errors identified during this process spring will send that information under errors object

    @RequestMapping(value="/saveMsg",method=POST)
    public ModelAndView saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors)
    {
        if(errors.hasErrors())
        {
            log.error("Contact form validation failed due to:"+errors.toString());
            return new ModelAndView("contact.html");
        }
        contactService.saveMessageDetails(contact);
//        contactService.setCounter(contactService.getCounter()+1);
//        log.info("Number of times Contact form is submitted:"+contactService.getCounter());
        return new ModelAndView("redirect:/contact");
    }
    @RequestMapping("/displayMessages")
    public ModelAndView displayMessages(Model model)
    {
        List<Contact> contactMsgs=contactService.findMsgsWithOpenStatus();
        ModelAndView modelAndView=new ModelAndView("messages.html");
        modelAndView.addObject("contactMsgs",contactMsgs);
        return modelAndView;
    }
    @RequestMapping(value = "/closeMsg",method = GET)
    public String closeMsg(@RequestParam int id, Authentication authentication)
    {
        contactService.updateMsgStatus(id,authentication.getName());
        return "redirect:/displayMessages";
    }

}
