package com.margulan.uniproject.Controller;

import com.margulan.uniproject.Model.Task;
import com.margulan.uniproject.Repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TasksRepository tasksRepository;

    @GetMapping("/searchTasks")
    public List<Task> searchTasks(
            @RequestParam int user_id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam int page
    ) {

        Pageable pageable = PageRequest.of(page, 10);

        return tasksRepository.searchTasksByUserId(user_id, title, status, priority, pageable);
    }

}
