package com.example.xiaohua;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
	private List<Data>list;
	private LayoutInflater inflater;
	public MyAdapter(Context context){
		inflater=LayoutInflater.from(context);
	}
	public void getlist(List<Data>list){
		this.list=list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Data data=list.get(position);
		View view=inflater.inflate(R.layout.item, null);
		TextView content=(TextView) view.findViewById(R.id.content);
		TextView time=(TextView) view.findViewById(R.id.time);
		content.setText(data.getContent());
		time.setText(data.getUpdatetime());
		return view;
	}

}
