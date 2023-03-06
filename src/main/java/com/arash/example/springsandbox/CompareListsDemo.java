package com.arash.example.springsandbox;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

public class CompareListsDemo {

    public static void main(String[] args) {

        Foo foo = new Foo("foo");
        FooBar fooBar = new FooBar();
        BeanUtils.copyProperties(foo, fooBar);
        System.out.println(fooBar);

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private static class Foo {

        private String foo;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private static class FooBar {

        private String foo;
        private String bar;
    }
}
