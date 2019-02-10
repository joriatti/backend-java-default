package com.backend.service;

import com.backend.email.SignupEmailSender;
import com.backend.exceptions.BadRequestException;
import com.backend.exceptions.BaseException;
import com.backend.exceptions.InternalServerErrorException;
import com.backend.model.Signup;
import com.backend.util.IdGenerator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class SignupService extends AbstractPersistence<Signup, String> {

    @Autowired
    private SignupEmailSender signupEmailSender;

    @Autowired
    private UserService userService;

    @Autowired
    private IdGenerator idGenerator;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Signup autoRegister(String email, String firstName, String lastName,
                               String password, String retypePassword)
            throws BaseException {

        validateEmail(email);
        validateFirstName(firstName);
        validatePassword(password, retypePassword);
        validateUserAlreadyExist(email);

        Signup signup = new Signup();
        signup.setId(idGenerator.nextId());
        signup.setEmail(email);
        signup.setFirstName(firstName);
        signup.setLastName(lastName);
        signup.setPassword(new BCryptPasswordEncoder().encode(password));
        signup.setRetypePassword(new BCryptPasswordEncoder().encode(retypePassword));
        signup.setRegisterDate(LocalDateTime.now());

        try {
            byte[] bytesOfMessage = signup.getRegisterDate().toString().getBytes("UTF-8");
            signup.setSignupHash(java.security.MessageDigest.getInstance("MD5").digest(bytesOfMessage).toString());
        } catch (Exception e) {
            throw new InternalServerErrorException("BACKEND_GENERIC_ERROR_MESSAGE");
        }

        this.save(signup);

        signupEmailSender.sendEmail(signup);

        return signup;
    }

    private void validateUserAlreadyExist(String email) throws BadRequestException {
        if (userService.findByEmail(email) != null) {
            throw new BadRequestException("USER_ALREADY_EXIST");
        }
    }

    private void validatePassword(String password, String retypePassword) throws BadRequestException {
        if (StringUtils.isEmpty(password)) {
            throw new BadRequestException("PASSWORD_REQUIRED");
        }

        if (!password.equals(retypePassword)) {
            throw new BadRequestException("PASSWORD_NOT_EQUALS_RETYPE_PASSWORD");
        }
    }

    private void validateFirstName(String fullName) throws BadRequestException {
        if (StringUtils.isEmpty(fullName)) {
            throw new BadRequestException("NAME_REQUIRED");
        }
    }

    private void validateEmail(String email) throws BadRequestException {
        if (StringUtils.isEmpty(email)) {
            throw new BadRequestException("EMAIL_REQUIRED");
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            throw new BadRequestException("INVALID_EMAIL");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void confirmUser(String email, String signupHash) throws BadRequestException {
        Signup signup = this.findByUniqueProperty("email", email);
        if (signup == null) {
            throw new BadRequestException("USER_NOT_FOUND");
        }
        if (!signup.getSignupHash().equals(signupHash)) {
            throw new BadRequestException("INVALID_HASH");
        }

        userService.createUserFromSignup(signup);
    }
}
