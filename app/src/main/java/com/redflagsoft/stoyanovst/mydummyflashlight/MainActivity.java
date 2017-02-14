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
    private boolean isItTurned = false;


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
                        letThereBeLight();
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

            new Thread(new Runnable() {
                @Override
                public void run() {
                    forDummies();
                }
            }).start();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeLabel(int buttonLabel){
       toggleSwitch.setText(buttonLabel);
    }

    public void letThereBeLight() {
                    if(!isItTurned){
                        new Thread( new Runnable() {
                            @Override
                            public void run() {
                                turnFlashOn();
                            }
                        }).start();
                        changeLabel(R.string.button_text_off);
                    }
                    else {
                        new Thread(new Runnable() {
                            @Override
                            @SuppressWarnings("deprecation")
                            public void run() {
                                try {
                                    turnFlashOff();

                                } catch (IOException e) {
                                    cam.release();
                                    cam = Camera.open();
                                }
                            }
                        }).start();
                        changeLabel(R.string.button_text_on);
                    }
    }

    @SuppressWarnings("deprecation")
    public void turnFlashOn(){
        isItTurned = true;
        cam = Camera.open();
        p = cam.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        cam.setParameters(p);
        cam.startPreview();

    }

    @SuppressWarnings("deprecation")
    public void turnFlashOff() throws IOException{

        isItTurned = false;
        cam.reconnect();
        p = cam.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        cam.setParameters(p);
        cam.stopPreview();
        cam.release();


    }


   /* @TargetApi(Build.VERSION_CODES.M)
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
    }*/

    public void forDummies(){
        new Thread( new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Access Denied!", Toast.LENGTH_LONG).show();
            }
        }).start();
    }
}
