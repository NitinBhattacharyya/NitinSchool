package com.example.learn_Nitin.Controller;

import com.example.learn_Nitin.model.NitinClass;
import com.example.learn_Nitin.model.Person;
import com.example.learn_Nitin.repository.NitinClassRepository;
import com.example.learn_Nitin.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    NitinClassRepository nitinClassRepository;
    @RequestMapping(value = "/displayClasses",method = RequestMethod.GET)
    public ModelAndView displayClasses()
    {
        ModelAndView modelAndView=new ModelAndView("classes.html");
        List<NitinClass> nitinClasses=nitinClassRepository.findAll();
        modelAndView.addObject("nitinClass",new NitinClass());
        modelAndView.addObject("nitinClasses",nitinClasses);
        return modelAndView;
    }
    @PostMapping("/addNewClass")
    public ModelAndView addNewClass(@ModelAttribute("nitinClass")NitinClass nitinClass)
    {
        nitinClassRepository.save(nitinClass);
        ModelAndView modelAndView=new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(@RequestParam int id)
    {
        Optional<NitinClass> nitinClass=nitinClassRepository.findById(id);
        for(Person person:nitinClass.get().getPersons())
        {
            person.setNitinClass(null);
            personRepository.save(person);
        }
        nitinClassRepository.deleteById(id);
        ModelAndView modelAndView=new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }
    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(@RequestParam int classId)
    {
        ModelAndView modelAndView=new ModelAndView("students.html");
        return modelAndView;
    }
}
