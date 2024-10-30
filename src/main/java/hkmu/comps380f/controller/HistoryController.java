package hkmu.comps380f.controller;

import hkmu.comps380f.dao.HistoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/history")
public class HistoryController {
    @Resource
    HistoryService hService;

    @GetMapping(value = {"/photo", "/photo/"})
    public String photo(ModelMap model) {
        model.addAttribute("username", "all");
        model.addAttribute("photos", hService.getPhotos());
        return "historyPhoto";
    }
    @GetMapping(value = {"/photo/{username}"})
    public String photoByUser(ModelMap model, @PathVariable("username") String username) {
        model.addAttribute("username", username);
        model.addAttribute("photos", hService.getPhotosByUser(username));
        return "historyPhoto";
    }
    @GetMapping(value = {"/comment/{username}"})
    public String commentByUser(ModelMap model, @PathVariable("username") String username) {
        model.addAttribute("username", username);
        model.addAttribute("comments", hService.getCommentsByUser(username));
        return "historyComment";
    }

}
