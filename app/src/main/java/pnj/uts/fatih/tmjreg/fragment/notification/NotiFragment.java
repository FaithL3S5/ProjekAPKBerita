package pnj.uts.fatih.tmjreg.fragment.notification;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import pnj.uts.fatih.tmjreg.R;
import pnj.uts.fatih.tmjreg.UbahActivity;
import pnj.uts.fatih.tmjreg.adapter.AdapterUser;
import pnj.uts.fatih.tmjreg.basisdata.HelperBasisdata;
import pnj.uts.fatih.tmjreg.model.UserModel;

public class NotiFragment extends Fragment {

    ListView listView;
    AdapterUser adapterUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listView);
        adapterUser = new AdapterUser(getActivity(),R.layout.item_user);
        listView.setAdapter(adapterUser);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                UserModel model = (UserModel) parent.getAdapter().getItem(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage("Hapus akun "+model.getNama()+"?");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase database = new HelperBasisdata(getActivity()).getWritableDatabase();
                        long delete = database.delete("tb_user","id=?", new String[]{""+model.getId()});
                        if(delete != -1){
                            Toast.makeText(getActivity(), "Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            getdata();
                        }
                        else{
                            Toast.makeText(getActivity(), "Tidak Dihapus", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserModel model = (UserModel) parent.getAdapter().getItem(position);
                Toast.makeText(getActivity(), "Ubah Data", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), UbahActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getdata();
    }

    void getdata(){
        adapterUser.clear();
        ArrayList<UserModel> data = new ArrayList<>();
        SQLiteDatabase basisdata = new HelperBasisdata(getActivity()).getReadableDatabase();
        Cursor cursor = basisdata.rawQuery("SELECT * FROM tb_user",null);

        if(cursor.moveToFirst()){
            do{
                UserModel model = new UserModel();
                model.setId(cursor.getInt(0));
                model.setNama(cursor.getString(1));
                model.setMail(cursor.getString(4));
                data.add(model);
            } while (cursor.moveToNext());
        }
        adapterUser.addAll(data);
        adapterUser.notifyDataSetChanged();
    }

}
