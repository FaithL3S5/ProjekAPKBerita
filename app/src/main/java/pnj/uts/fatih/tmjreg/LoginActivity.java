package pnj.uts.fatih.tmjreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pnj.uts.fatih.tmjreg.basisdata.HelperBasisdata;


public class LoginActivity extends AppCompatActivity {
    EditText edtEmail,edtPassword;
    Button actionLogin;
    TextView txtRegister;
    SharedPreferences sharedPreferences;
    SharedPreferences prefs = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        prefs = getSharedPreferences("IndroidFatih", MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("IndroidFatih", MODE_PRIVATE);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        actionLogin = findViewById(R.id.actionLogin);
        txtRegister = findViewById(R.id.txtRegister);

        actionLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtEmail.getText().toString().length() > 0 && edtPassword.getText().toString().length()>0) {
                    SQLiteDatabase basisdata = new HelperBasisdata(LoginActivity.this).getReadableDatabase();
                    Cursor cursor = basisdata.rawQuery("SELECT * FROM tb_user WHERE mail=? and pass=?", new String[]{edtEmail.getText().toString(),edtPassword.getText().toString()});
                    if(cursor.getCount()>0){
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLogin", true);
                        cursor.moveToFirst();
                        editor.putString("nama","Pendataan Alumni");
                        editor.putInt("id",cursor.getInt(0));
                        editor.commit();
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, "Mohon Maaf Email dan Password Salah", Toast.LENGTH_SHORT).show();
                    }

                    cursor.close();
                    basisdata.close();

                }
                else {
                    Toast.makeText(LoginActivity.this, "Mohon Lengkapi Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.getBoolean("firstrun", true)) {
            SQLiteDatabase basisdata = new HelperBasisdata(LoginActivity.this).getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("nama","Muhammad Fatih Fahroji");
            contentValues.put("dob","1807421008");
            contentValues.put("bt","TMJ5 REG.");
            contentValues.put("mail","fatihfahroji10@gmail.com");
            contentValues.put("pass","1234");
            long insert = basisdata.insert("tb_user",null,contentValues);
            basisdata.close();
            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }
}
