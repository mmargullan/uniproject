package com.margulan.uniproject.Controller;

import com.margulan.uniproject.Service.PhotoService;
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

    @PostMapping
    public String uploadPhoto(@PathVariable String user_id, @RequestParam(name = "photo") MultipartFile photo, RedirectAttributes redirectAttributes) throws IOException {
        photoService.uploadPhoto(user_id, photo);
        return "redirect:/personalPage";
    }

    @GetMapping("/getPhoto")
    public String getPhoto(@PathVariable(name = "photo_id") String photo_id, Model model) throws IOException {
        model.addAttribute("photo", photoService.getPhoto(photo_id));
        return "redirect:/personalPage";
    }

}
