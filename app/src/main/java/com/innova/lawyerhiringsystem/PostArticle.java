package com.innova.lawyerhiringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.innova.lawyerhiringsystem.model.Article;

public class PostArticle extends AppCompatActivity {

    EditText aTitle, aContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_article);

        aTitle = findViewById(R.id.article_title);
        aContent = findViewById(R.id.article_content);
    }

    public void postArticle(View view) {
        String title, content;
        title = aTitle.getText().toString();
        content = aContent.getText().toString();
        Article article = new Article(title,content);

        // pushing to database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("article").push() ;
        ref.setValue(article);

        Toast.makeText(this, "Article has been posted.",
                Toast.LENGTH_SHORT).show();

        startActivity(new Intent(PostArticle.this,  LawyerDashboard.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

    }
}