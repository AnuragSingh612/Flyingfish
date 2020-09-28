package com.example.flyingfish;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView extends View {
    private int fishX = 10;
    private int fishY;
    private int fishSpeed;
    private int canvasWidth, canvasHeight;
    private boolean touch = false;
    private int yellowx, yellowy, yellowspeed = 16;
    private int greenX,greenY,greenspeed=18;
    private int redx,redy,redspeed=21;
    private Paint redPaint=new Paint();
    private Paint greenPaint= new Paint();
    private Paint yellowPaint = new Paint();
    private int timer;
    private int score;
    private int lifecounter;

    private Bitmap fish[] = new Bitmap[2];
    private Bitmap backgroundImage,c;
    private Paint scorePaint = new Paint();
    private Paint timerPaint = new Paint();

    private Bitmap life[] = new Bitmap[2];

    public FlyingFishView(Context context) {
        super(context);
        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);
        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);
       //
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);
        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);
        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);
        timerPaint.setColor((Color.WHITE));
        timerPaint.setTextSize(80);
        timerPaint.setTypeface(Typeface.DEFAULT_BOLD);
        timerPaint.setAntiAlias(true);
        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);
        fishY = 550;
        score = 0;
        timer=0;
        lifecounter=0;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        canvas.drawBitmap(backgroundImage, 0, 0, null);
        timer++;

        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight() * 3;
        fishY = fishY + fishSpeed;
        if (fishY < minFishY) {
            fishY = minFishY;
        }
        if (fishY > maxFishY) {
            fishY = maxFishY;
        }
        fishSpeed = fishSpeed + 2;
        if (touch) {
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;
        } else {
            canvas.drawBitmap(fish[0], fishX, fishY, null);
        }

        yellowx = yellowx - yellowspeed;
        if (hitballChecker(yellowx, yellowy)) {
            score = score + 10;
            yellowx = -100;
        }
        if (yellowx < 0) {
            yellowx = canvasWidth + 21;
            yellowy = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(yellowx, yellowy, 25, yellowPaint);


     redx = redx- redspeed;
        if (hitballChecker(redx, redy)) {
            score = score - 20;
            redx = -100;
            lifecounter--;
            if(lifecounter==0)
            {
                Toast.makeText(getContext(),"game over",Toast.LENGTH_SHORT).show();
            }
        }
        if (redx < 0) {
            redx = canvasWidth + 21;
            redy = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(redx, redy, 25, redPaint);


        greenX = greenX - greenspeed;
        if (hitballChecker(greenX, greenY)) {
            score = score + 20;
            greenX = -100;

        }
        if (greenX < 0) {
           greenX = canvasWidth + 21;
           greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(greenX, greenY, 25, greenPaint);


       canvas.drawText("Time:"+timer,780,30,timerPaint);
        canvas.drawText("Score:" + score, 20, 60, scorePaint);

        // canvas.drawBitmap(fish[0],0,0,null);
        //canvas.drawText("Score :", 20, 60, scorePaint);
        canvas.drawBitmap(life[0], 580, 10, null);
        canvas.drawBitmap(life[0], 680, 10, null);
        canvas.drawBitmap(life[0], 780, 10, null);


    }

    public boolean hitballChecker(int x, int y) {

            if (fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight()))

            {
             return true;
            }
            return false;
        }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;
            fishSpeed = -22;


        }
        return true;
    }
}
