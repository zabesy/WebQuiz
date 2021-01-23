package com.example.adam.demo;

import com.example.adam.demo.models.Answer;
import com.example.adam.demo.models.AnswerResponse;
import com.example.adam.demo.models.WebQuiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
public class WebQuizService {

    @Autowired
    private WebQuizRepository quizRepository;

    public Page<WebQuiz> findAll(int pageNo){
        Pageable paging = PageRequest.of(pageNo, 10);
        Page<WebQuiz> pageResult = quizRepository.findAll(paging);

        if(pageResult.hasContent()){
            return pageResult;
        }else{
            return new PageImpl<WebQuiz>(new ArrayList<>());
        }
    }

    public Optional<WebQuiz> findById(int id){
        return quizRepository.findById(id);
    }

    public WebQuiz create(WebQuiz quiz){
        return quizRepository.save(quiz);
    }

    public void delete(WebQuiz quiz){
        quizRepository.delete(quiz);
    }

    public Optional<AnswerResponse> getResult(int id, Answer answer){
        return findById(id).map(webQuiz -> {
                Arrays.sort(answer.getAnswer());
                Arrays.sort(webQuiz.getAnswer());
                if (Arrays.equals(answer.getAnswer(),webQuiz.getAnswer())){
                    return new AnswerResponse(true);
                }else{
                    return new AnswerResponse(false);
                }
            });
    }


}
