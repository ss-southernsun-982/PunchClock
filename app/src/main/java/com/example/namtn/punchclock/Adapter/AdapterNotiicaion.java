package com.example.namtn.punchclock.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.namtn.punchclock.ModelView.NotificationModelVIew;
import com.example.namtn.punchclock.R;

import java.util.List;

public class AdapterNotiicaion extends BaseAdapter {

    private Context context;
    private List<Object> mListObject;
    private static final int HEADER_ITEM = 0;
    private static final int MORE_ITEM = 1;
    private LayoutInflater inflater;
    private TextView mTextViewTitle, mTextViewHeader, mTextViewSubTitle;

    public AdapterNotiicaion(Context context, List<Object> mListNotification) {
        this.context = context;
        this.mListObject = mListNotification;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {
        if (mListObject.get(position) instanceof NotificationModelVIew) {
            return MORE_ITEM;
        } else {
            return HEADER_ITEM;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mListObject.size();
    }

    @Override
    public Object getItem(int position) {
        return mListObject.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            switch (getItemViewType(position)) {
                case HEADER_ITEM:
                    convertView = inflater.inflate(R.layout.row_item_header_list_timeline, null);
                    break;
                case MORE_ITEM:
                    convertView = inflater.inflate(R.layout.row_item_list_timeline, null);
                    break;
            }
        }

        switch (getItemViewType(position)) {
            case HEADER_ITEM:
                convertView = inflater.inflate(R.layout.row_item_header_list_timeline, null);
                mTextViewHeader = convertView.findViewById(R.id.txt_header_list_timeline);

                mTextViewHeader.setText((String) mListObject.get(position));
                break;
            case MORE_ITEM:
                convertView = inflater.inflate(R.layout.row_item_list_timeline, null);
                mTextViewTitle = convertView.findViewById(R.id.txt_title_notification);
                mTextViewHeader = convertView.findViewById(R.id
                        .txt_header_list_timeline);
                mTextViewSubTitle = convertView.findViewById(R.id
                        .txt_sub_title_notification);
                mTextViewTitle.setText(((NotificationModelVIew) mListObject.get(position)).getTitle());
                mTextViewSubTitle.setText(((NotificationModelVIew) mListObject.get(position)).getSunTitle());
                break;
        }

//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//
//            viewHolder.mTextViewTitle = convertView.findViewById(R.id.txt_title_notification);
//            viewHolder.mTextViewHeader = convertView.findViewById(R.id.txt_header_list_timeline);
//            viewHolder.mTextViewSubTitle = convertView.findViewById(R.id
//                    .txt_sub_title_notification);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        NotificationModelVIew notificationModelVIew = mListObject.get(position);
//        viewHolder.mTextViewTitle.setText(notificationModelVIew.getTitle());
//        viewHolder.mTextViewSubTitle.setText(notificationModelVIew.getSunTitle());
        return convertView;
    }
}
