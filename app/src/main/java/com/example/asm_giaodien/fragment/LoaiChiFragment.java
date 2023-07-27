package com.example.asm_giaodien.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asm_giaodien.R;
import com.example.asm_giaodien.adapter.LoaiChiAdapter;
import com.example.asm_giaodien.adapter.LoaiThuAdapter;
import com.example.asm_giaodien.dao.KhoanThuChiDAO;
import com.example.asm_giaodien.model.Loai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LoaiChiFragment extends Fragment {

    ListView listViewLoaiThu;
    FloatingActionButton floatAdd;
    LoaiChiAdapter adapter;
    ArrayList<Loai> list;
    KhoanThuChiDAO khoanThuChiDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loaichi_fragment,container,false);

        listViewLoaiThu = view.findViewById(R.id.listViewLoaiChi);
        floatAdd = view.findViewById(R.id.floatAdd);

        khoanThuChiDAO = new KhoanThuChiDAO(getContext());
        loadData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogThem();
            }
        });

        return view;
    }

    private void loadData(){
        list = khoanThuChiDAO.getDSLoai("chi");

        adapter = new LoaiChiAdapter(list,getContext(),khoanThuChiDAO);
        listViewLoaiThu.setAdapter(adapter);
    }

    private  void showDialogThem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view= inflater.inflate(R.layout.dialog_themloaichi,null);
        EditText edtInput = view.findViewById(R.id.edtInput);
        builder.setView(view);
        builder.setPositiveButton("thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tenloai = edtInput.getText().toString();
                Loai loaiThem = new Loai(tenloai,"chi");
                if(khoanThuChiDAO.themMoiLoaiChi(loaiThem)){
                    Toast.makeText(getContext(), "thêm thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }
                else{
                    Toast.makeText(getContext(), "không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
