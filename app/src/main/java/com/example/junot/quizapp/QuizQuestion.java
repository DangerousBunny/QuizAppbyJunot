package com.example.junot.quizapp;

public class QuizQuestion {

    private int mTotalQuestion;

    private String mQuestions[] = {
            "What is NBA?",
            "How many teams are there in the NBA?",
            "How many games are there in an NBA season?",
            "What is the name of the championship series that the NBA has to end the playoffs and find a champion?",
            "Which one is the true GOAT?",
            "NBA logo silhouette based on?",
            "Which player with the most championship ring?",
            "Which player is the one with highest scoring point leader in NBA history?"
    };

    private String mOptions[][] = {
            {"National Basket Association", "National Baseball League", "National Badminton League", "National Beach Ball League"},
            {"30 team", "15 team", " 20 team", " 10 team"},
            {"82 games", "45 games", "70 games", "69 games"},
            {"Super bowl", "Rose bowl", "Champions League", "NBA Finals"},
            {"Michael Jordan", "Lebron James", "Steph Curry", "Kobe"},
            {"Lebron James", "Michael Jordan", "Jerry West", "Karl Malone"},
            {"Kobe", "Bill Russel", "Lebron James", "Michael Jordan"},
            {"Kareem Abdul Jabbar", "Michael Jordan", "Wilt Chamberlain", "Kobe Bryant"}
    };

    private String mCorrectAnswers[] = {
            "National Basket Association",
            "30 team", "82 games",
            "NBA Finals",
            "Michael Jordan",
            "jerry West", "" +
            "Bill Russel",
            "Kareem Abdul Jabbar"
    };

    public QuizQuestion() {
        mTotalQuestion = mQuestions.length;
    }

    public String getQuestion(int x) {
        String question = mQuestions[x];
        return question;
    }

    public String getOption1(int x) {
        String option0 = mOptions[x][0];
        return option0;
    }

    public String getOption2(int x) {
        String option1 = mOptions[x][1];
        return option1;
    }

    public String getOption3(int x) {
        String option2 = mOptions[x][2];
        return option2;
    }

    public String getOption4(int x) {
        String option3 = mOptions[x][3];
        return option3;
    }

    public String getCorrectAnswer(int x) {
        String answer = mCorrectAnswers[x];
        return answer;
    }

    public int getmTotalQuestion() {
        return mTotalQuestion;
    }
}
