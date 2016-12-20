package com.redflagsoft.stoyanovst.mydummyflashlight;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import android.os.Handler;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button toggleSwitch;
    private Camera cam;
    private Camera.Parameters p;
    private Handler handler;
    private Runnable runnable;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Simple Dummy Flashlight by Stoyanov's RedFlagSoft", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        toggleSwitch = (Button) findViewById(R.id.button);
        toggleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler = new Handler(Looper.getMainLooper());
                runnable = new Runnable(){
                    @Override
                    public void run(){
                        letThereBeLight();
                    }
                };
                handler.post(runnable);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            handler = new Handler(Looper.getMainLooper());
            runnable = new Runnable() {
                @Override
                public void run() {
                    forDummies();
                }
            };
            handler.post(runnable);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    boolean isItTurned = false;

    public void changeLabel(){
        if(isItTurned){
            toggleSwitch.setText(R.string.button_text_off);
        }
        else toggleSwitch.setText(R.string.button_text_on);
    }

    @SuppressWarnings("deprecation")
    public void letThereBeLight() {

        runnable = new Runnable() {
            @Override
            public void run() {

                try {

                    if (!isItTurned) {
                        cam = Camera.open();
                        p = cam.getParameters();
                        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        cam.setParameters(p);
                        cam.startPreview();
                        isItTurned = !isItTurned;
                    } else {
                        cam.reconnect();
                        p = cam.getParameters();
                        p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        cam.setParameters(p);
                        cam.stopPreview();
                        cam.release();
                        isItTurned = !isItTurned;
                    }
                }

                catch(IOException e) {
                    cam.release();
                    cam = Camera.open();
                }
                finally {
                    changeLabel();
                }
            }

        };
        handler.post(runnable);

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void lightForNewAndroid(Button toggleSwitch) throws CameraAccessException{

        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String camId[];
        camId = manager.getCameraIdList();

        if (toggleSwitch.getText() == getString(R.string.button_text_on)) {
            manager.setTorchMode(camId[0], true);
            toggleSwitch.setText(R.string.button_text_off);
        } else if (toggleSwitch.getText() == getString(R.string.button_text_off)) {
                    manager.setTorchMode(camId[0], false);
                    toggleSwitch.setText(R.string.button_text_on);
        }
    }

    public void forDummies(){
        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Access Denied!", Toast.LENGTH_LONG).show();
            }
        };
        handler.post(runnable);
    }
}
