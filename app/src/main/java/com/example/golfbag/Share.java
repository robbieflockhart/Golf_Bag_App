/*
 * This class allows the user to tweet the names of each club in the bag list.
 *
 * @author  Robbie Flockhart - 40343879
 */
package com.example.golfbag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Share extends AppCompatActivity {

    private ArrayList<Club> bagList;
    private TextView tweet;
    private Button button;
    private String bagListString = "";
    private String bagListTweet = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

            //navigation bar that switches activities.
            BottomNavigationView navBar = (BottomNavigationView) findViewById(R.id.navBar);

            Menu menu = navBar.getMenu();
            MenuItem menuItem = menu.getItem(3);
            menuItem.setChecked(true);

            navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.ic_view_list:
                            Intent intent1 = new Intent(Share.this, ClubList.class);
                            startActivity(intent1);
                            break;

                        case R.id.ic_golf_course:
                            Intent intent2 = new Intent(Share.this, MainActivity.class);
                            startActivity(intent2);
                            break;

                        case R.id.ic_add_box:
                            Intent intent3 = new Intent(Share.this, AddClub.class);
                            startActivity(intent3);
                            break;

                        case R.id.ic_share:

                            break;
                    }

                    return false;
                }
            });

        //gets the contents of the bag list from the saved json string.
        SharedPreferences sharedPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Bag List", null);
        Type type = new TypeToken<ArrayList<Club>>() {}.getType();
        bagList = gson.fromJson(json, type);

        if(bagList == null) {
            bagList = new ArrayList<>();
        }

        //sets out the structure of the tweet.
        for (Club c : bagList)
        {
            bagListString += c.getName() + "\n";
            bagListTweet += c.getName() + ", ";

        }

        tweet = (TextView) findViewById(R.id.textView2);
        tweet.setText(bagListString);

        //on click listener - when the button is clicked the tweet is composed and twitter is opened.
        button = (Button) findViewById(R.id.composeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetUrl = "https://twitter.com/intent/tweet?text=" + bagListTweet+ " &url=";
                Uri uri = Uri.parse(tweetUrl);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });

    }
}
