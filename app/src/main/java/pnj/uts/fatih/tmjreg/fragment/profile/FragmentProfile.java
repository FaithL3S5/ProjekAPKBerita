package pnj.uts.fatih.tmjreg.fragment.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import pnj.uts.fatih.tmjreg.LoginActivity;
import pnj.uts.fatih.tmjreg.R;
import pnj.uts.fatih.tmjreg.UbahActivity;
import pnj.uts.fatih.tmjreg.basisdata.HelperBasisdata;

public class FragmentProfile extends Fragment {
    TextView edtBod,edtJamLahir,edtEmail,edtPassword,edtNama;
    Button actionUbah;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("IndroidFatih", Context.MODE_PRIVATE);
        edtBod = view.findViewById(R.id.edtBod);
        edtJamLahir = view.findViewById(R.id.edtJamLahir);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtNama = view.findViewById(R.id.edtNama);
        actionUbah = view.findViewById(R.id.actionUbah);

        SQLiteDatabase basisdata = new HelperBasisdata(getActivity()).getReadableDatabase();
        Cursor cursor = basisdata.rawQuery("SELECT * FROM tb_user WHERE id=?", new String[]{""+sharedPreferences.getInt("id",0)});
        cursor.moveToFirst();
        edtBod.setText(cursor.getString(2));
        edtJamLahir.setText(cursor.getString(3));
        edtEmail.setText(cursor.getString(4));
        edtNama.setText(cursor.getString(1));

        cursor.close();
        basisdata.close();

        actionUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreference = getActivity().getSharedPreferences("IndroidFatih", Context.MODE_PRIVATE);
                SharedPreferences.Editor edits = sharedPreference.edit();
                edits.clear();
                edits.commit();
                Toast.makeText(getActivity(),"Telah Logout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }
}
