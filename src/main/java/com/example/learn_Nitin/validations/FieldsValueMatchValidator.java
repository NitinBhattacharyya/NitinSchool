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
//        System.out.println("We are here");
//        System.out.println(field);
//        System.out.println(fieldMatch);
        Object fieldValue=new BeanWrapperImpl(value).getPropertyValue(field);
//        System.out.println(fieldValue.toString());
        Object fieldMatchValue=new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
//        System.out.println(fieldMatchValue.toString());
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
            if(fieldValue.toString().startsWith("$2a"))
            {
//                System.out.println("Password confirmed");
                return true;
            }
            else {
//                System.out.println("email confirmed:"+ fieldValue.equals(fieldMatchValue));
                return fieldValue.equals(fieldMatchValue);
            }
        }
        else {
            return fieldMatchValue==null;
        }
        //Disabling JPA validations before saving is a necessity due to the code we have written
        //Here is what I found
        /*
        So after a bit of debugging I found out that when we try to update the details of person through updateProfile,
        and JPA tries to perform validation before updating the database,our fieldsmatch validation basically fails at
        matching email and confirm email because there is no confirm email field to check it to in our profile form and
        our confirm email field in person class is essentially null after retrieving the person details from DB,
        because we do not save it in database. This is the reason why it becomes essential to disable JPA validation
        check because we are using same POJO classes as our DB entities and enabling JPA validation would fail at
        validating our email and confirm email when we want to update person details because of confirm email being null.
         */

    }


}
