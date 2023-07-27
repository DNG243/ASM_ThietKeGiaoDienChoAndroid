package com.example.asm_giaodien.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asm_giaodien.R;
import com.example.asm_giaodien.adapter.KhoanThuChiAdapter;
import com.example.asm_giaodien.dao.KhoanThuChiDAO;
import com.example.asm_giaodien.model.KhoanThuChi;
import com.example.asm_giaodien.model.Loai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class KhoanThuFragment extends Fragment {
    ListView listViewKhoanThu;
    ArrayList<KhoanThuChi> list;
    KhoanThuChiDAO khoanThuChiDAO;
    ArrayList<HashMap<String, Object>> listSpinner;
    KhoanThuChiAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.khoanthu_fragment, container, false);

        listViewKhoanThu = view.findViewById(R.id.listViewKhoanThu);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);

        khoanThuChiDAO = new KhoanThuChiDAO(getContext());
        getData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogThem();
            }
        });

        return view;
    }
    public void getData(){

        list = khoanThuChiDAO.getDSKhoanThuChi("thu");


        adapter = new KhoanThuChiAdapter(list, getContext(), khoanThuChiDAO,getDataSpinner());
        listViewKhoanThu.setAdapter(adapter);
    }

    private void showDialogThem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_themkhoanthu,null);
        Spinner spnLoaiThu = view.findViewById(R.id.spnLoaiThu);
        EditText edtTien = view.findViewById(R.id.edtTien);
        builder.setView(view);

        SimpleAdapter adapter = new SimpleAdapter(
                getContext(),
                getDataSpinner(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spnLoaiThu.setAdapter(adapter);

        builder.setPositiveButton("thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tien = edtTien.getText().toString();
                HashMap<String,Object> selected = (HashMap<String, Object>) spnLoaiThu.getSelectedItem();
                int maloai = (int) selected.get("maloai");
                KhoanThuChi khoanThuChi = new KhoanThuChi(Integer.parseInt(tien), maloai);
                if (khoanThuChiDAO.themMoiKhoanThuChi(khoanThuChi)){
                    Toast.makeText(getContext(), "thêm thành công", Toast.LENGTH_SHORT).show();
                    getData();
                }else{
                    Toast.makeText(getContext(), "thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create() ;
        alertDialog.show();
    }

    private ArrayList<HashMap<String, Object>> getDataSpinner() {
        ArrayList<Loai> listLoai = khoanThuChiDAO.getDSLoai("thu");
        listSpinner = new ArrayList<>();

        for (Loai loai : listLoai) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maloai", loai.getMaloai());
            hashMap.put("tenloai", loai.getTenloai());
            listSpinner.add(hashMap);
        }
        return listSpinner;
    }
}
