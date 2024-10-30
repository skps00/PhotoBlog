package hkmu.comps380f.controller;

import hkmu.comps380f.dao.CommentService;
import hkmu.comps380f.dao.PhotoService;
import hkmu.comps380f.exception.PhotoNotFound;
import hkmu.comps380f.model.Comment;
import hkmu.comps380f.model.Photo;
import hkmu.comps380f.view.DownloadingView;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/photo")
public class PhotoController {

    @Resource
    private PhotoService pService;

    @Resource
    private CommentService cService;

    @GetMapping(value = {"", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("photoList", pService.getPhotos());
        return "listPhoto";
    }

    @GetMapping("/upload")
    public ModelAndView upload() {
        return new ModelAndView("addPhoto", "photoForm", new Form());
    }

    @PostMapping("/upload")
    public View upload(Form form, Principal principal) throws IOException {
        String customerName = principal.getName();
        pService.createPhoto(customerName, form.getDescription(), form.getPhotos());
        return new RedirectView("/profile/" + customerName, true);
    }
    public static class Form {
        private String description;
        private List<MultipartFile> photos;
        // Getters and Setters of customerName, subject, body, attachments
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<MultipartFile> getPhotos() {
            return photos;
        }

        public void setPhotos(List<MultipartFile> photos) {
            this.photos = photos;
        }
    }

    @GetMapping("/view/{photoId}")
    public ModelAndView view(@PathVariable("photoId") UUID photoId,
                       ModelMap model)
            throws PhotoNotFound {
        Photo photo = pService.getPhoto(photoId);
        List<Comment> comments = cService.findCommentByPhotoId(photoId);
        model.addAttribute("photo", photo);
        model.addAttribute("comments", comments);
        return new ModelAndView("viewPhoto", "commentForm", new CommentForm());
    }

    @GetMapping("/download/{photoId}")
    public View download(@PathVariable("photoId") UUID photoId)
            throws PhotoNotFound{
        Photo photo = pService.getPhoto(photoId);
        return new DownloadingView(photo.getName(),
                photo.getMimeContentType(), photo.getContents());
    }

    @GetMapping("/delete/{photoId}")
    public String deletePhoto(@PathVariable("photoId") UUID photoId)
            throws PhotoNotFound {
        pService.delete(photoId);
        return "redirect:/photo/list";
    }

    @ExceptionHandler({PhotoNotFound.class})
    public ModelAndView error(Exception e) {
        return new ModelAndView("error", "message", e.getMessage());
    }

    public static class CommentForm {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    @PostMapping("/comment/new/{photoId}")
    public View upload(CommentForm form, Principal principal, @PathVariable("photoId") UUID photoId) throws IOException {
        String customerName = principal.getName();
        cService.createComment(customerName, photoId, form.getContent());
        return new RedirectView("/photo/view/" + photoId, true);
    }

    @GetMapping("/comment/delete/{photoId}/{commentId}")
    public String deleteComment(@PathVariable("photoId") UUID photoId, @PathVariable("commentId") long commentId)
            throws PhotoNotFound {
        cService.delete(photoId, commentId);
        return "redirect:/photo/view/"+photoId;
    }
}

