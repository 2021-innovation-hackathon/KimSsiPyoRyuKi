package com.hocket.modules.account.validator;

import com.hocket.modules.account.AccountRepository;
import com.hocket.modules.account.form.AccountForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class AccountFormValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(AccountForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountForm accountForm = (AccountForm) target;

        if(accountRepository.existsByNickname(accountForm.getNickname())){
            errors.rejectValue("nickname", "exists.nickname");
        }
        if(accountRepository.existsByEmail(accountForm.getEmail())){
            errors.rejectValue("email", "exists.email");
        }

        String patter = "[a-z가-힣0-9_]{2,20}$";

        if(!accountForm.getNickname().matches(patter)){
            errors.rejectValue("nickname", "wrong.nickname");

        }

    }
}
