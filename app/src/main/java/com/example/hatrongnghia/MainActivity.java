package com.example.hatrongnghia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hatrongnghia.database.AppDatabase;
import com.example.hatrongnghia.entity.FeedbackEntity;

public class MainActivity extends AppCompatActivity {
    EditText edName, edEmail, edDescription;
    Spinner spinner;
    CheckBox cbPrivacy;
    Button btnSend;
    String option = "1";
    String[] listOption = {"1", "2", "3"};
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = AppDatabase.getAppDatabase(this);

        initView();
        onSend();
    }

    private void initView() {
        edName = findViewById(R.id.edName);
        edEmail = findViewById(R.id.edEmail);
        edDescription = findViewById(R.id.edDescription);

        spinner = findViewById(R.id.spinner);
        setSpinner();

        cbPrivacy = findViewById(R.id.cbPrivacy);
        btnSend = findViewById(R.id.btnSend);
    }

    private void onSend() {
        FeedbackEntity feedback = new FeedbackEntity();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edName.getText().toString().toLowerCase().trim();
                String email = edEmail.getText().toString().toLowerCase().trim();
                String description = edDescription.getText().toString().toLowerCase().trim();
                if (name.equalsIgnoreCase("") || email.equalsIgnoreCase("") || description.equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Fill all edit", Toast.LENGTH_SHORT).show();
                } else {
                    if (EditTextValidation.isValidEmailId(email)) {
                        if (!cbPrivacy.isChecked()) {
                            Toast.makeText(MainActivity.this, "Check the checkbox", Toast.LENGTH_SHORT).show();
                        } else {
                            feedback.setName(name);
                            feedback.setEmail(email);
                            feedback.setDescription(description);
                            feedback.setOption(option);
                            db.feedbackDao().insertFeedback(feedback);
                            Toast.makeText(MainActivity.this, db.feedbackDao().getAllFeedback().size() + " feedbacks in database",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else
                        Toast.makeText(MainActivity.this, "Email not valid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listOption);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                option = listOption[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}