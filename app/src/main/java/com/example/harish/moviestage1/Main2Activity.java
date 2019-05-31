package com.example.harish.moviestage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


public class Main2Activity extends AppCompatActivity {
    TextView tv1,tv2,tv3,tv4,tv5;
    ImageView imageView;
    String poster_path,title,id,vote_avg,overview,release_date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv1=findViewById(R.id.title_text);
        tv2=findViewById(R.id.vote_avg);
        tv3=findViewById(R.id.release_date);
        tv4=findViewById(R.id.overview_text);
        imageView=findViewById(R.id.img1);
        poster_path= getIntent().getStringExtra("poster_path");
        title=getIntent().getStringExtra("title");
        id=getIntent().getStringExtra("id");
        vote_avg=getIntent().getStringExtra("vote_avg");
        overview=getIntent().getStringExtra("overview");
        release_date=getIntent().getStringExtra("release_date");
        Picasso.get().load(poster_path).into(imageView);
        tv1.setText("Title :"+ title);
        tv2.setText("Vote Avg :"+ vote_avg);
        tv3.setText("Release Date:"+ release_date);
        tv4.setText("OverView :"+  overview);

        }
}
