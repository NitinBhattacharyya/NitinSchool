package com.example.learn_Nitin.Controller;

import com.example.learn_Nitin.model.Courses;
import com.example.learn_Nitin.model.NitinClass;
import com.example.learn_Nitin.model.Person;
import com.example.learn_Nitin.repository.CoursesRepository;
import com.example.learn_Nitin.repository.NitinClassRepository;
import com.example.learn_Nitin.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
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
    @Autowired
    CoursesRepository coursesRepository;
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
        if(nitinClass.isPresent())
        {
            System.out.println("We are here");
            for(Person person: nitinClass.get().getPersons()) {
                System.out.println("Lets go");
                person.setNitinClass(null);
//                System.out.println("email "+person.getEmail()+" class_id "+person.getNitinClass().getClassId());
                personRepository.save(person);
            }
        }
//        nitinClass.get().setPersons(null);
        nitinClassRepository.deleteById(id);
        ModelAndView modelAndView=new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }
    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(@RequestParam int classId, HttpSession session,@RequestParam(value="error",required = false)String error)
    {
        String errorMessage=null;
        if(error!=null)errorMessage="Invalid Email Entered";
        ModelAndView modelAndView=new ModelAndView("students.html");
        Optional<NitinClass> nitinClass=nitinClassRepository.findById(classId);
        session.setAttribute("nitinClass",nitinClass.get());
        modelAndView.addObject("nitinClass",nitinClass.get());
        modelAndView.addObject("person",new Person());
        modelAndView.addObject("errorMessage",errorMessage);
        return modelAndView;
    }
    @PostMapping("/addStudent")
    public ModelAndView addStudents(@ModelAttribute("person")Person person,HttpSession session)
    {
        ModelAndView modelAndView=new ModelAndView();
        NitinClass nitinClass=(NitinClass) session.getAttribute("nitinClass");
        Person personEntity=personRepository.readByEmail(person.getEmail());
        if(personEntity==null || !(personEntity.getPersonID()>0))
        {
            modelAndView.setViewName("redirect:/admin/displayStudents?classId="+nitinClass.getClassId()+"&error=true");
            return modelAndView;
        }
        personEntity.setNitinClass(nitinClass);
//        personRepository.save(personEntity);
        nitinClass.getPersons().add(personEntity);
        nitinClassRepository.save(nitinClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId="+nitinClass.getClassId());
        return modelAndView;

    }
    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudents(@RequestParam int personId, HttpSession session)
    {
        NitinClass nitinClass=(NitinClass) session.getAttribute("nitinClass");
        Optional<Person> person=personRepository.findById(personId);
        person.get().setNitinClass(null);
        nitinClass.getPersons().remove(person.get());
        NitinClass nitinClassSaved=nitinClassRepository.save(nitinClass);
        session.setAttribute("nitinClass",nitinClassSaved);
        ModelAndView modelAndView=new ModelAndView("redirect:/admin/displayStudents?classId="+nitinClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses()
    {
        List<Courses> courses=coursesRepository.findAll();
        ModelAndView modelAndView=new ModelAndView("courses_secure.html");
        modelAndView.addObject("courses",courses);
        modelAndView.addObject("course",new Courses());
        return modelAndView;
    }
    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(@ModelAttribute("course")Courses course)
    {
        ModelAndView modelAndView=new ModelAndView();
        coursesRepository.save(course);
        modelAndView.setViewName("redirect:/admin/displayCourses");
        return modelAndView;
    }

    @GetMapping("/viewStudents")
    public ModelAndView viewStudents(@RequestParam int id,HttpSession session,@RequestParam(value="error",required = false)String error)
    {
        String errorMessage=null;
        if(error!=null)
        {
            errorMessage="Invalid Email Entered";
        }
        ModelAndView modelAndView=new ModelAndView("course_students.html");
        Optional<Courses> courses=coursesRepository.findById(id);
        session.setAttribute("courses",courses.get());
        modelAndView.addObject("courses",courses.get());
        modelAndView.addObject("person",new Person());
        modelAndView.addObject("errorMessage",errorMessage);
        return modelAndView;
    }
    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(@ModelAttribute("person")Person person,HttpSession session)
    {
        ModelAndView modelAndView=new ModelAndView();
        Courses courses=(Courses)session.getAttribute("courses");
        Person personEntity=personRepository.readByEmail(person.getEmail());
        if(personEntity==null || !(personEntity.getPersonID()>0))
        {
            modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId()+"&error=true");
            return modelAndView;
        }
        personEntity.getCourses().add(courses);
        courses.getPersons().add(personEntity);
//        personRepository.save(personEntity);
        Courses savedCourses=coursesRepository.save(courses);
        session.setAttribute("courses",savedCourses);
//        session.setAttribute("courses",courses);
        modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId());
        return modelAndView;
    }
    @GetMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(@RequestParam int personId,HttpSession session)
    {
        Courses courses=(Courses)session.getAttribute("courses");
        Optional<Person> person=personRepository.findById(personId);
        person.get().getCourses().remove(courses);
        courses.getPersons().remove(person.get());
        Courses savedCourse=coursesRepository.save(courses);
        session.setAttribute("courses",savedCourse);
        ModelAndView modelAndView=new ModelAndView("redirect:/admin/viewStudents?id="+courses.getCourseId());
        return modelAndView;
    }
}
