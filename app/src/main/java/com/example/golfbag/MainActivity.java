/*
 * This activity displays the bag list in a recycler view.
 *
 * @author  Robbie Flockhart - 40343879
 */
package com.example.golfbag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BagListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Club> bagList = new ArrayList<>();

    private boolean delete = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //navigation bar that switches activities.
        BottomNavigationView navBar = (BottomNavigationView) findViewById(R.id.navBar);

        Menu menu = navBar.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

       navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.ic_view_list:
                        Intent intent1 = new Intent(MainActivity.this, ClubList.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_golf_course:

                        break;

                    case R.id.ic_add_box:
                        Intent intent2 = new Intent(MainActivity.this, AddClub.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_share:
                        Intent intent3 = new Intent(MainActivity.this, Share.class);
                        startActivity(intent3);
                        break;
                }

                return false;
            }
        });

        addToList();
        buildRecyclerView();
    }

    //add the selected club from the club list to the bag list.
    public void addToList() {
        loadBagList();
        Intent intent = getIntent();
        String name = intent.getStringExtra(AddClub.NAME);
        String description = intent.getStringExtra(AddClub.DESCRIPTION);
        int image = intent.getIntExtra(AddClub.IMAGE, 0);
        if (bagList.size() == 14) {
            if (name != null & description != null & image != 0) {
                Toast.makeText(MainActivity.this, "Club not added, bag full.", Toast.LENGTH_SHORT).show();
            }
        }
        if (bagList.size() < 14) {
            if (name != null & description != null & image != 0) {
                bagList.add(new Club(name, description, image));
                Toast.makeText(MainActivity.this, "Club added to bag.", Toast.LENGTH_SHORT).show();
            }
        }
        saveBagList();
    }

    //deletes the selected club from the bag list.
    public void deleteClub(int position) {
        bagList.remove(position);
        adapter.notifyItemRemoved(position);
        saveBagList();
    }

    //saves the arraylist as a json string.
    private void saveBagList() {
        SharedPreferences sharedPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bagList);
        editor.putString("Bag List", json);
        editor.apply();
    }

    //loads the json string into the bag list.
    private void loadBagList() {
        SharedPreferences sharedPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Bag List", null);
        Type type = new TypeToken<ArrayList<Club>>() {}.getType();
        bagList = gson.fromJson(json, type);

        if(bagList == null) {
            bagList = new ArrayList<>();
        }
    }

    //builds the recycler view with the contents of thw arraylist.
    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        adapter = new BagListAdapter(bagList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnClubClickListener(new BagListAdapter.onClubClickListener() {
            @Override
            public void onClubClick(int position) {
                ImageView deleteImage = (ImageView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.deleteImage);
                if (delete == false) {
                    deleteImage.setVisibility(View.GONE);
                    delete = true;
                }
                else {
                    deleteImage.setVisibility(View.VISIBLE);
                    delete = false;
                }
            }

            @Override
            public void onDeleteClick(int position) {
                deleteClub(position);
            }
        });
    }
}
