package com.innova.lawyerhiringsystem;
/* Case Messaging Board
*  Available for lawyers hired
*  send and recieve messages*/
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.innova.lawyerhiringsystem.model.ChatMessage;

public class CMB extends AppCompatActivity {

    private FirebaseListAdapter<ChatMessage> adapter;
    public String caseTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_m_b);

        Intent intent = getIntent();
        caseTitle = intent.getStringExtra("case");

        displayChatMessages();

        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference("messages").child(caseTitle)
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getEmail())
                        );

                // Clear the input
                input.setText("");

            }
        });
    }

    private void displayChatMessages() {

        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference("messages").child(caseTitle)) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);

    }
}