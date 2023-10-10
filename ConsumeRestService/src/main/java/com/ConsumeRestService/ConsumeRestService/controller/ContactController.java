package com.ConsumeRestService.ConsumeRestService.controller;

import com.ConsumeRestService.ConsumeRestService.model.Contact;
import com.ConsumeRestService.ConsumeRestService.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ConsumeRestService.ConsumeRestService.proxy.ContactProxy;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class ContactController {

    @Autowired
    ContactProxy contactProxy;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WebClient webClient

    @GetMapping("/getMessages")
    public List<Contact> getMessages(@RequestParam("status")String status)
    {
        return contactProxy.getMessagesByStatus(status);
    }

    @PostMapping("/saveMsg")
    public ResponseEntity<Response> saveMsg(@RequestBody Contact contact)
    {
        String url="http://localhost:8080/NitinSchool/api/contact/saveMsg";
        HttpHeaders headers=new HttpHeaders();
        headers.add("invocationFrom","RestTemplate");
        HttpEntity<Contact> httpEntity=new HttpEntity<>(contact,headers);
        ResponseEntity<Response> responseEntity=restTemplate.exchange(url, HttpMethod.POST,httpEntity,Response.class);
        return responseEntity;
    }

    @PostMapping("/saveMessage")
    public Mono<Response> saveMessage(@RequestBody Contact contact)
    {
        String uri="http://localhost:8080/NitinSchool/api/contact/saveMsg";
        return webClient.post().uri(uri)
                .header("invocationFrom","webClient")
                .body(Mono.just(contact),Contact.class)
                .retrieve()
                .bodyToMono(Response.class);
    }
}
