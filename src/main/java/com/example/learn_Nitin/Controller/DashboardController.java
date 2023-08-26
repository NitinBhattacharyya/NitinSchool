package com.example.learn_Nitin.Controller;

import com.example.learn_Nitin.model.Person;
import com.example.learn_Nitin.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class DashboardController {
    @Autowired
    PersonRepository personRepository;
    //HttpSession is an interface available inside servlet library.Using the session parameter we can store any information inside our session and we
    //fetch this information whenever we want inside other controller classes
    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session) {
        Person person=personRepository.readByEmail(authentication.getName());
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        //Binds an object to this session, using the name specified. If an object of the same name is already bound to the session, the object is replaced.
        session.setAttribute("loggedInPerson",person);
//        throw new RuntimeException("Its been a bad day!!");
        return "dashboard.html";
    }
}
