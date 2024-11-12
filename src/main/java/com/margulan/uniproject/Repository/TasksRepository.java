package com.margulan.uniproject.Repository;

import com.margulan.uniproject.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Task, String> {

    List<Task> findByUserId(int userId);

    Task findByTitle(String title);

    void deleteByTitle(String title);

//    @Query("select t from Task t where t.user.id = :userId")
//    List<Task> findTasksByUserId(int userId, Pageable pageable);

    @Query("select t from Task t where t.user.id = :userId and " +
            "(:title is null or t.title like %:title%) and " +
            "(:status is null or t.status like %:status%) and " +
            "(:priority is null or t.priority like %:priority%)")
    List<Task> searchTasksByUserId(int userId, String title, String status, String priority, Pageable pageable);

}
