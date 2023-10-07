package com.example.learn_Nitin.rest;

import com.example.learn_Nitin.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@Order(1)//Since @RestController is basically @Controller+@ResponseBody,even writing annotations=RestController.class does not work
//Spring randomly picks which GlobalExceptionHandler to use. We use @Order to give priority to a particular Controller
public class GlobalExceptionRestController extends ResponseEntityExceptionHandler {

    //The below method will be executed if someone is sending invalid input data
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Response response=new Response(status.toString(),ex.getBindingResult().toString());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> exceptionHandler(Exception exception)
    {
        Response response=new Response("500",exception.getMessage());
        System.out.println("exception: "+exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
