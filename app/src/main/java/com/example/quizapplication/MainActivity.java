package com.example.quizapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

// Purpose of MainActivity.java: Contains the code for the quiz application UI.
// Author: Sabin Constantin Lungu
// Date of Last Modified: 19 December 2019

public class MainActivity extends AppCompatActivity {

    private TextView questionView1; // Text view component for the question
    private ProgressBar quizProgressBar; // Progress bar component
    private TextView scoreTextView; // Text viw for the score

    private Button trueButton; // True Button
    private Button falseButton; // False Button

    private int score; // Score of the user
    private int quizQuestion; // The question to choose
    private int questionIndex; // The index of the question

    private QuizModel[] questionBank = new QuizModel[] { // Round 1 question bank

            // INSTANTIATE EASY QUESTIONS BY DEFAULT (>=0)
            new QuizModel(R.string.Q1, true),
            new QuizModel(R.string.Q2, true),
            new QuizModel(R.string.Q3, false),
            new QuizModel(R.string.Q4, true),
            new QuizModel(R.string.Q5, false),

            // INSTANTIATE MEDIUM QUESTIONS IF THE SCORE OF THE USER IS >=5
            new QuizModel(R.string.Q6, false),
            new QuizModel(R.string.Q7, false),
            new QuizModel(R.string.Q8, true),
            new QuizModel(R.string.Q9, true),
            new QuizModel(R.string.Q10, true),

            // INSTANTIATE HARD QUESTIONS - IF THE SCORE OF THE USER IS >=10
            new QuizModel(R.string.Q11, true),
            new QuizModel(R.string.Q12, true),
            new QuizModel(R.string.Q13, false),
            new QuizModel(R.string.Q14, false),
            new QuizModel(R.string.Q15, true)
    };

    final int USER_PROGRESS = (int)Math.ceil(100.0 / questionBank.length); // User progress bar formula

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise the UI components
        this.questionView1 = findViewById(R.id.questionTxt); // Find the question view
        this.quizProgressBar = findViewById(R.id.quizBar);
        this.scoreTextView = findViewById(R.id.scoreTxt);

        QuizModel firstQuestion = questionBank[questionIndex];
        quizQuestion = firstQuestion.getMemberQuestion();
        questionView1.setText(quizQuestion);

         this.trueButton = findViewById(R.id.trueBtn); // Create an object for the first button
         this.falseButton = findViewById(R.id.falseBtn); // Create object for the false button

        // Initialise the prizes instance to be won if the score is 10

        trueButton.setOnClickListener(new View.OnClickListener() { // Add an action listener to the true button

            public void onClick(View view) {
                if(view.getId() == R.id.trueBtn) { // If the true button is clicked

                  verifyUserAnswer(true); // Verify the user answer
                  changeQuestionBtnClick(); // After verifying the answer, change the question.
                }
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() { // Create an on click listener for the false button

            public void onClick(View view) {
                
                if(view.getId() == R.id.falseBtn) { // If the false button is clicked
                verifyUserAnswer(false); // 1. First Verify the user answer
                changeQuestionBtnClick(); // 2. Change the question
                }
            }
        });
    }

    private void changeQuestionBtnClick() { // Method to change the question
        // Local variables that stores the state of the application
        String alertTitle = "Quiz Finished";
        String mediumTitle = "Medium Round";

        String hardTitle = "Hard Round";
        String gameOverText = "Game Over";
        boolean gameOver;

        this.questionIndex = (questionIndex +1) % 15;

        if(score == 5) { // END OF ROUND EASY ROUND 1
            AlertDialog.Builder mediumRoundAlert = new AlertDialog.Builder(this);
            mediumRoundAlert.setCancelable(true);
            mediumRoundAlert.setTitle(mediumTitle);
            mediumRoundAlert.setMessage("Your score is 5. You may proceed to the Medium Round" + this.score); // Show message to the user saying to proceed to the medium round if the score is 5 in the easy round

            mediumRoundAlert.setPositiveButton("Proceed To Medium Round", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            mediumRoundAlert.show(); // Show the dialogue
        }

        if(score == 10) { // If the user score is 10

            AlertDialog.Builder hardRoundAlert = new AlertDialog.Builder(this); // Hard Alert Dialogue

            hardRoundAlert.setCancelable(true);
            hardRoundAlert.setTitle(hardTitle);
            hardRoundAlert.setMessage("Your score is 10. Hard Round Next" + this.score);

            hardRoundAlert.setPositiveButton("Proceed to Hard Round", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss(); // Dismiss the dialogue
                }
            });

            hardRoundAlert.show();
        }

        if(score == 15 || score <=0) { // If the score is 15 or the score is less than 0 then it's game over
            Toast.makeText(getApplicationContext(), gameOverText, Toast.LENGTH_SHORT).show();
            gameOver = true; // Set flag to true

            Log.i("Game over status", "status" + gameOver); // Log the status to the console for debugging sakes
        }

        if(questionIndex == 0) { // If the question index is 0 after all the questions are complete
            // Show Alert Dialogue

            AlertDialog.Builder quizAlert = new AlertDialog.Builder(this);
            quizAlert.setCancelable(false); // Won't allow the user to click outside the boundaries of the alert dialogue, hence it won't close
            quizAlert.setTitle(alertTitle);
            quizAlert.setMessage("Your Final Score Is : " + score);


            quizAlert.setPositiveButton("Finish The Quiz", new DialogInterface.OnClickListener() {


                public void onClick(DialogInterface dialog, int which) {
                    finish(); // Close the application
                }
            });

            quizAlert.show(); // Show the alert
        }

        this.quizQuestion = questionBank[questionIndex].getMemberQuestion();

        questionView1.setText(this.quizQuestion);
        this.quizProgressBar.incrementProgressBy(USER_PROGRESS); // Increment the progress bar by a value of 10.
        this.scoreTextView.setText("Score : " + score);
    }

    private void verifyUserAnswer(boolean userGuess) {
       boolean currentQuestionAnswer = questionBank[questionIndex].isUserAnswer();

       if(currentQuestionAnswer == userGuess) { // If the correct question is guessed by the user

           Toast.makeText(getApplicationContext(), R.string.correct_toast_message, Toast.LENGTH_LONG).show(); // Show the toast message if the answer is correct
           this.score++;
       }

       else {
          Toast.makeText(getApplicationContext(), R.string.incorrect_text, Toast.LENGTH_LONG).show();
          this.score--; // Decrement the score if the answer is wrong
       }
    }
}
