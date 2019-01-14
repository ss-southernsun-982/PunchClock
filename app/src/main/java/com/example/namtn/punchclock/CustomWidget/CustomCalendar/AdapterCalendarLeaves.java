package com.example.namtn.punchclock.CustomWidget.CustomCalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.namtn.punchclock.R;
import com.example.namtn.punchclock.Retrofit.RetrofitResponse.LeavesResult.LeavesData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterCalendarLeaves extends BaseAdapter {

    Context mContext;
    List<CalendarSateLeaves> mListCalendar;
    List<LeavesData> mListLeaves;
    LayoutInflater inflater;
    SimpleDateFormat dateFormat;
    Calendar mCalendar;

    public AdapterCalendarLeaves(Context mContext, List<CalendarSateLeaves> mListCalendar,
                                 List<LeavesData> mListLeaves) {
        this.mContext = mContext;
        this.mListCalendar = mListCalendar;
        this.mListLeaves = mListLeaves;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dateFormat = new SimpleDateFormat("d/M");
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
        LinearLayout mBackGround, mLinearLayoutAddLeaves;
        CircleImageView mCircleImageView, mCircleImageView2;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_calendar_leaves_layout, null);
            viewHolder.mTextViewCalendar = convertView.findViewById(R.id.txt_date_of_month);
            viewHolder.mBackGround = convertView.findViewById(R.id.bg_date_of_month);
            viewHolder.mLinearLayoutAddLeaves = convertView.findViewById(R.id.view_add_leaves);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mListCalendar.get(position).isDateOfMonth()) {
            viewHolder.mBackGround.setBackgroundColor(mContext.getResources().getColor(R.color
                    .colorWhite));
            viewHolder.mCircleImageView = new CircleImageView(mContext);
            viewHolder.mCircleImageView2 = new CircleImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup
                    .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            viewHolder.mLinearLayoutAddLeaves.setLayoutParams(params);
            LinearLayout.LayoutParams paramsImage1 = new LinearLayout.LayoutParams(12, 12);
            paramsImage1.setMargins(0, 0, 3, 0);
            viewHolder.mCircleImageView.setLayoutParams(paramsImage1);
            viewHolder.mCircleImageView2.setLayoutParams(new LinearLayout.LayoutParams(12, 12));
            viewHolder.mCircleImageView.setImageResource(R.color.colorRed);
            viewHolder.mCircleImageView2.setImageResource(R.color.colorGreen);
            viewHolder.mLinearLayoutAddLeaves.addView(viewHolder.mCircleImageView);
            viewHolder.mLinearLayoutAddLeaves.addView(viewHolder.mCircleImageView2);
        } else {
            viewHolder.mTextViewCalendar.setTextColor(mContext.getResources().getColor(R.color
                    .coloGray));
        }
        mCalendar = Calendar.getInstance();
        CalendarSateLeaves sateLeaves = mListCalendar.get(position);
        String calendarDay = sateLeaves.getDate() + "/" + (sateLeaves.getMonth() + 1) + "/" +
                sateLeaves.getYear();
        String toDay = dateFormat.format(mCalendar.get(Calendar.DATE)) + "/" + mCalendar.get(Calendar.YEAR);
        if (calendarDay.equals(toDay)) {
            viewHolder.mBackGround.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
            viewHolder.mTextViewCalendar.setTextColor(mContext.getResources().getColor(R.color
                    .colorWhite));
        }
        String s = String.valueOf(sateLeaves.getDate());
        viewHolder.mTextViewCalendar.setText(s);
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        if (mListCalendar.get(position).isDateOfMonth()) {
            return true;
        } else {
            return false;
        }
    }
}
