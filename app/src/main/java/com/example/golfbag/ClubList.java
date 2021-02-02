/*
 * This activity displays the club list in a recycler view.
 *
 * @author  Robbie Flockhart - 40343879
 */
package com.example.golfbag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ClubList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ClubListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Club> clubList;

    private boolean delete = true;

    public static final String BAGLIST = "com.example.golfbag.BAGLIST";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_list);

        //navigation bar that switches activities.
        BottomNavigationView navBar = (BottomNavigationView) findViewById(R.id.navBar);

        Menu menu = navBar.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.ic_view_list:

                        break;

                    case R.id.ic_golf_course:

                        Intent intent1 = new Intent(ClubList.this, MainActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_add_box:
                        Intent intent2 = new Intent(ClubList.this, AddClub.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_share:
                        Intent intent3 = new Intent(ClubList.this, Share.class);
                        startActivity(intent3);
                        break;
                }

                return false;
            }
        });

        addToList();
        buildRecyclerView();
    }

    //adds the club from the add club activity.
    public void addToList() {
        loadClubList();

        Intent intent = getIntent();
        String name = intent.getStringExtra(AddClub.NAME);
        String description = intent.getStringExtra(AddClub.DESCRIPTION);
        int image = intent.getIntExtra(AddClub.IMAGE, 0);

        if (name != null & description != null & image != 0) {
            clubList.add(new Club(name, description, image));
        }

        saveClubList();
    }

    //deletes the club from the club list.
    public void deleteClub(int position) {
        clubList.remove(position);
        adapter.notifyItemRemoved(position);
        saveClubList();
    }

    //adds the club selected to the bag list.
    public void addToBagList(int position) {
        Club club = clubList.get(position);
        String name = club.getName();
        String description = club.getDescription();
        int image = club.getImage();
        Intent intent = new Intent(ClubList.this, MainActivity.class);
        intent.putExtra(AddClub.NAME, name);
        intent.putExtra(AddClub.DESCRIPTION, description);
        intent.putExtra(AddClub.IMAGE, image);
        startActivity(intent);
    }

    //save the arraylist as a json string.
    private void saveClubList() {
        SharedPreferences sharedPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(clubList);
        editor.putString("Club List", json);
        editor.apply();
    }

    //loads the json string into the club list.
    private void loadClubList() {
        SharedPreferences sharedPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Club List", null);
        Type type = new TypeToken<ArrayList<Club>>() {}.getType();
        clubList = gson.fromJson(json, type);

        if(clubList == null) {
            clubList = new ArrayList<>();
        }
    }

    //builds the recycler view with the contents of the arraylist.
    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ClubListAdapter(clubList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnClubClickListener(new ClubListAdapter.onClubClickListener() {
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

            @Override
            public void onAddClick(int position) {
                addToBagList(position);

                Toast.makeText(ClubList.this, "Added to Bag", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
