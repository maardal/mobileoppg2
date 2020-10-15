package no.maardal.fantapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    FantService fantService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fantService = fantService.getInstance();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            setUserInfo();
        });
    }

    private void setUserInfo() {
        User user = fantService.getUser();
        if (user != null) {
            TextView uid = findViewById(R.id.uid);
            TextView firstName = findViewById(R.id.last_name);
            TextView lastName = findViewById(R.id.first_name);

            uid.setText(user.getUserid());
            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
        }
    }
}
