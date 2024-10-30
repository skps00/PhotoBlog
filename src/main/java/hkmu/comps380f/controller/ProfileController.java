package hkmu.comps380f.controller;

import hkmu.comps380f.dao.PhotoService;
import hkmu.comps380f.dao.UserManagementService;
import hkmu.comps380f.dao.UserService;
import hkmu.comps380f.exception.PhotoNotFound;
import hkmu.comps380f.model.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/profile")
public class ProfileController {
    @Resource
    private UserService uService;
    @Resource
    private PhotoService pService;
    @Resource
    private UserManagementService umService;

    @GetMapping(value = {"", "/"})
    public String index(Principal principal) {
        try {
            return "redirect:/profile/" + principal.getName();
        } catch (Exception e){
            return "redirect:/";
        }
    }
    @GetMapping(value = {"/{username}"})
    public String list(ModelMap model, @PathVariable("username") String username) {
        model.addAttribute("user", uService.getUser(username));
        model.addAttribute("photos", pService.getPhotosByUser(username) );
        return "profile";
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

    @GetMapping("/edit/{username}")
    public ModelAndView showEdit(@PathVariable("username") String username,
                                 Principal principal, HttpServletRequest request)
            throws PhotoNotFound {
        if(request.isUserInRole("ROLE_ADMIN")){
            return new ModelAndView(new RedirectView("/user/edit/"+username, true));
        }
        User user = uService.getUser(username);
        if (user == null  || !principal.getName().equals(user.getUsername())) {
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
    public String edit(@PathVariable("username") String username, Form form, Principal principal)
            throws IOException, PhotoNotFound {
        User user = uService.getUser(username);
        if (user == null || !principal.getName().equals(user.getUsername())) {
            return "redirect:/photo/list";
        }
        umService.updateUser(username, form.getPassword(), null,
                form.getEmail(), form.getTel(), form.getDescription());
        return "redirect:/profile/" + username;
    }
}
