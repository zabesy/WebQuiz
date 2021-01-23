package com.example.adam.demo;

import com.example.adam.demo.models.WebQuiz;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebQuizRepository extends PagingAndSortingRepository<WebQuiz, Integer> {
}
