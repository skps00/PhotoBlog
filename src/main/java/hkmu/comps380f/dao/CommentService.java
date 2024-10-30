package hkmu.comps380f.dao;

import hkmu.comps380f.exception.PhotoNotFound;
import hkmu.comps380f.model.Comment;
import hkmu.comps380f.model.Photo;
import hkmu.comps380f.model.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    @Resource
    CommentRepository cRepo;
    @Resource
    UserRepository uRepo;
    @Resource
    PhotoRepository pRepo;

    @Transactional
    public void createComment(String username, UUID photoId, String content)
            throws PhotoNotFound {
        User user = uRepo.findById(username).orElse(null);
        if (user == null){
            throw new RuntimeException("User " + username + " not found.");
        }
        Photo photo = pRepo.findById(photoId).orElse(null);
        if (photo == null) {
            throw new PhotoNotFound(photoId);
        }
        Comment comment = new Comment(user, photo, content);
        Comment savedComment = cRepo.save(comment);
        user.getComments().add(savedComment);
        photo.getComments().add(savedComment);
    }

    @Transactional
    public List<Comment> findCommentByPhotoId(UUID photoId){
        return cRepo.findCommentByPhotoIdOrderByCreateTimeDesc(photoId);
    }

    @Transactional
    public List<Comment> findCommentByUserId(UUID photoId){
        return cRepo.findCommentByPhotoIdOrderByCreateTimeDesc(photoId);
    }

    @Transactional
    public void delete(UUID photoId, long commentId){
        Photo photo = pRepo.findById(photoId).orElse(null);
        if (photo == null) {
            throw new PhotoNotFound(photoId);
        }
        Comment comment = cRepo.findById(commentId).orElse(null);
        if (comment == null) {
            throw new RuntimeException("Comment ID " + commentId + " not found.");
        }
        User user = comment.getUser();
        photo.getComments().remove(comment);
        user.getComments().remove(comment);
        cRepo.delete(comment);
    }
}
