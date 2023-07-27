package com.example.asm_giaodien.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_giaodien.R;
import com.example.asm_giaodien.dao.KhoanThuChiDAO;
import com.example.asm_giaodien.model.KhoanThuChi;

import java.util.ArrayList;
import java.util.HashMap;

public class KhoanThuChiAdapter extends BaseAdapter {
    private ArrayList<KhoanThuChi> list;
    private Context context;
    private KhoanThuChiDAO thuChiDAO;
    private ArrayList<HashMap<String,Object>> listSpinner;

    public KhoanThuChiAdapter(ArrayList<KhoanThuChi> list, Context context, KhoanThuChiDAO thuChiDAO,ArrayList<HashMap<String,Object>> listSpinner) {
        this.list = list;
        this.context = context;
        this.thuChiDAO = thuChiDAO;
        this.listSpinner = listSpinner;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public static class ViewOfItem{
        TextView txtTen,txtTien;
        ImageView ivSua,ivXoa;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        ViewOfItem  viewOfItem;

        if(view==null){
            view = inflater.inflate(R.layout.item_khoanthu,viewGroup,false);
            viewOfItem = new ViewOfItem();
            viewOfItem.txtTen = view.findViewById(R.id.txtTen);
            viewOfItem.txtTien = view.findViewById(R.id.txtTien);
            viewOfItem.ivSua = view.findViewById(R.id.ivSua);
            viewOfItem.ivXoa = view.findViewById(R.id.ivXoa);
            view.setTag(viewOfItem);
        }
        else {
            viewOfItem = (ViewOfItem) view.getTag();
        }
        viewOfItem.txtTen.setText(list.get(i).getTenloai());
        viewOfItem.txtTien.setText(String.valueOf(list.get(i).getTien()));

        viewOfItem.ivSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSua(list.get(i));
            }
        });

        viewOfItem.ivXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int makhoan = list.get(i).getMakhoan();
                if(thuChiDAO.xoaKhoanThuChi(makhoan)){
                    Toast.makeText(context, "xoá thành công", Toast.LENGTH_SHORT).show();
                    reloadData();
                }
                else{
                    Toast.makeText(context, "thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private  void  showDialogSua(KhoanThuChi khoanThuChi){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view =  inflater.inflate(R.layout.dialog_suakhoanthu,null);
        Spinner spnLoaiThu = view.findViewById(R.id.spnLoaiThu);
        EditText edtTien = view.findViewById(R.id.edtTien);
        builder.setView(view);

        SimpleAdapter adapter =new SimpleAdapter(
                context,
                listSpinner,
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spnLoaiThu.setAdapter(adapter);

        edtTien.setText(String.valueOf(khoanThuChi.getTien()));

        int index = 0;
        int vitri= -1;
        for(HashMap<String,Object> item:listSpinner){
            if((int)item.get("maloai")==khoanThuChi.getMaloai())
                vitri=index;


            index++;
        }
        spnLoaiThu.setSelection(vitri);

        builder.setPositiveButton("cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tien = edtTien.getText().toString();
                HashMap<String,Object> selected = (HashMap<String, Object>) spnLoaiThu.getSelectedItem();
                int maloai = (int) selected.get("maloai");
                khoanThuChi.setTien(Integer.parseInt(tien));
                khoanThuChi.setMaloai(maloai);
                if(thuChiDAO.capNhatKhoanThuChi(khoanThuChi)){
                    Toast.makeText(context, "cập nhật thành công", Toast.LENGTH_SHORT).show();
                    reloadData();
                }
                Toast.makeText(context, "thất bại", Toast.LENGTH_SHORT).show();
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

    private void reloadData(){
        list.clear();
        list = thuChiDAO.getDSKhoanThuChi("thu");
        notifyDataSetChanged();
    }
}
