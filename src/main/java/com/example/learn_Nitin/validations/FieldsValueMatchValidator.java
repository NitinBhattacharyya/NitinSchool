package com.example.learn_Nitin.validations;

import com.example.learn_Nitin.annotation.FieldsValueMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch,Object> {
    private String field;
    private String fieldMatch;
    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field= constraintAnnotation.field();
        this.fieldMatch= constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldValue=new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue=new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
        //The below code gives problem after we use password encoder
        //before saving the person details into our database we encrypt the password and
        //set the password field in person class to encrypted password
        //When spring mvc does the validations,both the password and confirm password are in plain text
        //hence the below code returns true if both strings match
        //Spring JPA however just before saving the person details to database does this validation again
        //because we are using the same pojo class as entity. This time however,the password is in encrypted
        //form whereas confirm password is not. So the below code fails and we get password do not match error
//        if(fieldValue!=null)
//        {
//            return fieldValue.equals(fieldMatchValue);
//        }
//        else {
//            return fieldMatchValue==null;
//        }
        //To solve the above issue,since if the password is getting encrypted,validations check by
        //Spring MVC already says that the password and confirm password match,we simply return true
        //if the field start with "$2a",since that is how Bcrypt encoded strings start
        if(fieldValue!=null)
        {
            if(fieldValue.toString().startsWith("$2a"))return true;
            else {
                return fieldValue.equals(fieldMatchValue);
            }
        }
        else {
            return fieldMatchValue==null;
        }

    }


}
