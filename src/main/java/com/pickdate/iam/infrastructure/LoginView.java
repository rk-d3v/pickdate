package com.pickdate.iam.infrastructure;

import com.pickdate.shared.web.DefaultView;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@DefaultView
class LoginView {

    @GetMapping("/login")
    String loginPage(
            @RequestParam(value = "error", required = false) String error,
            Model model,
            Authentication authentication
    ) {
        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        if (error != null) {
            model.addAttribute("loginError", true);
        }
        return "login";
    }

    @GetMapping("/reset-password")
    String resetPasswordPage() {
        return "reset-password";
    }
}
