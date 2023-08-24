package com.example.learn_Nitin.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="holidays")
public class Holiday extends BaseEntity{
    @Id
    private String day;
    private String reason;
    //will convert enum to string at runtime and store into database
    @Enumerated(EnumType.STRING)
    private Type type;
    public enum Type {
        FESTIVAL, FEDERAL
    }
}
