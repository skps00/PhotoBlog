package hkmu.comps380f.dao;

import hkmu.comps380f.exception.PhotoNotFound;
import hkmu.comps380f.model.Photo;
import hkmu.comps380f.model.User;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserManagementService {
    @Resource
    private UserRepository uRepo;

    @Transactional
    public void createUser(String username, String password, String[] roles,
                                 String email, String tel, String description) {
        User user = new User(username, password, roles);
        user.setEmail(email);
        user.setTel(tel);
        user.setDescription(description);
        uRepo.save(user);
    }
    @Transactional
    public List<User> getUsers() {
        return uRepo.findAll();
    }

    @Transactional
    public void delete(String username) {
        User user = uRepo.findById(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        uRepo.delete(user);
    }

    @Transactional
    public void updateUser(String username, String password, String[] roles,
                           String email, String tel, String description)
            throws IOException, PhotoNotFound {
        User user = uRepo.findById(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        user.setPassword(password);
        user.setDescription(description);
        user.setTel(tel);
        user.setEmail(email);
        if(roles != null)
            user.setRoles(roles);
        uRepo.save(user);
    }
}

