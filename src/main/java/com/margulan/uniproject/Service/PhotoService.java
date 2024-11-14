package com.margulan.uniproject.Service;

import com.margulan.uniproject.Model.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {

    void uploadPhoto(String user_id, MultipartFile file) throws IOException;
    Photo getPhoto(String user_id);
    void deletePhoto(String user_id);

}
