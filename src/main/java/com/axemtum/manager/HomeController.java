/*
 * Copyright Axemtum 2024
 */
package com.axemtum.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The home controller for the application
 *
 * @author Magnus Rossander
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

}
