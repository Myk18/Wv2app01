package ua.cn.sandi.wv2app01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.widget.ImageView;

import java.util.concurrent.TimeUnit;
import android.os.Handler;

/**
 * Created by mikni on 07.09.2017.
 */


class StatBitmap{

    Handler h;

    int ballWidth;

    int ballHeight;

    int ballSpeed=40; // 1-40 TimeUnit.MILLISECONDS.sleep(1000/ballSpeed)

    int[][] myLines = {
            {0,0,639,0,1},
            {639,0,639,479,2},
            {0,479,639,479,3},
            {0,0,639,0,4},
            {0,0,639,479,5},
            {639,0,0,479,6},
    };

    int[][] myRects = {
            {0,10,100,20,1},
            {320,120,345,155,2},
    };

    int[][] myCircles = {
            {150,80,75,3},
            {318,100,5,3},
            {420,0,50,3},
            {500,88,15,3},
            {639,240,20,3},
    };

    private Canvas ca;

    private Canvas c;

    private Bitmap b;

    private Bitmap ba;

    StatBitmap(){

        h = new Handler();

        b = Bitmap.createBitmap(640,480,Bitmap.Config.ARGB_8888);

        ba = Bitmap.createBitmap(640,480,Bitmap.Config.ARGB_8888);

        c = new Canvas(b);

        ca = new Canvas(ba);

        Thread t2 = new Thread(new Runnable() {
            public void run() {

                try {

                    int wi;
                    int hi;

                    for (wi = 1; wi < 640; wi++) {

                        TimeUnit.MILLISECONDS.sleep(1000/ballSpeed);

                        hi = wi * 480 / 640;

                        if (wi == 635) wi = 1;

                        ballWidth = wi;
                        ballHeight = hi;

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        t2.start();   // started

    }

    private void setBallWidth(int ballWidth) {
        this.ballWidth = ballWidth;
    }

    private void setBallHeight(int ballHeight) {
        this.ballHeight = ballHeight;
    }

    public int getBallWidth() {
        return ballWidth;
    }

    public int getBallHeight() {
        return ballHeight;
    }

    public Canvas getCa() {
        return ca;
    }



    public void playsound(Context c, int o) {

        final MediaPlayer mp = MediaPlayer.create(c, o);

        final Runnable sound01 = new Runnable() {
            public void run() {
                mp.start();
            }
        };

        Thread playsound = new Thread(new Runnable() {
            @Override
            public void run() {
                h.post(sound01);
            }
        });
        playsound.start();

    }


    private Paint idToPaint(int i){

        Paint paintId = new Paint();

        switch (i) {
            case 1:
                paintId.setColor(Color.CYAN);
                break;
            case 2:
                paintId.setColor(Color.MAGENTA);
                break;
            case 3:
                paintId.setColor(Color.RED);
                break;
            case 4:
                paintId.setColor(Color.YELLOW);
                break;
            case 5:
                paintId.setColor(Color.BLUE);
                break;
            case 6:
                paintId.setColor(Color.GREEN);
                break;
            case 7:
                paintId.setColor(Color.GRAY);
                break;
            case 8:
                paintId.setColor(Color.WHITE);
                break;
            default:
                paintId.setColor(Color.BLACK);
                break;
        }
        return paintId;
    }

    private void drawmylines() {

        for (int[] line : myLines) {

            c.drawLine(line[0], line[1], line[2], line[3], idToPaint(line[4]));
        }
    }
    private void drawmyrects() {

        for (int[] rect : myRects) {

            RectF pri = new RectF(rect[0], rect[1], rect[2], rect[3]);
            c.drawRect(pri, idToPaint(rect[4]));
        }
    }
    private void drawmycircles() {

        for (int[] circle : myCircles) {

            c.drawCircle(circle[0], circle[1], circle[2], idToPaint(circle[3]));

        }
    }
    public Canvas drawmyAcircles(Canvas ca) {
        ca.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        ca.drawCircle(ballWidth, ballHeight, 20, idToPaint(5));
        return ca;
    }

    public void drawStaticLayer(ImageView iv){

        drawmyrects();
        drawmylines();
        drawmycircles();

        iv.setImageBitmap(b);
    }
    public void drawActionLayer(ImageView iv, Canvas ca){

        drawmyAcircles(ca);

        iv.setImageBitmap(ba);

    }

}   // StatBitmap class end


