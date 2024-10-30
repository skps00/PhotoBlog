package hkmu.comps380f.dao;

import hkmu.comps380f.model.Comment;
import hkmu.comps380f.model.Photo;
import hkmu.comps380f.model.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HistoryService {

    @Resource
    CommentRepository cRepo;
    @Resource
    UserRepository uRepo;
    @Resource
    PhotoRepository pRepo;

    @Transactional
    public List<Photo> getPhotos() {
        return pRepo.findAllByOrderByCreateTimeDesc();
    }
    @Transactional
    public List<Photo> getPhotosByUser(String username){
        User user = uRepo.findById(username).orElse(null);
        if (user == null){
            throw new RuntimeException("User " + username + " not found.");
        }
        return pRepo.findPhotosByCustomerNameOrderByCreateTimeDesc(username);
    }
    @Transactional
    public List<Comment> getCommentsByUser(String username){
        User user = uRepo.findById(username).orElse(null);
        if (user == null){
            throw new RuntimeException("User " + username + " not found.");
        }
        return cRepo.findCommentByUsernameOrderByCreateTimeDesc(username);
    }
}
