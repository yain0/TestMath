package com.example.testmath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.testmath.DB.DBService;
import com.example.testmath.Models.Subject;

import org.json.JSONException;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerSubject;
    private static final int REQUEST_CODE_TEST = 1;
    public static final String EXTRA_SUBJECT_ID = "extraSubjectID";
    public static final String EXTRA_SUBJECT_NAME = "extraSubjectName";


    private void startTest() {
        Subject selectedSubject = (Subject) spinnerSubject.getSelectedItem();
        int subjectID = selectedSubject.getId();
        String subjectName = selectedSubject.getName();

        Intent intent = new Intent(MainActivity.this, TestActivity.class);
        intent.putExtra(EXTRA_SUBJECT_ID, subjectID);
        intent.putExtra(EXTRA_SUBJECT_NAME, subjectName);

        startActivityForResult(intent, REQUEST_CODE_TEST);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerSubject = findViewById(R.id.spinner_subject);
        spinnerSubject.setSelection(1);

        try {
            DBService dbHelper = new DBService();
            List<Subject> subjects = dbHelper.getAllSubjects();
            String[] subjects_names = new String[subjects.size()];
            int i = 0;
            for(Subject subject : subjects){
                subjects_names[i]= subject.getName();
                i++;
            }

            ArrayAdapter<Subject> adapterSubjects = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, subjects);
            adapterSubjects.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSubject.setAdapter(adapterSubjects);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Button buttonStartTest = findViewById(R.id.button_start_test);
        buttonStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTest();
            }
        });
    }


}
