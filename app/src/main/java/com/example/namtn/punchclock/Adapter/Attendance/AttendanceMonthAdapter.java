package com.example.namtn.punchclock.Adapter.Attendance;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.namtn.punchclock.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AttendanceMonthAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<AttendanceMonthModel> arrayList;
    private Context context;
    private LayoutInflater inflater;
    private Calendar calendar;
    private int month;
    private String TAG = "DDDDDD";

    public AttendanceMonthAdapter(ArrayList<AttendanceMonthModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH) + 1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .item_month_attendance, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        AttendanceMonthModel playList = arrayList.get(i);
        viewHolder.mTextViewMonth.setText(playList.getMonth());
        if (playList.getId() == month) {
            viewHolder.mRelativeBackgroundMonth.setBackground(context.getResources().getDrawable
                    (R.drawable.circle_background_blue));
            viewHolder.mTextViewMonth.setTextColor(context.getResources().getColor(R.color
                    .colorPrimary));
        } else {
            viewHolder.mRelativeBackgroundMonth.setBackground(context.getResources().getDrawable
                    (R.drawable.circle_background_white));
            viewHolder.mTextViewMonth.setTextColor(context.getResources().getColor(R.color
                    .colorWhite));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder  // Implement 2 sự kiện onClick và onLongClick
{
    TextView mTextViewMonth;
    RelativeLayout mRelativeBackgroundMonth, mRelativeRoot;
    private ItemClick itemClickListener; // Khai báo interface

    public ViewHolder(View itemView) {
        super(itemView);
        mTextViewMonth = itemView.findViewById(R.id.txt_month_list_attendance);
        mRelativeBackgroundMonth = itemView.findViewById(R.id.layout_background_list_month);
        mRelativeRoot = itemView.findViewById(R.id.root_layout_item_month);
    }

    //Tạo setter cho biến itemClickListenenr
    public void setItemClickListener(ItemClick itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}