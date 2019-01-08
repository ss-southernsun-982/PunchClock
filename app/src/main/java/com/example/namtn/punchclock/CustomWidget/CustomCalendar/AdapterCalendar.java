package com.example.namtn.punchclock.CustomWidget.CustomCalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.namtn.punchclock.R;

import java.util.List;

public class AdapterCalendar extends BaseAdapter {

    Context mContext;
    List<CalendarState> mListCalendar;
    LayoutInflater inflater;

    public AdapterCalendar(Context mContext, List<CalendarState> mListCalendar) {
        this.mContext = mContext;
        this.mListCalendar = mListCalendar;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mListCalendar.size();
    }

    @Override
    public Object getItem(int position) {
        return mListCalendar.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView mTextViewCalendar;
        RelativeLayout mBackGround;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_calendar_layout, null);
            viewHolder.mTextViewCalendar = convertView.findViewById(R.id.txt_date_of_month);
            viewHolder.mBackGround = convertView.findViewById(R.id.bg_date_of_month);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String s = String.valueOf(mListCalendar.get(position).getDate());
        if (mListCalendar.get(position).isSelected() && !s.equalsIgnoreCase("0")) {
            viewHolder.mBackGround.setBackgroundColor(mContext.getResources().getColor(R.color
                    .colorPrimary));
        } else {
            viewHolder.mBackGround.setBackgroundColor(mContext.getResources().getColor(R.color
                    .colorWhite));
        }
        if (s.equalsIgnoreCase("0")){
            s = "";
        }
        viewHolder.mTextViewCalendar.setText(s);
        return convertView;
    }
}
