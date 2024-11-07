package com.margulan.uniproject.Repository;

import com.margulan.uniproject.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Task, String> {

    List<Task> findByUserId(int userId);

    Task findByTitle(String title);

    void deleteByTitle(String title);
}
