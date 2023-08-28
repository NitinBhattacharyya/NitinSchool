package com.example.learn_Nitin.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
    public ModelAndView displayClasses()
    {
        ModelAndView modelAndView=new ModelAndView("classes.html");
        return modelAndView;
    }
}
