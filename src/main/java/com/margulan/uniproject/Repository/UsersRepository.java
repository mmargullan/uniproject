package com.margulan.uniproject.Repository;


import com.margulan.uniproject.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, String> {

    User findByUsername(String username);
}
