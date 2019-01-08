package com.example.namtn.punchclock.Adapter.Attendance;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.namtn.punchclock.R;
import com.example.namtn.punchclock.Retrofit.RetrofitResponse.AttendanceResult.AttendanceData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AttendanceDateAdapter extends BaseAdapter {

    private Context context;
    private List<AttendanceData> listAttendance;
    private LayoutInflater inflater;
    private String TAG = "ParseException";

    public AttendanceDateAdapter(Context context, List<AttendanceData> listAttendance) {
        this.context = context;
        this.listAttendance = listAttendance;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listAttendance.size();
    }

    @Override
    public Object getItem(int position) {
        return listAttendance.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView mTextViewDate, mTextViewIn, mTextViewOut;
        ImageView mImageSatus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_date_attendance, null);
            viewHolder.mTextViewDate = convertView.findViewById(R.id.txt_date_attendance);
            viewHolder.mTextViewIn = convertView.findViewById(R.id.txt_checkIn_attendance);
            viewHolder.mTextViewOut = convertView.findViewById(R.id.txt_checkOut_attendance);
            viewHolder.mImageSatus = convertView.findViewById(R.id.img_status);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        AttendanceData data = listAttendance.get(position);
        String strCheckIn = "", strCheckOut = "";
        if (data.getCheckIn() != null) {
            strCheckIn = data.getCheckIn();
            strCheckIn = strCheckIn.substring(strCheckIn.indexOf(" "), strCheckIn.length());
        } else {
            strCheckIn = "00:00:00";
        }
        if (data.getCheckOut() != null){
            strCheckOut = data.getCheckOut();
            strCheckOut = strCheckOut.substring(strCheckOut.indexOf(" "), strCheckOut.length());
            if (!strCheckOut.equalsIgnoreCase("Out: 00:00:00")){
                viewHolder.mImageSatus.setImageDrawable(context.getResources().getDrawable(R.color.colorGreen));
            }
            Log.d(TAG, "getView: " + strCheckOut);
        } else {
            strCheckOut = "00:00:00";
        }
        Date date = null;
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = dateParser.parse(data.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
// Then convert the Date to a String, formatted as you dd/MM/yyyy
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        viewHolder.mTextViewDate.setText(dateFormatter.format(date));
        viewHolder.mTextViewIn.setText("In: " + strCheckIn);
        viewHolder.mTextViewOut.setText("Out: " + strCheckOut);
        return convertView;
    }
}
