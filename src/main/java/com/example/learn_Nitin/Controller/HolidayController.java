package com.example.learn_Nitin.Controller;

import com.example.learn_Nitin.model.Holiday;
import com.example.learn_Nitin.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class HolidayController {
    private HolidayRepository holidayRepository;
//    private List<Holiday> holidays;
    private Iterable<Holiday> holidays;
    @Autowired
    public HolidayController(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
//        this.holidays = holidayRepository.findAllHolidays();
        //for JPA
        this.holidays=holidayRepository.findAll();

    }

    @GetMapping("/holidays")
    public String displayHolidaysRequestParam(Model model, @RequestParam(required = false)Boolean festival, @RequestParam(required = false) Boolean federal) {

        if(festival==null && federal==null)
        {
            festival=true;
            federal=true;
        }

        model.addAttribute("festival",festival);
        model.addAttribute("federal",federal);
        Holiday.Type[] types = Holiday.Type.values();
        List<Holiday> holidayList= StreamSupport.stream(holidays.spliterator(),false).collect(Collectors.toList());
        for (Holiday.Type type : types) {
//            model.addAttribute(type.toString(),
//                    (holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
            model.addAttribute(type.toString(),
                    (holidayList.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }
        return "holidays.html";
    }
    @GetMapping(value={"/holidays/{display}","/holidays/"})
    public String displayHolidaysPathVariable(Model model,@PathVariable(required = false) String display)
    {
        if(null != display && display.equals("all")){
            model.addAttribute("festival",true);
            model.addAttribute("federal",true);
        }else if(null != display && display.equals("federal")){
            model.addAttribute("federal",true);
        }else if(null != display && display.equals("festival")){
            model.addAttribute("festival",true);
        }
        else {
            model.addAttribute("festival",true);
            model.addAttribute("federal",true);
        }
        Holiday.Type[] types = Holiday.Type.values();
        List<Holiday> holidayList= StreamSupport.stream(holidays.spliterator(),false).collect(Collectors.toList());
        for (Holiday.Type type : types) {
//            model.addAttribute(type.toString(),
//                    (holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
            model.addAttribute(type.toString(),
                    (holidayList.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }
        return "holidays.html";
    }
}
