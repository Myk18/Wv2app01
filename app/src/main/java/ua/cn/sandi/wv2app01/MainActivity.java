package ua.cn.sandi.wv2app01;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

    Button b1;
    Button bd;
    Button bu;
    Button bcl;
    EditText ed1;

    private WebView wv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        b1=(Button)findViewById(R.id.button);
        bu=(Button)findViewById(R.id.button_u);
        bd=(Button)findViewById(R.id.button_d);
        bcl=(Button)findViewById(R.id.button_changelayout);

        ed1=(EditText)findViewById(R.id.editText);
        ed1.setText("http://testung.sandi.cn.ua/arkabox/arkabox.html");

        wv1=(WebView)findViewById(R.id.webView);

        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearApplicationCache();
                String url = ed1.getText().toString();
                wv1.loadUrl(url);
            }
        });
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InjectKeys(19);
            }
        });
        bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InjectKeys(20);
            }
        });

        bcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GraphActivity.class);
                startActivity(intent);
                }
        });
    }

    private void InjectKeys(final int keyEventCode) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Instrumentation().sendKeyDownUpSync(keyEventCode);
            }
        }).start();
    }

    private void clearApplicationCache()
    {
        File dir = getCacheDir();

        if(dir!= null && dir.isDirectory())
        {
            try
            {
                ArrayList<File> stack = new ArrayList<File>();

                // Initialise the list
                File[] children = dir.listFiles();
                for(File child:children)
                {
                    stack.add(child);
                }

                while(stack.size() > 0)
                {
                    Log.v(TAG, "Clearing the stack - " + stack.size());
                    File f = stack.get(stack.size() - 1);
                    if(f.isDirectory() == true)
                    {
                        boolean empty = f.delete();

                        if(empty == false)
                        {
                            File[] files = f.listFiles();
                            if(files.length != 0)
                            {
                                for(File tmp:files)
                                {
                                    stack.add(tmp);
                                }
                            }
                        }
                        else
                        {
                            stack.remove(stack.size() - 1);
                        }
                    }
                    else
                    {
                        f.delete();
                        stack.remove(stack.size() - 1);
                    }
                }
            }
            catch(Exception e)
            {
                Log.e(TAG, "Failed to clean the cache");
            }
        }
    }

    private void PlayStartSound (final int snd){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // play sound

            }
        }).start();
    }


}

