package hkmu.comps380f.controller;

import hkmu.comps380f.dao.UserManagementService;
import hkmu.comps380f.dao.UserService;
import hkmu.comps380f.exception.PhotoNotFound;
import hkmu.comps380f.model.Photo;
import hkmu.comps380f.model.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserManagementController {
    @Resource
    UserManagementService umService;
    @Resource
    UserService uService;
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
    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("addUser", "user", new Form());
    }
    @PostMapping("/create")
    public String create(Form form) throws IOException {
        umService.createUser(form.getUsername(), form.getPassword(), form.getRoles(), form.getEmail(), form.getTel(), form.getDescription());
        return "redirect:/user/list";
    }
    @GetMapping({"", "/", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("ticketUsers", umService.getUsers());
        return "listUser";
    }
    @GetMapping("/delete/{username}")
    public String delete(@PathVariable("username") String username) {
        umService.delete(username);
        return "redirect:/user/list";
    }
    @GetMapping("/edit/{username}")
    public ModelAndView showEdit(@PathVariable("username") String username,
                                 Principal principal, HttpServletRequest request)
            throws PhotoNotFound {
        User user = uService.getUser(username);
        if (user == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(user.getUsername()))) {
            return new ModelAndView(new RedirectView("/photo/list", true));
        }
        ModelAndView modelAndView = new ModelAndView("editUser");
        modelAndView.addObject("user", user);
        Form userForm = new Form();
        userForm.setEmail(user.getEmail());
        userForm.setTel(user.getTel());
        userForm.setPassword(user.getPassword().substring(6));
        userForm.setDescription(user.getDescription());
        userForm.setRoles(user.getRoleArray());
        modelAndView.addObject("userForm", userForm);
        return modelAndView;
    }

    @PostMapping("/edit/{username}")
    public String edit(@PathVariable("username") String username, Form form,
                       Principal principal, HttpServletRequest request)
            throws IOException, PhotoNotFound {
        User user = uService.getUser(username);
        if (user == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(user.getUsername()))) {
            return "redirect:/photo/list";
        }
        umService.updateUser(username, form.getPassword(), form.getRoles(),
                form.getEmail(), form.getTel(), form.getDescription());
        return "redirect:/profile/" + username;
    }
}

