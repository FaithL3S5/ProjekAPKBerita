package pnj.uts.fatih.tmjreg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import pnj.uts.fatih.tmjreg.basisdata.HelperBasisdata;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtBod,edtJamLahir,edtEmail,edtPassword,edtNama;
    Button actionSimpan;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtNama = findViewById(R.id.edtNama);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        actionSimpan = findViewById(R.id.actionSimpan);
        edtBod = findViewById(R.id.edtBod);
        edtJamLahir =findViewById(R.id.edtJamLahir);

        edtBod.setOnClickListener(this);
        edtJamLahir.setOnClickListener(this);
        actionSimpan.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edtBod:
                //event click BOD
                new DatePickerDialog(this,dateSetListener,calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.edtJamLahir:
                //event click Jam Lahir
                new TimePickerDialog(this, timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),true).show();
                break;
            case R.id.actionSimpan:
                if (edtNama.getText().toString().length()>0 && edtEmail.getText().toString().length()>0
                        && edtBod.getText().toString().length()>0 && edtJamLahir.getText().toString().length()>0
                        && edtPassword.getText().toString().length()>0){
                    simpan();
                }
                else{
                    Toast.makeText(this, "Harap Isi Semua Data", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    void simpan(){
        SQLiteDatabase basisdata = new HelperBasisdata(this).getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("nama",edtNama.getText().toString());
        contentValues.put("dob",edtBod.getText().toString());
        contentValues.put("bt",edtJamLahir.getText().toString());
        contentValues.put("mail",edtEmail.getText().toString());
        contentValues.put("pass",edtPassword.getText().toString());

        long insert = basisdata.insert("tb_user",null,contentValues);
        if(insert != -1){
            Toast.makeText(this, "Registrasi Sukses", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this, "Registrasi Gagal", Toast.LENGTH_SHORT).show();
        }
        basisdata.close();

    }


    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
            edtBod.setText(dateFormat.format(calendar.getTime()));

        }
    };

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            edtJamLahir.setText(timeFormat.format(calendar.getTime()));
        }
    };
}