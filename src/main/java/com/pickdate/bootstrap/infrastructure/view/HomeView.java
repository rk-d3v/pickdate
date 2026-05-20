package com.pickdate.bootstrap.infrastructure.view;

import com.pickdate.shared.web.DefaultView;
import org.springframework.web.bind.annotation.GetMapping;


@DefaultView
class HomeView {

    @GetMapping("/")
    String home() {
        return "home";
    }
}
