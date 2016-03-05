package com.example.xiaohua;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class HttpXThread extends Thread {
	// 配置您申请的KEY
	public static final String APPKEY = "5f2baa2925357b307099fc62d6e449df";
	private static ListView listView;
	private static MyAdapter adapter;
	private static Handler handler;
	private static int count=1;
	private static Context context;
	public HttpXThread(ListView listView, MyAdapter adapter, Handler handler,int count,Context context) {
		super();
		this.listView = listView;
		this.adapter = adapter;
		this.handler = handler;
		this.count=count;
		this.context=context;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		getRequest1();
	}

	// 1.按更新时间查询笑话
	public static void getRequest1() {
		String result = null;
		String url = "http://japi.juhe.cn/joke/content/list.from";// 请求接口地址
		Map params = new HashMap();// 请求参数
		// params.put("sort","");//类型，desc:指定时间之前发布的，asc:指定时间之后发布的
		 params.put("page",count);//当前页数,默认1
		params.put("pagesize", 20);// 每次返回条数,默认1,最大20
		long time=System.currentTimeMillis();
		System.out.println(time+"时间");
		String s_time=String.valueOf(time);
		String str_time=s_time.substring(0, 10);
		System.out.println(str_time+">>>>>>>>>>");
		 params.put("time",str_time);//时间戳（10位），如：1418816972
		params.put("key", APPKEY);// 您申请的key
		result=net(url, params);
	}

	public static String net(String strUrl, Map params) {
		strUrl = strUrl + "?" + urlencode(params);
		try {
			URL url = new URL(strUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setReadTimeout(5000);
			con.setRequestMethod("GET");
			if (con.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String len;
				while ((len = br.readLine()) != null) {
					sb.append(len);
				}
				Log.v("内容", sb.toString());
				final List<Data>list=getJson(sb.toString());
				System.out.println(list.toString()+"???????");
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						adapter.getlist(list);
						listView.setAdapter(adapter);
					}
				});
				return sb.toString();
			} else {
				Log.v("CON", "请查询网络连接");
				Toast.makeText(context, "请检查网络", 0).show();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 将map型转为请求参数型
	public static String urlencode(Map<String, Object> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=")
						.append(URLEncoder.encode(i.getValue() + "", "UTF-8"))
						.append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	//解析json
	public static List<Data>getJson(String json){
		List<Data>list=new ArrayList<>();
		try {
			System.out.println("1");
			JSONObject object=new JSONObject(json);
			System.out.println("2");
			JSONObject jsonObject=object.getJSONObject("result");
			JSONArray array=jsonObject.getJSONArray("data");
			System.out.println("3");
			Data datas=null;
			for (int i = 0; i < array.length(); i++) {
				JSONObject data= array.getJSONObject(i);
				String content=data.getString("content");
				String updatetime=data.getString("updatetime");
				datas=new Data(content, updatetime);
				list.add(datas);
			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
