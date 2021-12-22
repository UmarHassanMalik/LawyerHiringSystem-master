package com.innova.lawyerhiringsystem;
/*This activity is for both roles Client/Lawyer
* Provide feedback for platform
* Entered data will be pushed to firebase under 'feedback' node*/
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.innova.lawyerhiringsystem.model.Feedback;

public class GiveFeedback extends AppCompatActivity {

    EditText name, email, message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_feedback);

        name = findViewById(R.id.feedback_name);
        email = findViewById(R.id.feedback_email);
        message = findViewById(R.id.feedback_msg);
    }

    public void submitFeedback(View v)
    {
        String txtName, txtEmail, txtMessage;
        txtName = name.getText().toString();
        txtEmail = email.getText().toString();
        txtMessage = message.getText().toString();
        Feedback feedback = new Feedback(txtName,txtEmail, txtMessage);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("feedback").push() ;
        ref.setValue(feedback);

        Toast.makeText(this, "Tankyou for your feedback.",
                Toast.LENGTH_SHORT).show();

        // clearing entered text
        name.setText("");
        email.setText("");
        message.setText("");
    }


}