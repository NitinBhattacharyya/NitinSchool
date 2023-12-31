package com.example.learn_Nitin.Controller;

import com.example.learn_Nitin.model.Person;
import com.example.learn_Nitin.service.PersonService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
//when we put a request mapping on top of a class,it will act as a prefix for all urls mapped inside this class
@RequestMapping("/public")
public class PublicController {
    @Autowired
    PersonService personService;
    @RequestMapping(value="/register",method= RequestMethod.GET)
    public String displayRegisterPage(@RequestParam(value="error",required = false)String error, Model model)
    {
        model.addAttribute("person",new Person());
        String errorMessage=null;
        if(error!=null)errorMessage="You are already registered";
        model.addAttribute("errorMessage",errorMessage);
        return "register.html";
    }
    @RequestMapping(value="/createUser",method=RequestMethod.POST)
    public String createUser(@Valid @ModelAttribute("person")Person person, Errors errors,Model model)
    {
        if(errors.hasErrors())
        {
            return "register.html";
        }
        boolean isSaved=personService.createNewPerson(person);
        if(isSaved)
        {
            return "redirect:/login?register=true";
        }
        else {
            //return "redirect:/register?error=true";
            //the above redirect statement for whatever reason keeps redirecting to /login instead
            return "redirect:/public/register?error=true";
        }
    }
}
