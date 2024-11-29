package com.margulan.uniproject.Service.Impl;

import com.margulan.uniproject.Exception.UserNotFoundException;
import com.margulan.uniproject.Model.Photo;
import com.margulan.uniproject.Model.User;
import com.margulan.uniproject.Repository.PhotoRepository;
import com.margulan.uniproject.Repository.UsersRepository;
import com.margulan.uniproject.Service.PhotoService;
import com.margulan.uniproject.Service.UsersService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;


@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    UsersService usersService;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void uploadPhoto(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new FileNotFoundException();
        }

        try {
            User user = usersRepository.findByEmail(usersService.getLoggedUser().getName()).orElseThrow(
                    () -> new UserNotFoundException("User not found"));
            Photo existingPhoto = photoRepository.findByUserId(user.getId());
            if (existingPhoto != null) {
                photoRepository.delete(existingPhoto);
            }
            Photo photo = new Photo(user, file.getOriginalFilename(), file.getBytes());
            photoRepository.save(photo);
        } catch (Exception e) {
            throw new IOException();
        }
    }

    @Override
    public ResponseEntity<byte[]> getPhoto() {
        User user = usersRepository.findByEmail(usersService.getLoggedUser().getName()).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        Photo photo = photoRepository.findByUserId(user.getId());

        if (photo == null || photo.getPhoto() == null) {
            return ResponseEntity.notFound().build();
        }

        // Set content type (e.g., "image/jpeg" or "image/png")
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG); // Or MediaType.IMAGE_PNG if needed

        return new ResponseEntity<>(photo.getPhoto(), headers, HttpStatus.OK);
    }

    @Override
    public void deletePhoto(String user_id) {
        Photo photo = photoRepository.findByUserId(Integer.parseInt(user_id));
        photoRepository.delete(photo);
    }

}
