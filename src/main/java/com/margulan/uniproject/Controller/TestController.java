package com.margulan.uniproject.Controller;

import java.util.*;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public List<Integer> test(@RequestParam int[] array) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        System.out.println(Collections.frequency(list, 5));
        return list;
    }

}
