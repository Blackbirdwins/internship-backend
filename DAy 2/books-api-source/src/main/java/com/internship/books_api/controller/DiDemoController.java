package com.internship.books_api.controller;

import com.internship.books_api.di.ConstructorInjectedService;
import com.internship.books_api.di.FieldInjectedService;
import com.internship.books_api.di.SetterInjectedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/di-demo")
public class DiDemoController {

    private final ConstructorInjectedService constructorService;
    private final SetterInjectedService setterService;
    private final FieldInjectedService fieldService;

    public DiDemoController(ConstructorInjectedService constructorService,
                            SetterInjectedService setterService,
                            FieldInjectedService fieldService) {
        this.constructorService = constructorService;
        this.setterService = setterService;
        this.fieldService = fieldService;
    }

    @GetMapping("/constructor")
    public Map<String, String> constructor() {
        return Map.of("type", "constructor", "result", constructorService.doWork());
    }

    @GetMapping("/setter")
    public Map<String, String> setter() {
        return Map.of("type", "setter", "result", setterService.doWork());
    }

    @GetMapping("/field")
    public Map<String, String> field() {
        return Map.of("type", "field", "result", fieldService.doWork());
    }
}