package com.example.learn_Nitin.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;

@Slf4j
@Controller
public class LoginController {
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String displayLoginPage(@RequestParam(value = "error", required = false) String error,
                                   @RequestParam(value = "logout", required = false) String logout,@RequestParam(value="register",required = false) String register, Model model) {
        String errorMessge = null;
        if (error != null) {
            errorMessge = "Username or Password is incorrect !!";
        }
        if (logout != null) {
            errorMessge = "You have been successfully logged out !!";
        }
        if(register!=null)
        {
            errorMessge="Your Registration is Successful.Login with registered credentials";
        }
        model.addAttribute("errorMessge", errorMessge);
        return "login.html";
    }
    @RequestMapping(value="/logout",method=RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response)
    {
        //whenever successfull authentication happens during login operation
        //those authentication details will be saved inside SecurityContextHolder
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        //Instead of getting the authentication details in the above manner
        //we can also pass Authentication object as parameter to logoutPage mehthod
        //and spring will automatically create and inject the bean
        if(auth!=null)
        {
            //if the authentication object is not null then it is an indication
            //the login operation is successfull now the user can perform logout
            new SecurityContextLogoutHandler().logout(request,response,auth);
        }
        return "redirect:/login?logout=true";
    }
}
