package com.arash.example.springsandbox.service;

import org.apache.commons.lang3.LocaleUtils;

import java.util.Arrays;
import java.util.Locale;

public class LocaleFoo {

    public static void main(String[] args) {
        System.out.println(Arrays.asList(Locale.getISOCountries()));
    }
}
