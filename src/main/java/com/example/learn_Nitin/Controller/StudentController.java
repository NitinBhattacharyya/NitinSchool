package com.example.learn_Nitin.Controller;

import com.example.learn_Nitin.model.Person;
import com.example.learn_Nitin.repository.CoursesRepository;
import com.example.learn_Nitin.repository.NitinClassRepository;
import com.example.learn_Nitin.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    CoursesRepository coursesRepository;
    @Autowired
    NitinClassRepository nitinClassRepository;

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(HttpSession session)
    {
        Person person=(Person) session.getAttribute("loggedInPerson");
        ModelAndView modelAndView=new ModelAndView("courses_enrolled.html");
        modelAndView.addObject("person",person);
        return modelAndView;
    }
}
