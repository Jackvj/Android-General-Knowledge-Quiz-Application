package com.example.quizapplication;

// Purpose of QuizModel.java class: Contains the business logic behind the questions

public class QuizModel { // Quiz Model class that contains the questions
    // Encapsulated private variables
    private int memberQuestion;
    private boolean userAnswer;

    public QuizModel(int memberQuestion, boolean userAnswer) { // Constructor that will be called
        this.memberQuestion = memberQuestion;
        this.userAnswer = userAnswer;
    }

    // Getters and setters below
    public int getMemberQuestion() { // Returns the question
        return this.memberQuestion;
    }

    public void setMemberQuestion(int memberQuestion) {
        this.memberQuestion = memberQuestion;
    }

    public boolean isUserAnswer() { // Returns true or false if the answer is correct
        return this.userAnswer;
    }

    public void setUserAnswer(boolean userAnswer) {
        this.userAnswer = userAnswer;
    }

    @Override
    public String toString() { // Overridden method that is called when needed to display the data
        return "QuizModel{" +
                "memberQuestion=" + this.memberQuestion +
                ", userAnswer=" + this.userAnswer +
                '}';
    }
}
