package com.example.datastorageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    // format file
    public static final String FILENAME = "namafile.txt";
    public static final String PREFNAME = "com.example.datastorageapp.PREF";

    private TextView textBaca;
    private TextView editText;

    private static final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textBaca = findViewById(R.id.text_baca);
        editText = findViewById(R.id.edit_text);
    }

    private static boolean hasPermission (Context context, String... permissions){
        if  (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null){
            for (String permission:permissions){
                if (ActivityCompat.checkSelfPermission(context,permission)
                        != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }

        }
        return true;
    }

    public void Simpan(View view) {
        // untuk Share Preference
        simpanFileSP();

        //untuk Internal Storage
        simpanFileIS();

        //untuk Internal Storage
        simpanFileES();
    }


    public void hapus(View view) {

        // untuk Share Preference
        hapusFileSP();

        //untuk Internal Storage
        hapusFileIS();

        //untuk Internal Storage
        hapusFileES();

    }

    public void Baca(View view) {
        // untuk Share Preference
        bacaFileSP();

        //untuk Internal Storage
        bacaFileIS();

        //untuk Internal Storage
        bacaFileES();
    }


    private void simpanFileSP() {
        String isiFile = editText.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(PREFNAME
        , MODE_PRIVATE);
        // agar bisa di update
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FILENAME,isiFile);
        editor.commit();
    }

    private void bacaFileSP() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFNAME
                , MODE_PRIVATE);
        if (sharedPreferences.contains(FILENAME)){
                String mytet = sharedPreferences.getString(FILENAME,"");
                textBaca.setText(mytet);
        }else{
            textBaca.setText("");
        }
    }

    private void hapusFileSP() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFNAME
                , MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }


    // Fungsi Internal Storage
    private void simpanFileIS() {

        String isiFile = editText.getText().toString();
        File path = getDir("NEWFOLDER",MODE_PRIVATE);
        File file = new File(path.toString(),FILENAME);
        FileOutputStream outputStream = null;

        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, false);
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void bacaFileIS() {
        File path = getDir("NEWFOLDER",MODE_PRIVATE);
        File file = new File(path.toString(),FILENAME);
        if (file.exists()){
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null){
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
            textBaca.setText(text.toString());
        }else {
            textBaca.setText("");
        }
    }

    private void hapusFileIS() {
        File path = getDir("NEWFOLDER",MODE_PRIVATE);
        File file = new File(path.toString(),FILENAME);
        if (file.exists()){
            file.delete();
        }
    }


    // Fungsi External Storage
    private void simpanFileES() {
        if (hasPermission(this,PERMISSIONS)){
            String isiFile = editText.getText().toString();
            File path = Environment.getExternalStorageDirectory();

            //Membuat folder baru dengan nama NAMAFOLDER
            File file = new File(path.toString()+"/NAMAFOLDER",FILENAME);
            FileOutputStream outputStream = null;

            try {
                file.createNewFile();
                outputStream = new FileOutputStream(file, false);
                outputStream.write(isiFile.getBytes());
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE);
        }
    }

    private void bacaFileES() {
        File path = Environment.getExternalStorageDirectory();
        File file = new File(path.toString(),FILENAME);
        if (file.exists()){
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null){
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
            textBaca.setText(text.toString());
        }else {
            textBaca.setText("");
        }
    }

    private void hapusFileES() {
        File path = getDir("NEWFOLDER",MODE_PRIVATE);
        File file = new File(path.toString(),FILENAME);
        if (file.exists()){
            file.delete();
        }
    }
}
