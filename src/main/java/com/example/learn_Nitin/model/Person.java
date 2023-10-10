package com.example.learn_Nitin.model;

import com.example.learn_Nitin.annotation.FieldsValueMatch;
import com.example.learn_Nitin.annotation.PasswordValidator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

//@Data
/*
Very useful explanation given in below two articles as to why @Data annotation should
be avoided for JPA entity classes
https://jpa-buddy.com/blog/lombok-and-jpa-what-may-go-wrong/
https://thorben-janssen.com/lombok-hibernate-how-to-avoid-common-pitfalls/
 */
@Getter
@Setter
@Entity
@FieldsValueMatch.List({

        @FieldsValueMatch(
                field="email",
                fieldMatch = "confirmEmail",
                message="Email addresses do not match"
        ),
        @FieldsValueMatch(
                field = "pwd",
                fieldMatch = "confirmPwd",
                message="Passwords do not match!"
        )
})
public class Person extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name="native",strategy = "native")
    @Column(name="person_id")
    private int personID;

    @NotBlank(message="Name must not be blank")
    @Size(min=3,message="Name must be atleast 3 characters")
    private String name;

    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message="Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    private String email;

    @NotBlank(message="Confirm Email must not be blank")
    @Email(message = "Please provide a valid confirm email address" )
    @Transient
    @JsonIgnore
    private String confirmEmail;

    @NotBlank(message="Password must not be blank")
    @Size(min=5, message="Password must be at least 5 characters long")
    @PasswordValidator
    @JsonIgnore
    private String pwd;

    @NotBlank(message="Confirm Password must not be blank")
    @Size(min=5, message="Confirm Password must be at least 5 characters long")
    @Transient
    @JsonIgnore
    private String confirmPwd;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST, targetEntity = Roles.class)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id",nullable = false)
    private Roles roles;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL, targetEntity = Address.class)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id",nullable = true)
    private Address address;

    //Not using cascading here because there is no scenario where we will
    //be trying to save the person object with a new class that does not already
    //exist in DB. So no need for cascading
    //Join column is used to refer to the foreign key
    @ManyToOne(fetch=FetchType.LAZY,optional = true)
    @JoinColumn(name="class_id",referencedColumnName = "classId",nullable = true)
    private NitinClass nitinClass;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "person_courses",
            joinColumns = {
                    @JoinColumn(name = "person_id", referencedColumnName = "person_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "course_id", referencedColumnName = "courseId")})
    private Set<Courses> courses;
}
