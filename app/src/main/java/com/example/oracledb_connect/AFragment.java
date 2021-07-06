package com.example.oracledb_connect;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AFragment extends Fragment {
    //프래그먼트 테스트
    //기본적인 리스트뷰 구현(뷰홀더사용)

    private ViewHolder viewHolder;
    public static ListView info_listview;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_a, container, false);

        info_listview = (ListView) rootView.findViewById(R.id.popupLV);

        ListView_Adapter adapter = new ListView_Adapter();
        adapter.alldeleteItem();

        adapter.addItem(new InfoListViewItem("1"));
        adapter.addItem(new InfoListViewItem("2"));
        adapter.addItem(new InfoListViewItem("3"));

        adapter.notifyDataSetChanged();
        info_listview.setAdapter(adapter);

        return rootView;
    }

    public class ListView_Adapter extends BaseAdapter {

        private ArrayList<InfoListViewItem> items = new ArrayList<InfoListViewItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            //Count = Size of ArrayList;
            return items.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Context context = parent.getContext();
            if(convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
                viewHolder.setItem(items.get(position), position);
            } else {
                convertView.getTag();
            }
            return convertView;
        }

        public void alldeleteItem() {
            int loation=getCount();
            for(int i=0;i<loation;i++) {
                items.remove(0);
            }
            notifyDataSetChanged();
        }

        public void addItem(InfoListViewItem item){items.add(item);}


    }



    private class ViewHolder {

        private TextView cView;

        public ViewHolder(View itemView) {
            //각 아이템들에 대해 선언
            cView = (TextView)itemView.findViewById(R.id.cardview_text);
        }

        void setItem(InfoListViewItem item, int position) {
            cView.setText(item.getNum1());
        }

    }


}
