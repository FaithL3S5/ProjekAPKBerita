package pnj.uts.fatih.tmjreg.basisdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HelperBasisdata extends SQLiteOpenHelper {

    public static String _NAMA_BASISDATA = "db_user";
    public static int _VERSI_BASISDATA = 1;
    public static String _BUAT_TABEL = "create table tb_user (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nama TEXT, dob TEXT, bt TEXT, mail TEXT, pass TEXT)";

    public HelperBasisdata(@Nullable Context context) {
        super(context, _NAMA_BASISDATA, null, _VERSI_BASISDATA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(_BUAT_TABEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
