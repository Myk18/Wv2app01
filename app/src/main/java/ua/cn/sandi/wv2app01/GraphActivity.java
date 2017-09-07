package ua.cn.sandi.wv2app01;

import android.app.Activity;
import android.content.Intent;
import android.graphics.*;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import java.util.concurrent.TimeUnit;


/**
 * Created by mikni on 18.08.2017.
 */

public class GraphActivity extends Activity {

    Button bcl;

    Button bbip;

    Button bleft;
    Button bright;

    Handler h;

    ProgressBar pbCount;

    final int max = 100;

    int cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphics_main);

        h = new Handler();

        bcl=(Button)findViewById(R.id.button_changelayout);
        bbip=(Button)findViewById(R.id.button_bip);

        bleft=(Button)findViewById(R.id.button_left);
        bright=(Button)findViewById(R.id.button_right);

        bcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        bleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
                //v.setSoundEffectsEnabled(true);
                //audioManager.playSoundEffect(SoundEffectConstants.CLICK);
            }
        });

        bright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
                //v.setSoundEffectsEnabled(true);
                //audioManager.playSoundEffect(SoundEffectConstants.CLICK);
            }
        });

        pbCount = (ProgressBar) findViewById(R.id.pbCount);
        pbCount.setMax(max);
        pbCount.setProgress(0);


        // main class
        final StatBitmap mysb = new StatBitmap();


        final Runnable updateProgress = new Runnable() {
            public void run() {
                pbCount.setProgress(cnt);
            }
        };

        Thread t_progress = new Thread(new Runnable() {
            public void run() {
                try {
                    for (cnt = 1; cnt < max; cnt++) {
                        TimeUnit.MILLISECONDS.sleep(500);

                        if (cnt == 99) { cnt = 1;}

                        // обновляем ProgressBar
                        h.post(updateProgress);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t_progress.start();     // progress started



        bbip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mysb.playsound(GraphActivity.this, R.raw.nominal);
            }
        });


        final Runnable updateLayer = new Runnable() {
            public void run() {
                mysb.drawActionLayer((ImageView) findViewById(R.id.myActionView), mysb.getCa());
            }
        };

        Thread t2 = new Thread(new Runnable() {
            public void run() {

                    try {

                        int wi;
                    //    int hi;

                            for (wi = 1; wi < 640; wi++) {

                                TimeUnit.MILLISECONDS.sleep(35);

                    //            hi = wi * 480 / 640;

                                if (wi == 635) wi = 1;
/*
                                mysb.setBallWidth(wi);
                                mysb.setBallHeight(hi);
*/
                                runOnUiThread(updateLayer);

                            }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }
        });
        t2.start();   // started

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    mysb.drawStaticLayer((ImageView) findViewById(R.id.myImageView));
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }    //onCreate end

    private class StatBitmap1{

        StatBitmap1(){

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

        int ballWidth;

        int ballHeight;

        int ballSpeed=40; // 1-40 TimeUnit.MILLISECONDS.sleep(1000/ballSpeed)

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

        private Bitmap b = Bitmap.createBitmap(640,480,Bitmap.Config.ARGB_8888);

        private Bitmap ba = Bitmap.createBitmap(640,480,Bitmap.Config.ARGB_8888);

        private Canvas c = new Canvas(b);

        private Canvas getCa() {
            return ca;
        }

        private Canvas ca = new Canvas(ba);

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


        final MediaPlayer mp = MediaPlayer.create(GraphActivity.this, R.raw.nominal);

        final Runnable sound01 = new Runnable() {
            public void run() {
                mp.start();
            }
        };

        private void playsound() {

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
        private Canvas drawmyAcircles(Canvas ca) {
                ca.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                ca.drawCircle(ballWidth, ballHeight, 20, idToPaint(5));
            return ca;
        }

        private void drawStaticLayer(ImageView iv){

            drawmyrects();
            drawmylines();
            drawmycircles();

            iv.setImageBitmap(b);
        }
        private void drawActionLayer(ImageView iv, Canvas ca){

            drawmyAcircles(ca);

            iv.setImageBitmap(ba);

        }

    }   // StatBitmap class end


}   // activity end

