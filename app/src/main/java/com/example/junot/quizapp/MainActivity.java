package com.example.junot.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private QuizQuestion mQuizQuestion = new QuizQuestion();

    private TextView mScoreView;
    private TextView mQuestionView;
    private Button mButtonOption1;
    private Button mButtonOption2;
    private Button mButtonOption3;
    private Button mButtonOption4;
    private LinearLayout linearLayoutQuestion;
    private LinearLayout linearLayoutScore;
    private TextView textViewScore;
    private TextView textViewGreetings;
    private Button btnBackToLogin;

    private String mAnswer;
    private int mScore = 0;
    private int mQuestionNumber = 0;
    private boolean isFirst = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScoreView = (TextView) findViewById(R.id.Score);
        mQuestionView = (TextView) findViewById(R.id.question);
        mButtonOption1 = (Button) findViewById(R.id.option1);
        mButtonOption2 = (Button) findViewById(R.id.option2);
        mButtonOption3 = (Button) findViewById(R.id.option3);
        mButtonOption4 = (Button) findViewById(R.id.option4);
        btnBackToLogin = findViewById(R.id.btnBackToLogin);
        textViewGreetings = findViewById(R.id.txtViewGreeting);
        textViewScore = findViewById(R.id.txtViewScore);
        linearLayoutQuestion = findViewById(R.id.linearLayoutQuestion);
        linearLayoutScore = findViewById(R.id.linearLayoutScore);
        updateQuestion();

        linearLayoutScore.setVisibility(View.GONE);
        linearLayoutQuestion.setVisibility(View.VISIBLE);
        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //the beginning of button listener button1
        mButtonOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cara kerjanya
                if (mButtonOption1.getText() == mAnswer) {
                    mScore = mScore + 1;
                    updateScore(mScore);
                    updateQuestion();

                    //toast kl benar toast kl salah
                    Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
            }
        });

        //the end of button listener button1

        mButtonOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cara kerjanya
                if (mButtonOption2.getText() == mAnswer) {
                    mScore = mScore + 1;
                    updateScore(mScore);
                    updateQuestion();

                    //toast kl benar toast kl salah
                    Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
            }
        });

        mButtonOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cara kerjanya
                if (mButtonOption3.getText() == mAnswer) {
                    mScore = mScore + 1;
                    updateScore(mScore);
                    updateQuestion();

                    //toast kl benar toast kl salah
                    Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
            }
        });

        mButtonOption4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cara kerjanya
                if (mButtonOption4.getText() == mAnswer) {
                    mScore = mScore + 1;
                    updateScore(mScore);
                    updateQuestion();

                    //toast kl benar toast kl salah
                    Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
            }
        });
    }

    private void updateQuestion() {
        if (isFirst) {
            isFirst = false;
        } else {
            if (mQuestionNumber < mQuizQuestion.getmTotalQuestion() - 1) {
                mQuestionNumber++;
            }else
            {
                linearLayoutQuestion.setVisibility(View.GONE);
                linearLayoutScore.setVisibility(View.VISIBLE);
                textViewGreetings.setText("Hello, your score is");
                textViewScore.setText(String.valueOf(mScore));
            }
        }

        mQuestionView.setText(mQuizQuestion.getQuestion(mQuestionNumber));
        mButtonOption1.setText(mQuizQuestion.getOption1(mQuestionNumber));
        mButtonOption2.setText(mQuizQuestion.getOption2(mQuestionNumber));
        mButtonOption3.setText(mQuizQuestion.getOption3(mQuestionNumber));
        mButtonOption4.setText(mQuizQuestion.getOption4(mQuestionNumber));

        mAnswer = mQuizQuestion.getCorrectAnswer(mQuestionNumber);
    }

    private void updateScore(int point) {
        mScoreView.setText("" + mScore);
    }
}
