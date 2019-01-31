package com.aidl.custom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.aidl.custom.R;
import com.test.aidl.bean.Person;

import java.util.List;

public class PersonAdapter extends BaseAdapter {
    private List<Person> dataList;
    private LayoutInflater mInflater;

    public PersonAdapter(Context context, List<Person> dataList){
        this.dataList = dataList;
        mInflater = LayoutInflater.from(context);
    }

    public void setNewData(List<Person> data){
        dataList.clear();
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_person, null);
            viewHolder.nameTv = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Person person = dataList.get(i);
        viewHolder.nameTv.setText(person.getmName()!=null?person.getmName():"");

        return convertView;
    }

    class ViewHolder{
        public TextView nameTv;
    }
}
