package com.hocket.modules.account.validator;

import com.hocket.modules.account.AccountRepository;
import com.hocket.modules.account.form.LoginForm;
import com.hocket.modules.account.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class SignUpFormValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpForm signUpForm = (SignUpForm) target;

        if(accountRepository.existsByNickname(signUpForm.getNickname())){
            errors.rejectValue("nickname", "exists.nickname");
        }

        String patter = "[a-z가-힣0-9_]{2,20}$";
        if(!signUpForm.getNickname().matches(patter)){
            errors.rejectValue("nickname", "wrong.nickname");

        }

    }
}
