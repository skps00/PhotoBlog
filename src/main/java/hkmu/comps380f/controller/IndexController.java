package hkmu.comps380f.controller;

import hkmu.comps380f.dao.UserManagementService;
import hkmu.comps380f.dao.UserService;
import hkmu.comps380f.model.User;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;

@Controller
public class IndexController {
    @Resource
    UserManagementService umService;

    @GetMapping("/")
    public String index() {
        return "redirect:/photo/list";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/register")
    public ModelAndView register(Principal principal, HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USER")) {
            return new ModelAndView(new RedirectView("/photo/list", true));
        }
        return new ModelAndView("addUser", "user", new UserManagementController.Form());
    }
    @PostMapping("/register")
    public String register(Form form, HttpServletRequest request) throws IOException {
        if (request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_USER")) {
            return"/photo/list";
        }
        umService.createUser(form.getUsername(), form.getPassword(), new String[]{"ROLE_USER"}, form.getEmail(), form.getTel(), form.getDescription());
        try {
            request.login(form.getUsername(), form.getPassword());
        } catch (ServletException e) {

        }
        return "redirect:/";
    }
    public static class Form {
        private String username;  private String password;  private String[] roles;
        private String email; private String tel; private String description;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}


