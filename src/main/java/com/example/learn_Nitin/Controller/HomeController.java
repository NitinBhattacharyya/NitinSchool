package com.example.learn_Nitin.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    //Map multiple paths
    @RequestMapping(value={"","/","/home"})
    public String displayHome()
    {
        //model.addAttribute("username","John Doe");
        //Thymeleaf is smart enough to read the variable username from the model object
        //and generate a html code after populating the username value inside th:text
        //and will send to the browser inside response
        return "home.html";
    }


}
