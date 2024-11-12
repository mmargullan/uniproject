package com.margulan.uniproject.Repository;

import com.margulan.uniproject.Model.Message;
import com.margulan.uniproject.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {

    List<Message> findByUserId(int userId);

}
