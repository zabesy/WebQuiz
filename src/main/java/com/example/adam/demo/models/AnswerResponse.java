package com.example.adam.demo.models;

public class AnswerResponse {

    private final boolean success;
    private final String feedback;

    public AnswerResponse(boolean success) {
        this.success = success;
        this.feedback = success ? "Congratulations, you're right!" : "Wrong answer! Please, try again.";
    }


    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }


}
