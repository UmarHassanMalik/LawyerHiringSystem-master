package com.innova.lawyerhiringsystem;
/* Role: Lawyer
*  Class to view all available Articles
* */
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.innova.lawyerhiringsystem.model.Article;
import com.innova.lawyerhiringsystem.model.Lawyer;

public class ViewArticle extends AppCompatActivity {

    TextView viewArticle;
    Article article;

    public String articles = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_article);

        viewArticle = findViewById(R.id.view_articles);
        //setting custom font
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/segoeui.ttf");
        viewArticle.setTypeface(face);

        loadArticles();
    }

    public void loadArticles()
    { // perform read operation to database

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("article");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                   articles = ""; // clearing string for recurring read -- attached callback
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    article = child.getValue(Article.class);
                    Log.i("articles", article.getTitle());
                    Log.i("articles", article.getContent());

                    articles += "\n" + article.getTitle() + "\n" + article.getContent() + "\n";
                }

                viewArticle.setText(articles);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void intentPostArticles(View view) {
        startActivity(new Intent(ViewArticle.this, PostArticle.class));
    }
}