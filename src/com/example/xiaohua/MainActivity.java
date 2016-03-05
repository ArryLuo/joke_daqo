package com.example.xiaohua;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private ListView listView;
	private Handler handler = new Handler();
	private int count = 1;
	private Button up, net;
	private MyAdapter adapter;
	private TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		up = (Button) findViewById(R.id.up);
		net = (Button) findViewById(R.id.net);
		listView = (ListView) findViewById(R.id.listview);
		adapter = new MyAdapter(this);
		textView=(TextView) findViewById(R.id.ye);
		new HttpXThread(listView, adapter, handler, count,this).start();
		up.setOnClickListener(this);
		net.setOnClickListener(this);
		Toast.makeText(this, "亲，请记得打开网络哟!", 1).show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.up:
			count -= 1;
			if (count == 0) {
				count = 1;
				Toast.makeText(this, "已经是第一页了", 0).show();
				break;
			}
			new HttpXThread(listView, adapter, handler, count,this).start();
			textView.setText(count+"");
			
			
			break;
		case R.id.net:
			count += 1;
			new HttpXThread(listView, adapter, handler, count,this).start();
			textView.setText(count+"");
			break;
		default:
			break;
		}
	}

}
