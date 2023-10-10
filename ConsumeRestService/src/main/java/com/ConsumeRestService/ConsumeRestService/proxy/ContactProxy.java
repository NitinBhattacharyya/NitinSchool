package com.ConsumeRestService.ConsumeRestService.proxy;

import com.ConsumeRestService.ConsumeRestService.config.ProjectConfiguration;
import feign.Headers;
import com.ConsumeRestService.ConsumeRestService.model.Contact;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "contact",url = "http://localhost:8080/NitinSchool/api/contact",configuration = ProjectConfiguration.class)
public interface ContactProxy {
    @RequestMapping(method= RequestMethod.GET,value = "/getMessagesByStatus")
    @Headers(value = "Content-Type: application/json")
    public List<Contact> getMessagesByStatus(@RequestParam("status") String status);
}
