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
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_giaodien.R;
import com.example.asm_giaodien.dao.KhoanThuChiDAO;
import com.example.asm_giaodien.model.Loai;

import java.util.ArrayList;

public class LoaiChiAdapter extends BaseAdapter {
    private ArrayList<Loai> list;
    private Context context;
    private KhoanThuChiDAO khoanThuChiDAO;

    public LoaiChiAdapter(ArrayList<Loai> list, Context context, KhoanThuChiDAO khoanThuChiDAO) {
        this.list = list;
        this.context = context;
        this.khoanThuChiDAO = khoanThuChiDAO;
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
        TextView txtTen;
        ImageView ivSua,ivXoa;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        ViewOfItem viewOfItem;
        if(view==null){
            viewOfItem = new ViewOfItem();
            view = inflater.inflate(R.layout.item_loaichi,viewGroup,false);
            viewOfItem.txtTen = view.findViewById(R.id.txtTen);
            viewOfItem.ivSua = view.findViewById(R.id.ivSua);
            viewOfItem.ivXoa = view.findViewById(R.id.ivXoa);
            view.setTag(viewOfItem);
        }else{
            viewOfItem = (ViewOfItem) view.getTag();
        }

        viewOfItem.txtTen.setText(list.get(i).getTenloai());

        viewOfItem.ivSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSuaLoaiThu(list.get(i));
            }
        });

        viewOfItem.ivXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idCanXoa = list.get(i).getMaloai();
                if(khoanThuChiDAO.xoaLoaiChi(idCanXoa)){
                    Toast.makeText(context, "xoá thành công", Toast.LENGTH_SHORT).show();
                    reloadData();
                }
                else{
                    Toast.makeText(context, "xoá thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void showDialogSuaLoaiThu(Loai loai){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sualoaichi,null);
        EditText edtInput = view.findViewById(R.id.edtInput);
        builder.setView(view);

        edtInput.setText(loai.getTenloai());

        builder.setPositiveButton("cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tenloai = edtInput.getText().toString();
                loai.setTenloai(tenloai);
                if(khoanThuChiDAO.capnhatLoaiChi(loai)){
                    Toast.makeText(context, "cập nhật thành công", Toast.LENGTH_SHORT).show();
                    reloadData();
                }
                else {
                    Toast.makeText(context, "cập nhật không thành công", Toast.LENGTH_SHORT).show();
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

    private void reloadData(){
        list.clear();
        list = khoanThuChiDAO.getDSLoaiChi("chi");
        notifyDataSetChanged();
    }
}
