package com.example.namtn.punchclock.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.namtn.punchclock.Model.MainModel.MenuModel;
import com.example.namtn.punchclock.R;

import java.util.List;

public class MenuMainAdapter extends BaseAdapter {

    private Context context;
    private List<MenuModel> listMenu;
    private LayoutInflater inflater;

    public MenuMainAdapter(Context context, List<MenuModel> listMenu) {
        this.context = context;
        this.listMenu = listMenu;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listMenu.size();
    }

    @Override
    public Object getItem(int position) {
        return listMenu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        ImageView mImageViewItemMenu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_menu_main, null);
            viewHolder.mImageViewItemMenu = convertView.findViewById(R.id.img_item_menu_main);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MenuModel menuModel = listMenu.get(position);
        viewHolder.mImageViewItemMenu.setImageResource(menuModel.getIcon());
        return convertView;
    }
}
