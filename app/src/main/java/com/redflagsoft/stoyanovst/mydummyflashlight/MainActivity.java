package com.redflagsoft.stoyanovst.mydummyflashlight;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
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
            forDummies();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void letThereBeLight(View view) throws CameraAccessException {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String camId[];
        Button toggleSwitch = (Button) findViewById(R.id.button);

       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            camId = manager.getCameraIdList();
            if (toggleSwitch.getText() == getString(R.string.button_text_on)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    manager.setTorchMode(camId[0], true);
                    toggleSwitch.setText(R.string.button_text_off);
                }
            } else if (toggleSwitch.getText() == getString(R.string.button_text_off)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    manager.setTorchMode(camId[0], false);
                    toggleSwitch.setText(R.string.button_text_on);
                }


            }

        }
    }

    public void forDummies(){
        Toast.makeText(MainActivity.this, "Access Denied!", Toast.LENGTH_LONG).show();
    }
}
