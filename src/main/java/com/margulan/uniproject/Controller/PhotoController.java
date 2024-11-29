package com.margulan.uniproject.Controller;

import com.margulan.uniproject.Model.Photo;
import com.margulan.uniproject.Service.PhotoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/photo")
public class PhotoController {

    private final PhotoService photoService;

    PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/uploadPhoto")
    public String uploadPhoto(@RequestParam("photo") MultipartFile file) throws IOException {
        photoService.uploadPhoto(file);
        return "user_page";
    }

    @GetMapping("/getPhoto")
    public ResponseEntity<byte[]> getPhoto() {
        return photoService.getPhoto();
    }


}
