package com.example.adam.demo;

import com.example.adam.demo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class WebQuizHandler {

    @Autowired
    UserService userService;

    @Autowired
    WebQuizService quizService;


    public WebQuizHandler() {
    }

    @DeleteMapping(path = "/api/quizzes/{id}")
    public ResponseEntity<?> deleteQuiz(@AuthenticationPrincipal User user, @PathVariable int id){
        return quizService.findById(id)
                .map(quiz -> {
                    if(quiz.getUser().equals(user)){
                        quizService.delete(quiz);
                        return ResponseEntity.noContent().build();
                    }
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping(path = "/api/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user){
        if(userService.getByEmail(user.getEmail()).isPresent()){
            return ResponseEntity.badRequest().build();
        }

        userService.create(user);
        return ResponseEntity.ok().build();

    }

    @PostMapping(path = "/api/quizzes")
    public WebQuiz createQuiz(@AuthenticationPrincipal User user, @Valid @RequestBody WebQuiz webQ){
        webQ.setUser(user);
        return quizService.create(webQ);
    }

    @GetMapping(path = "/api/quizzes",produces = "application/json")
    public ResponseEntity<Page<WebQuiz>> getAllQuizzes(@RequestParam(defaultValue = "0") int page) {

        Page<WebQuiz> list = quizService.findAll(page);
        return new ResponseEntity<Page<WebQuiz>>(list, new HttpHeaders(), HttpStatus.OK);
    }


    @GetMapping(path = "/api/quizzes/completed")
    public ResponseEntity<Page<Completed>> getCompletedQuizzes(
            @RequestParam(defaultValue = "0") int page,
            @AuthenticationPrincipal User user){
        Page<Completed> list = userService.findAllCompleted(page, user);
        return new ResponseEntity<Page<Completed>>(list, new HttpHeaders(),HttpStatus.OK);
    }



    @GetMapping(path = "/api/quizzes/{id}")
    public WebQuiz getQuizByID(@PathVariable int id){
        return quizService.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));
    }


    @PostMapping(path = "/api/quizzes/{id}/solve")
    public ResponseEntity<AnswerResponse> getAnswer(
            @AuthenticationPrincipal User user,
            @PathVariable int id,
            @RequestBody Answer guess) {
        boolean isCorrect = quizService.getResult(id,guess).get().isSuccess();
        if(isCorrect){
            userService.setCompleted(user,id);
        }
        return ResponseEntity.of(quizService.getResult(id,guess));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    class QuizNotFoundException extends RuntimeException{
        public QuizNotFoundException(int id){
            super("Could not find a quiz with id: " + id);
        }
    }
}