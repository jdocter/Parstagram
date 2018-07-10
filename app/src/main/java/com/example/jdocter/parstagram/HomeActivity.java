package com.example.jdocter.parstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String imagePath = "/Users/jdocter/Downloads/twitter-logo.png";
    private EditText etDescription;
    private Button btnCreate;
    private Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        etDescription = findViewById(R.id.etDescription);
        btnCreate = findViewById(R.id.btnCreate);
        btnRefresh = findViewById(R.id.btnRefresh);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String description = etDescription.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();
                final File file = new File(imagePath);
                final ParseFile parseFile = new ParseFile(file);

                createPost(description,parseFile,user);
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTopPosts();
            }
        });





    }

    private void createPost(String description, ParseFile image, ParseUser user) {
        final com.example.jdocter.parstagram.model.Post newPost = new com.example.jdocter.parstagram.model.Post();
        newPost.setDescription(description);
        //newPost.setImage(image);
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null) {
                    Log.d("HomeActivity", "Post success");
                } else {
                    Log.e("HomeActivity", "Post Failure");
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadTopPosts() {
        final com.example.jdocter.parstagram.model.Post.Query postQuery = new com.example.jdocter.parstagram.model.Post.Query();
        postQuery.getTop().withUser();

        postQuery.findInBackground(new FindCallback<com.example.jdocter.parstagram.model.Post>() {
            @Override
            public void done(List<com.example.jdocter.parstagram.model.Post> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        Log.d("HomeActivity", "Post[" + i + "]=" + objects.get(i).getDescription()
                                + "\nusername = "
                                + objects.get(i).getUser().getUsername());
                    }
                }

            }
        });
    }


}