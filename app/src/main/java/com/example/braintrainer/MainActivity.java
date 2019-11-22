package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> answers = new ArrayList<>();
    TextView question , answer , score , timer;
    int totalQuestions , totalCorrectAnswer;
    Button b0,b1,b2,b3,playagain;
    int a,b,positionOfCorrectAnswer;

    public void go(View view){
        view.setVisibility(View.INVISIBLE);
    }

    public void generateQuestion(){
        answers.clear();
        Random random = new Random();
        a = random.nextInt(20);
        b = random.nextInt(20);
        positionOfCorrectAnswer = random.nextInt(4);

        for(int i=0;i<4;i++)
        {
            if(i == positionOfCorrectAnswer){
                answers.add(a+b);
            }
            else
            {
                int c = random.nextInt(50);
                while(c == a+b || answers.contains(c)){
                    c = random.nextInt(50);
                }
                answers.add(c);
            }
        }
    }

    public void nextQuestion()
    {
        String q = "What is " + a + " + " + b + " ?";
        question.setText(q);
        b0.setText(String.valueOf(answers.get(0)));
        b1.setText(String.valueOf(answers.get(1)));
        b2.setText(String.valueOf(answers.get(2)));
        b3.setText(String.valueOf(answers.get(3)));
    }

    public void check(View view)
    {
        String tag = view.getTag().toString();
        int position = Integer.parseInt(tag);
        String display = "";
        if(position == positionOfCorrectAnswer)
        {
            totalCorrectAnswer+=1;
            display = "Correct Answer";
        } else{
            display = "Wrong Answer";
        }
        totalQuestions+=1;
        score.setText(String.valueOf(totalCorrectAnswer) + "/" + String.valueOf(totalQuestions));
        answer.setVisibility(View.VISIBLE);
        answer.setText(display);
        generateQuestion();
        nextQuestion();
    }

    public void timers()
    {
        //  Timer not working properly
        Log.d("Inside" , "Timer");
        new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(Long.toString(millisUntilFinished/1000 ) + "s");
                Log.d("Timer" , "timer " + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                b0.setEnabled(false);
                b1.setEnabled(false);
                b2.setEnabled(false);
                b3.setEnabled(false);
                answer.setText("Your score: " + totalCorrectAnswer + "/" + totalQuestions);
                playagain.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void init()
    {

        question = (TextView) findViewById(R.id.question);
        b0 = (Button) findViewById(R.id.button0);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        answer = (TextView) findViewById(R.id.answer);
        answer.setText("");
        timer = (TextView) findViewById(R.id.timer);
        playagain = (Button) findViewById(R.id.playagain);
        playagain.setVisibility(View.INVISIBLE);
        totalCorrectAnswer = 0;
        totalQuestions = 0;
        score = (TextView) findViewById(R.id.score);
        score.setText("0/0");
        timer.setText("10s");
        b0.setEnabled(true);
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
    }

    public void playagain(View view){
        init();
        generateQuestion();
        nextQuestion();
        timers();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        generateQuestion();
        nextQuestion();
        timers();
    }
}
