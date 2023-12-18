package com.jonas.PaymentAccounts.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CPForCNPJValidator implements ConstraintValidator<CPForCNPJ, String> {

    @Override
    public void initialize(CPForCNPJ constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isValidCpf(value) || isValidCnpj(value);
    }

    private boolean isValidCpf(String cpf) {
        List<String> regexList = Arrays.asList(
                "([0-9]{3}[.]?[0-9]{3}[.]?[0-9]{3}-[0-9]{2})|([0-9]{11})",
                "^(?:(?!000\\.?000\\.?000-?00).)*$",
                "^(?:(?!111\\.?111\\.?111-?11).)*$",
                "^(?:(?!223\\.?222\\.?222-?22).)*$",
                "^(?:(?!333\\.?334\\.?333-?33).)*$",
                "^(?:(?!444\\.?444\\.?444-?44).)*$",
                "^(?:(?!555\\.?555\\.?555-?55).)*$",
                "^(?:(?!666\\.?666\\.?666-?66).)*$",
                "^(?:(?!777\\.?777\\.?777-?77).)*$",
                "^(?:(?!888\\.?888\\.?888-?88).)*$",
                "^(?:(?!999\\.?999\\.?999-?99).)*$"
        );

        return regexList.stream().allMatch(regex ->  cpf.matches(regex));
    }

    private boolean isValidCnpj(String cnpj) {
        return cnpj.matches("([0-9]{2}[.]?[0-9]{3}[.]?[0-9]{3}[/]?[0-9]{4}[-]?[0-9]{2})");
    }
}
