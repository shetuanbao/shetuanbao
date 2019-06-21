package com.community.shetuanbao.activity;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.F_GetBitmap;
import com.community.shetuanbao.utils.FontManager;
import com.community.shetuanbao.utils.RefreshableView;
import com.community.shetuanbao.utils.RequestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class huodong_sousuo extends Activity {
	TextView fanhui;
	private List<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	List<String[]> namey = new ArrayList<String[]>();
	List<String[]> timey = new ArrayList<String[]>();
	List<String[]> didiany = new ArrayList<String[]>();
	List<String[]> idy = new ArrayList<String[]>();
	List<String[]> imagey = new ArrayList<String[]>();
	private String[][] all = null;
	private String[][] all_2 = null;
	private String[][] all_3 = null;
	private String[][] all_4 = null;
	private String[][] all_5 = null;
	private String image = null;
	private String name = null;
	private String time = null;
	private String didian = null;
	private int id;
	byte all_image[];
	Bitmap imageData;
	String sousuo = null;
	baseAdapter base;
	ListView listview;
	RelativeLayout nodate;
	RefreshableView refreshableView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.huodong_sousuo_zong);
		nodate = (RelativeLayout) findViewById(R.id.shetuannodate);
		listview = (ListView) findViewById(R.id.huodong_sousuo_listview);
		Intent intent = getIntent();
		sousuo = intent.getStringExtra("sousuo");
		fanhui = (TextView) findViewById(R.id.huodong_sousuo_1);
		fanhui.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		thread_sousuo gg = new thread_sousuo();
		gg.start();
		try {
			gg.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class thread_sousuo extends Thread {
		@Override
		public void run() {
			//todo mo
			//得到活动的各种信息
			try {

				HashMap<String, Object> params = new HashMap<>();
				params.put("activityTitle", sousuo);
				String res = RequestUtils.post("/activities/getActivityDetail", params);
				Log.d("response",res);
				JSONObject jsonObject1 = new JSONObject(res);
				if (jsonObject1.getInt("code") == 200) {
					// 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
					JSONObject users = jsonObject1.getJSONObject("data");
						image = users.getString("activityPicture") + ".png";
						name= users.getString("activityTitle");
						time = users.getString("activityTime");
						didian = users.getString("activityPlace");
						id = users.getInt("activityId");
				}

			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (F_GetBitmap.isEmpty(image)) {
				try {
					HashMap<String, Object> params = new HashMap<>();
					params = new HashMap<>();
					params.put("picture", image);
					String res = RequestUtils.post("/activities/panfindpicture", params);
					JSONObject jsonObject1 = new JSONObject(res);
					if (jsonObject1.getInt("code") == 200) {
						// 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
						JSONArray list1 = (JSONArray) jsonObject1.get("data");
						all_image = new byte[list1.length()];
						for (int j = 0; j < list1.length(); j++) {
							all_image[j] = (byte) list1.getInt(j);
						}
						F_GetBitmap.setInSDBitmap(all_image, image);
						InputStream input = null;
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inSampleSize = 2;
						input = new ByteArrayInputStream(all_image);
						@SuppressWarnings({"rawtypes", "unchecked"})
						SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(input, null, options));
						imageData = (Bitmap) softRef.get();
						Log.d("Moziliang","1234");
//						System.out.println(imageData.);
					} else {
						Looper.prepare();
						Toast.makeText(huodong_sousuo.this, "mo：获取活动图标失败", Toast.LENGTH_LONG).show();
					}

				} catch (JSONException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				Log.d("Moziliang","123");
				imageData = F_GetBitmap.getSDBitmap(image);// 拿到的是BitMap类型的图片数据
				if (F_GetBitmap.bitmap != null && !F_GetBitmap.bitmap.isRecycled()) {
					F_GetBitmap.bitmap = null;
				}
			}
			initList();
		}
	}
	//todo mo


	public void initList() {
//		for (int i = 0; i < name.length; i++) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("time", time);
		map.put("image", imageData);
		map.put("didian", didian);
		map.put("id", id);
		listItem.add(map);
//		}
		initBaseAdapter();
	}
	public void initBaseAdapter() {
		base = new baseAdapter(this);
		listview.setAdapter(base);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				LinearLayout view1 = (LinearLayout) arg1;
				LinearLayout ll = (LinearLayout) view1.getChildAt(1);
				LinearLayout l = (LinearLayout) view1.getChildAt(0);
				LinearLayout namel = (LinearLayout) ll.getChildAt(0);
				LinearLayout timel = (LinearLayout) ll.getChildAt(1);
				LinearLayout placel = (LinearLayout) ll.getChildAt(2);
				TextView text = (TextView) namel.getChildAt(0);
				ImageView im = (ImageView) l.getChildAt(0);
				TextView text2 = (TextView) timel.getChildAt(0);
				TextView text3 = (TextView) placel.getChildAt(0);
				TextView text4 = (TextView) placel.getChildAt(1);
				Bitmap bb = ((BitmapDrawable) im.getDrawable()).getBitmap();
				String mes = text.getText().toString();
				String mes2 = text2.getText().toString();
				String mes3 = text3.getText().toString();
				String mes4 = text4.getText().toString();
				Intent it = new Intent(huodong_sousuo.this, HuoDongDetailActivity.class);
				it.putExtra("activity", "MainActivity");
				it.putExtra("name", mes);
				it.putExtra("time", mes2);
				it.putExtra("place", mes3);
				it.putExtra("id", id);
				it.putExtra("picture", bb);
				startActivity(it);

			}
		});
	}

	private class baseAdapter extends BaseAdapter { // 内部类：适配器

		private LayoutInflater mInflater = null;

		public baseAdapter(Context context) {

			mInflater = LayoutInflater.from(context);

			data.addAll(listItem);
			listItem.clear();
		}
		@Override
		public int getCount() {
			return data.size();// 记录当前列表中有多少条数
		}
		@Override
		public Object getItem(int position) {
			return data.get(position);
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder myViews;
			if (convertView == null) {
				myViews = new ViewHolder();
				//把xml2里面的东西装载进xml1里面
				convertView = mInflater.inflate(R.layout.huodong_souuso_list, null);
				myViews.image = (ImageView) convertView.findViewById(R.id.image_sousuo);
				myViews.name = (TextView) convertView.findViewById(R.id.name_sousuo);
				myViews.time = (TextView) convertView.findViewById(R.id.time_sousuo);
				myViews.didian = (TextView) convertView.findViewById(R.id.didian_sousuo);
				// myViews.id = (TextView) convertView.findViewById(R.id.id);
				convertView.setTag(myViews);
			} else {
				myViews = (ViewHolder) convertView.getTag();
			}
			myViews.image.setImageBitmap((Bitmap) data.get(position).get("image"));
			myViews.name.setText((String) data.get(position).get("name"));
			myViews.time.setText((String) data.get(position).get("time"));
			myViews.didian.setText((String) data.get(position).get("didian"));
			myViews.name.setTypeface(FontManager.tf);
			myViews.time.setTypeface(FontManager.tf);
			myViews.didian.setTypeface(FontManager.tf);
			return convertView;
		}
	}
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		listView.setLayoutParams(params);
	}
	static class ViewHolder {
		private ImageView image;
		private TextView name;
		private TextView time;
		private TextView didian;
		// private TextView id;
	}
}