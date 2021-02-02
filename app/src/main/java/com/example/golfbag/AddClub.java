/*
 * This activity allows the user to add a new club to the club list.
 *
 * @author  Robbie Flockhart - 40343879
 */
package com.example.golfbag;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class AddClub extends AppCompatActivity {
    private String name;
    private String description;
    private int image;
    private Button button;

    public static final String NAME = "com.example.golfbag.NAME";
    public static final String DESCRIPTION = "com.example.golfbag.DESCRIPTION";
    public static final String IMAGE = "com.example.golfbag.IMAGE";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_club);

        //navigation bar that switches activities.
        BottomNavigationView navBar = (BottomNavigationView) findViewById(R.id.navBar);

        Menu menu = navBar.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.ic_view_list:
                        Intent intent1 = new Intent(AddClub.this, ClubList.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_golf_course:
                        Intent intent2 = new Intent(AddClub.this, MainActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_add_box:

                        break;

                    case R.id.ic_share:
                        Intent intent3 = new Intent(AddClub.this, Share.class);
                        startActivity(intent3);
                        break;
                }

                return false;
            }
        });

        //declares the input boxes and radio buttons.
        final TextInputEditText nameInput = (TextInputEditText) findViewById(R.id.textInputEditText1);
        final TextInputEditText descriptionInput = (TextInputEditText) findViewById(R.id.textInputEditText2);
        final RadioButton radioButtonInput1 = (RadioButton) findViewById(R.id.radioButton1);
        final RadioButton radioButtonInput2 = (RadioButton) findViewById(R.id.radioButton2);
        final RadioButton radioButtonInput3 = (RadioButton) findViewById(R.id.radioButton3);


        //on click listener for the add button and sets the contents to the variables above.
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameInput.getText().toString();
                description = descriptionInput.getText().toString();

                if (radioButtonInput1.isChecked()) {
                    image = R.drawable.ic_golf_course_red_24dp;
                }
                else if (radioButtonInput2.isChecked()) {
                    image = R.drawable.ic_golf_course_blue_24dp;
                }
                else if (radioButtonInput3.isChecked()) {
                    image = R.drawable.ic_golf_course_green_24dp;
                }
                else {
                    image = R.drawable.ic_golf_course_black_24dp;
                }

                //sends the club description to the club list activity.
                Intent intent = new Intent(AddClub.this, ClubList.class);
                intent.putExtra(NAME, name);
                intent.putExtra(DESCRIPTION, description);
                intent.putExtra(IMAGE, image);
                startActivity(intent);
            }
        });
    }
}
