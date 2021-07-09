package pnj.uts.fatih.tmjreg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import pnj.uts.fatih.tmjreg.fragment.home.HomeFragment;
import pnj.uts.fatih.tmjreg.fragment.homefix.HomeFragfix;
import pnj.uts.fatih.tmjreg.fragment.notification.NotiFragment;
import pnj.uts.fatih.tmjreg.fragment.profile.FragmentProfile;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button actionHome,actionProfile,actionNotification;
    HomeFragfix homeFragment = new HomeFragfix();
    HomeFragment notiFragment = new HomeFragment();
    FragmentProfile fragmentProfile = new FragmentProfile();
    NotiFragment dataFragment = new NotiFragment();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences("IndroidFatih", MODE_PRIVATE);
        actionHome = findViewById(R.id.actionHome);
        actionProfile = findViewById(R.id.actionProfile);
        actionNotification = findViewById(R.id.actionNotification);
        setTitle(sharedPreferences.getString("nama",""));

        actionHome.setOnClickListener(this);
        actionProfile.setOnClickListener(this);
        actionNotification.setOnClickListener(this);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, notiFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (v.getId()){
            case R.id.actionHome:
                Intent explicit = new Intent(HomeActivity.this, WeatherActivity.class);
                startActivity(explicit);
                break;
            case R.id.actionProfile:
                fragmentTransaction.replace(R.id.container, fragmentProfile);
                fragmentTransaction.commit();
                break;
            case R.id.actionNotification:
                fragmentTransaction.replace(R.id.container, notiFragment);
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionMenu1:
                Toast.makeText(this,"Silahkan Masukan Data", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
            case R.id.actionMenu2:
                //Toast.makeText(this,"Menu 2 dipilih", Toast.LENGTH_SHORT).show();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, dataFragment);
                fragmentTransaction.commit();
                break;
            case R.id.actionMenu3:
                SharedPreferences sharedPreference = getSharedPreferences("IndroidFatih", Context.MODE_PRIVATE);
                SharedPreferences.Editor edits = sharedPreference.edit();
                edits.clear();
                edits.commit();
                Toast.makeText(this,"Telah Logout", Toast.LENGTH_SHORT).show();

                Intent intents = new Intent(this, LoginActivity.class);
                startActivity(intents);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}