package com.margulan.uniproject.Service.Impl;

import com.margulan.uniproject.Model.Photo;
import com.margulan.uniproject.Model.User;
import com.margulan.uniproject.Repository.PhotoRepository;
import com.margulan.uniproject.Repository.UsersRepository;
import com.margulan.uniproject.Service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public void uploadPhoto(String user_id, MultipartFile file) throws IOException {
        User user = usersRepository.findById(user_id).isPresent() ? usersRepository.findById(user_id).get() : null;
        Photo photo = photoRepository.findByUserId(Integer.parseInt(user_id));
        if(photo == null) {
            photo = new Photo();
            photo.setUser(user);
        }

        photo.setPhoto(file.getBytes());
        photoRepository.save(photo);

    }

    @Override
    public Photo getPhoto(String user_id) {
        return photoRepository.findByUserId(Integer.parseInt(user_id));
    }

    @Override
    public void deletePhoto(String user_id) {
        Photo photo = photoRepository.findByUserId(Integer.parseInt(user_id));
        photoRepository.delete(photo);
    }

}
