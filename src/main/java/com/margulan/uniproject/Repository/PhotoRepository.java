package com.margulan.uniproject.Repository;

import com.margulan.uniproject.Model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    Photo findByUserId(int userId);
}
