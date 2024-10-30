package hkmu.comps380f.dao;

import hkmu.comps380f.exception.PhotoNotFound;
import hkmu.comps380f.model.Photo;
import hkmu.comps380f.model.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class PhotoService {
    @Resource
    private PhotoRepository pRepo;
    @Resource
    private UserRepository uRepo;

    @Transactional
    public List<Photo> getPhotos() {
        return pRepo.findAll();
    }
    @Transactional
    public List<Photo> getPhotosByUser(String username){
        return pRepo.findPhotosByCustomerName(username);
    }
    @Transactional
    public Photo getPhoto(UUID id)
            throws PhotoNotFound {
        Photo photo = pRepo.findById(id).orElse(null);
        if (photo == null) {
            throw new PhotoNotFound(id);
        }
        return photo;
    }

    @Transactional
    public void delete(UUID id) throws PhotoNotFound {
        Photo deletedPhoto = pRepo.findById(id).orElse(null);
        if (deletedPhoto == null) {
            throw new PhotoNotFound(id);
        }
        deletedPhoto.getCustomer().getPhotos().remove(deletedPhoto);
        pRepo.delete(deletedPhoto);
    }

    @Transactional
    public void createPhoto(String customerName, String description, List<MultipartFile> attachments)
            throws IOException {
        User customer = uRepo.findById(customerName).orElse(null);
        if (customer == null){
            throw new RuntimeException("User " + customerName + " not found.");
        }
        for (MultipartFile filePart : attachments) {
            Photo photo = new Photo();
            photo.setCustomer(customer);
            photo.setDescription(description);
            photo.setName(filePart.getOriginalFilename());
            photo.setMimeContentType(filePart.getContentType());
            photo.setContents(filePart.getBytes());
            if (photo.getName() != null && photo.getName().length() > 0
                    && photo.getContents() != null
                    && photo.getContents().length > 0) {
                Photo savedPhoto = pRepo.save(photo);
                customer.getPhotos().add(savedPhoto);
            }
        }
    }
}

