package com.example.learn_Nitin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@Data
@Entity
@Table(name="class")
public class NitinClass extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private int classId;

    @NotBlank(message = "Name must not be blank")
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

    //cascading is persist because if we try to save the class object with
    //set or list of person objects,we want the person objects to be saved as well
    @OneToMany(mappedBy = "nitinClass", fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST,targetEntity = Person.class)
    private Set<Person> persons;
}
