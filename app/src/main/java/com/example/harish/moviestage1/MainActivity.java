package com.example.harish.moviestage1;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    StatefullRecyclerview recyclerView;
    RequestQueue requestQueue;
    String key="top_rated";
    List<Movies> list;
    String posterlink="https://image.tmdb.org/t/p/w185";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor speditor;
    public static final String State="state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.MovieRec);
        requestQueue= Volley.newRequestQueue(this);
        sharedPreferences=getSharedPreferences("harish",MODE_PRIVATE);
        String key1=sharedPreferences.getString(State,null);
        if(key1!=null){
            if(key1.equalsIgnoreCase(key)){
                display(key);
            }
            else{
                display(key1);
            }
            }

         if(key1==null){

            display(key);
         }
         }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.popular){
            String key="popular";
            speditor=sharedPreferences.edit();
            speditor.putString(State,key);
            speditor.commit();
            requestQueue= Volley.newRequestQueue(this);

            display(key);

        }
        if(id==R.id.top_rated){
            String key="top_rated";
            speditor=sharedPreferences.edit();
            speditor.putString(State,key);
            speditor.commit();
            requestQueue= Volley.newRequestQueue(this);
            display(key);
        }
     return super.onOptionsItemSelected(item);
    }

    public void display(String key){

        this.key=key;

        final String movieurl="https://api.themoviedb.org/3/movie/"+key+"?api_key=9579b92f0766e5fb7c586e7f7fe3d874";
        StringRequest stringRequest=new StringRequest(Request.Method.GET,movieurl,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    String poster_path=null;
                    String title=null;
                    String id=null;
                    String vote_avg=null;
                    String overview=null;
                    String release_date=null;
                    list=new ArrayList<>();
                    JSONObject root=new JSONObject(response);
                    JSONArray results=root.getJSONArray("results");
                    for (int i=0;i<results.length();i++){
                        JSONObject object=results.getJSONObject(i);
                        title=object.getString("title");
                        poster_path=posterlink+object.getString("poster_path");

                        id=object.getString("id");
                        vote_avg=object.getString("vote_average");
                        overview=object.getString("overview");
                        release_date=object.getString("release_date");

                        Movies movies=new Movies(title,poster_path,id,vote_avg,overview,release_date);
                        list.add(movies);

                    }

                    } catch (Exception e) {
                    e.printStackTrace();
                }
                MyAdapter myAdapter=new MyAdapter(MainActivity.this,list);

                if(getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                }
                else{
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                }
                recyclerView.setAdapter(myAdapter);

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);

    }
}
