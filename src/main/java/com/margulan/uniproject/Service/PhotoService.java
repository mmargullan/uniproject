package com.margulan.uniproject.Service;

import com.margulan.uniproject.Model.Photo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {

    void uploadPhoto(MultipartFile file) throws IOException;
    ResponseEntity<byte[]> getPhoto();
    void deletePhoto(String user_id);

}
