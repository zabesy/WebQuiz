package com.example.adam.demo;


import com.example.adam.demo.models.Completed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedRepository extends JpaRepository<Completed, Integer> {

    @Query("SELECT c FROM Completed c where c.user.email = :email order by c.completedAt desc")
    Page<Completed> findAllByCompletion(String email, Pageable pageable);
}
