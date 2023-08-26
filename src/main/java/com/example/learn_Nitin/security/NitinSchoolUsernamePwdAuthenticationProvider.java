package com.example.learn_Nitin.security;

import com.example.learn_Nitin.model.Person;
import com.example.learn_Nitin.model.Roles;
import com.example.learn_Nitin.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NitinSchoolUsernamePwdAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email=authentication.getName();
        String password=authentication.getCredentials().toString();
        Person person=personRepository.readByEmail(email);
        if(person!=null && person.getPersonID()>0 && passwordEncoder.matches(password,person.getPwd()))
        {
            return new UsernamePasswordAuthenticationToken(person.getName(),null,getGrantedAuthorities(person.getRoles()));
        }
        else {
            //will throw 401 error
            // (HTTP) 401 Unauthorized response status code indicates that the client request has not been completed because it lacks valid authentication credentials for the requested resource.
            throw new BadCredentialsException("Invalid credentials");
        }
    }
    private List<GrantedAuthority> getGrantedAuthorities(Roles roles)
    {
        List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
        //SimpleGrantedAuthority is an implementation of GrantedAuthority interface and Stores a String representation of an authority granted to the Authentication object.
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+roles.getRoleName()));
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
