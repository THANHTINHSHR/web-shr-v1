package cdw.cdwproject.model.User;


import cdw.cdwproject.repository.UserRespository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    // common-validator library.
    private final EmailValidator emailValidator = EmailValidator.getInstance();


    @Autowired
    private UserRespository userRespository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == UserForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserForm form = (UserForm) target;

        if (form.getName().length() <3 ) {
            System.out.println("name too short");
            errors.rejectValue("name", "", "Name at least 3 character");
//            return;
        }

        if (form.getName().length() >=20 ) {
            System.out.println("name out of range");
            errors.rejectValue("name", "", "Name out of range");
//            return;
        }

        if (!emailValidator.isValid(form.getEmail())) {
            System.out.println("email valid");
            errors.rejectValue("email", "", "Email is not valid");
//            return;
        }

        User userAccount = userRespository.getUserByEmail(form.getEmail());

        if (userAccount != null) {
            System.out.println("double email");
            errors.rejectValue("email", "", "Email has been exists");
            return;

        }

    }

}
