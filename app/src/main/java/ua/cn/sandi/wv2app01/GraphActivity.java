package ua.cn.sandi.wv2app01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

    Button acc;
    Button dec;
    Button brk;

    Button act1;

    ProgressBar pbCount;

    final int max = 100;

    int cnt;

    Boolean timecount = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphics_main);


        // main class init
        final Arkobox mysb = new Arkobox(this);


        //buttons
        acc=(Button)findViewById(R.id.button_spdup);
        dec=(Button)findViewById(R.id.button_spddown);
        brk=(Button)findViewById(R.id.button_brk);

        bcl=(Button)findViewById(R.id.button_changelayout);
        bbip=(Button)findViewById(R.id.button_bip);
        act1=(Button)findViewById(R.id.button_action01);

        bleft=(Button)findViewById(R.id.button_left);
        bright=(Button)findViewById(R.id.button_right);

        bcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraphActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code here
                mysb.setAngle(mysb.getAngle()-10);
            }
        });

        bright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code here
                mysb.setAngle(mysb.getAngle()+10);
            }
        });

        bbip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mysb.playsound(GraphActivity.this, R.raw.nominal);
            }
        });

        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code here
                mysb.speedUp();
            }
        });

        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code here
                mysb.speedDown();
            }
        });

        brk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code here
                mysb.breaking();
            }
        });





        // progress bar init

        pbCount = (ProgressBar) findViewById(R.id.pbCount);
        pbCount.setMax(max);
        pbCount.setProgress(0);

        final Runnable updateProgress = new Runnable() {
            public void run() {
                pbCount.setProgress(cnt);
            }
        };

        Thread t_progress = new Thread(new Runnable() {
            public void run() {
                try {
                    for (cnt = 1; cnt < max; cnt++) {

                        TimeUnit.MILLISECONDS.sleep(50);

                        if (cnt == 99) { cnt = 1;}

                        runOnUiThread(updateProgress);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t_progress.start();     // progress started


        //static layer init

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

        // action update

        final Runnable updateLayer = new Runnable() {
            public void run() {
                mysb.drawActionLayer((ImageView) findViewById(R.id.myActionView), mysb.getCa());
            }
        };

        Thread t2 = new Thread(new Runnable() {
            public void run() {

                try {
                    while(timecount){

                        TimeUnit.MILLISECONDS.sleep(50);

                        runOnUiThread(updateLayer);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        t2.start();   // started

    }    //onCreate end

}   // activity end

