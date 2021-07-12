package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    public void onLike(View view) throws IOException {
        TextView textView = (TextView) findViewById(R.id.textView);
        FileOutputStream fos = openFileOutput("like.txt", Context.MODE_PRIVATE);
        fos.write(textView.getText().toString().getBytes());
        fos.close();
    }

    public void onClick(View view){
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app1.db", MODE_PRIVATE, null);

        EditText etSkills = findViewById(R.id.etSkills);
        EditText etPayment = findViewById(R.id.etPayment);
        EditText etOperating = findViewById(R.id.etOperating);

        Cursor query = db.rawQuery("SELECT * FROM job;", null);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("");
        boolean b = false;
        boolean c = false;
        boolean d = false;
        while(query.moveToNext()){
            b = false;
            c = false;
            d = false;
            int space = 0;
            String name = query.getString(0);
            String skills = query.getString(1);
            String payment = query.getString(2);
            String operating = query.getString(3);
            String ex_skill = skills;
            String sk = etSkills.getText().toString();
            while (true){
                space = ex_skill.indexOf(' ');
                if (ex_skill.indexOf(' ') == -1) {
                    if (sk.equals(ex_skill)) {
                        b = true;
                    }
                    break;
                }
                if (sk.equals(ex_skill.substring(0,space))){
                    b = true;
                }
                ex_skill = ex_skill.substring(space+1);
            }
            String pay = etPayment.getText().toString();
            int price = Integer.parseInt(pay);
            if( price < Integer.parseInt(payment)) {
                c = true;
            }
            if (etOperating.getText().toString().equals(operating)) {
                d = true;
            }
            if(b&c&d) {
                textView.append("Name: " + name + "\n" + "Skills: " + skills + "\n" + "Оплата: " + payment + "\n"
                        + "Режим работы: " + operating + "\n" + "\n");
            }
        }
        query.close();
        db.close();
    }
}