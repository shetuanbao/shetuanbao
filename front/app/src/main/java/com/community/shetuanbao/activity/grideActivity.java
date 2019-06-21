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
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.F_GetBitmap;
import com.community.shetuanbao.utils.RequestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class grideActivity extends Activity{
	private GridView gridView;
	private List<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	byte all_image[][];
	Bitmap imageData[];
	List<String[]> imagey=new ArrayList<String[]>();
	String all[][]=null;
	String image[]=null;
	String name[]=null;
	baseAdapter base;
	int num;
	Map<String,Object> params;
	int id;
	@Override
	public void onCreate(Bundle savedInstanceState){ 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.gride);
        Intent intent = getIntent();
        //得到活动的相册
        id = intent.getIntExtra("id",0);
        thread_get gg=new thread_get();
        gg.start();
        try{
        	gg.join();
        }catch(Exception e){
        	e.printStackTrace();
        }
	}
	private class thread_get extends Thread{
		@Override
		public void run(){
			try{
				params=new HashMap<>();
				params.put("activityId",id);
				String res=RequestUtils.post("/activityPicture/yangGetAlbum",params);
				JSONObject jsonObject1 = new JSONObject(res);
				if (jsonObject1.getInt("code") == 200) {
					// 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
					JSONArray list1 = (JSONArray) jsonObject1.get("data");
					num = list1.length();
					image = new String[num];
					name = new String[num];
					imageData = new Bitmap[num];
					all_image = new byte[num][];
					for (int i = 0; i < num; i++) {
						image[i] = list1.getJSONObject(i).getString("pictureId")+".png";
						name[i] = "图片" + (i + 1) + "";
					}
					Thread_pic thread_pic = new Thread_pic();
					thread_pic.start();
					try {
						thread_pic.join();
					} catch (Exception e) {
						e.printStackTrace();
					}
					initList();
				}
				else {
						Looper.prepare();
						Toast.makeText(grideActivity.this, "获取活动相册失败", Toast.LENGTH_LONG).show();
					}


			}catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			}
		}
	public class Thread_pic extends Thread{
		@Override
		public void run(){
			try {
				for(int i=0;i<all_image.length;i++) {
					if (F_GetBitmap.isEmpty(image[i])) {
						Log.d("qwerty","走到这里");
						params = new HashMap<>();
						params.put("tubiao", image[i]);
						String res1 = RequestUtils.post("/community/panfindtubiao", params);
						try {
							JSONObject jsonObject1 = new JSONObject(res1);
							if (jsonObject1.getInt("code") == 200) {
								// 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
								JSONArray list1 = (JSONArray) jsonObject1.get("data");
								all_image[i] = new byte[list1.length()];
								for (int j = 0; j < list1.length(); j++) {
									all_image[i][j] = (byte) list1.getInt(j);
								}
								F_GetBitmap.setInSDBitmap(all_image[i], image[i]);
								InputStream input = null;
								BitmapFactory.Options options = new BitmapFactory.Options();
								options.inSampleSize = 1;
								input = new ByteArrayInputStream(all_image[i]);
								@SuppressWarnings({ "rawtypes", "unchecked" })
								SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(input, null, options));
								imageData[i] = (Bitmap) softRef.get();
								Log.d("bitmaptest","压缩前图片的大小"+ (imageData[i].getByteCount())

										+ "M宽度为"+ imageData[i].getWidth() + "高度为"+ imageData[i].getHeight());
								System.out.println(imageData.length);
							} else {
								Looper.prepare();
								Toast.makeText(grideActivity.this, "获取社团相册失败", Toast.LENGTH_LONG).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					else{
						Log.d("qwerty","走到这里123");
						imageData[i] = F_GetBitmap.getSDBitmap(image[i]);
						if (F_GetBitmap.bitmap != null && !F_GetBitmap.bitmap.isRecycled()) {
							F_GetBitmap.bitmap = null;
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void initList() {
		for (int i = 0; i < name.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name[i]);
			map.put("image", imageData[i]);
			listItem.add(map);
		}
		initBaseAdapter();
	}
	public void initBaseAdapter() {
		gridView=(GridView)findViewById(R.id.gridview);
		base = new baseAdapter(this);
		gridView.setAdapter(base);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Intent it=new Intent(grideActivity.this,CheckAlbumActivity.class);
				it.putExtra("xiangce", imageData[arg2]);
				startActivity(it);
			}
		});
	}
	private class baseAdapter extends BaseAdapter { // listview 的适配器
		private LayoutInflater mInflater = null;
		public baseAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
			data.addAll(listItem);
			listItem.clear();
		}

		@Override
		public int getCount() {
			return data.size();// 数据的长短
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
				convertView = mInflater.inflate(R.layout.gride_list, null);
				myViews.image = (ImageView) convertView.findViewById(R.id.image);
				myViews.name = (TextView) convertView.findViewById(R.id.title);
				convertView.setTag(myViews);
			} else {
				myViews = (ViewHolder) convertView.getTag();

			}
			myViews.image.setImageBitmap((Bitmap) data.get(position).get("image"));
			myViews.name.setText((String) data.get(position).get("name"));
			return convertView;
		}
	}
    static class ViewHolder {
		private ImageView image;
		private TextView name;
	}
}
