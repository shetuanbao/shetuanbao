package com.community.shetuanbao.activity;
import java.io.IOException;
import java.util.Calendar;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.community.shetuanbao.Login.LoginActivity;
import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.F_GetBitmap;
import com.community.shetuanbao.utils.FontManager;
import com.community.shetuanbao.utils.LocalLoader;
import com.community.shetuanbao.utils.NetLoader;
import com.community.shetuanbao.utils.RequestUtils;
import com.community.shetuanbao.utils.RoundImageView;
import com.community.shetuanbao.utils.activitytubiaoStrategy;
import com.community.shetuanbao.utils.usertubiaoStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.transform.sax.TransformerHandler;

@SuppressLint({ "HandlerLeak", "SimpleDateFormat" })
public class HuoDongDetailActivity extends Activity {
	private TextView funhui = null;
	private int num;
	HashMap<String,Object> params;
	public static int state =0;
	TextView xiangce=null;
	ProgressDialog pd;
	List<String[]> ls = new ArrayList<String[]>();
	List<String[]> ls_gallery = new ArrayList<String[]>();
	List<String[]> lsxing = new ArrayList<String[]>();
	List<String[]> detaily = new ArrayList<String[]>();
	List<String[]> imagey = new ArrayList<String[]>();
	List<String[]> namey = new ArrayList<String[]>();
	List<String[]> idy = new ArrayList<String[]>();
	List<String[]> timey=new ArrayList<String[]>();
	List<String[]> detaily2 = new ArrayList<String[]>();
	List<String[]> imagey2 = new ArrayList<String[]>();
	List<String[]> namey2 = new ArrayList<String[]>();
	List<String[]> idy2 = new ArrayList<String[]>();
	List<String[]> timey2=new ArrayList<String[]>();
	Bitmap picture;
	Bitmap imageData[];//活动相册
	byte all_image[][];
	TextView tt = null;
	TextView tt2 = null;
	TextView tt3 = null;
	TextView tt4 = null;//显示活动信息的四个textView
	String re_name = null;//活动名称
	String re_time = null;//活动时间
	String re_place = null;//活动地点
	String re_introduce=null;//活动介绍
	int re_id ;//活动id
	private String[][] all = null;
	private String[][] all_2 = null;
	private String[][] all_3 = null;
	private String[][] all_4 = null;
	private String[][] all_5=null;
	private String detail[] = null;//活动介绍
	private String image[] = null;//用户头像
	private String name[] = null;//评论用户名
	private String id[] = null;//活动id
	private String time[]=null;//评论时间
	private String renming;//人名
	private String rentupian;
	String msg3 = null;
	private List<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	ListView listview = null;
	private baseAdapter base = null;
	Button bt = null;
	LinearLayout ll = null;
	LinearLayout ll2 = null;
	ImageView back = null;
	private EditText actv;
	Button bt2 = null;
	Button bt3 = null;
	byte bb[] = null;
	Bitmap bb2 = null;
	public boolean pddd=false;
	List<String[]> zong=new ArrayList<String[]>();
	byte[][] all_image2=null;
	String[] image2=null;
	Bitmap imageData2[]=null;
	MainCampaignActivity mm2=new MainCampaignActivity();
	static int count=0;
	@SuppressWarnings("unused")
	private String cz3=null;
	List<String[]> xiangcey=new ArrayList<String[]>();
	String all_xiangce[][]=null;
	String xiangce2[]=null;
	byte all_image3[][]=null;
	Bitmap imageData3[]=null;
	private ViewPager viewPager;
	private ImageView[] tips;
	private ImageView[] mImageViews;
	int currentItem;
	private boolean isRunning = true;
	ViewGroup group;
	LinearLayout a;
	LinearLayout b;
	String shifou=null;
	String shifou2=null;
	String leixing=null;
	int communityId;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// 执行滑动到下一个页面
			viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
			if (isRunning) {
				// 在发一个handler延时
				handler.sendEmptyMessageDelayed(0, 2000);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.huodong);
		a=(LinearLayout)findViewById(R.id.huongdong_baoming);
		b=(LinearLayout)findViewById(R.id.huongdong_bubaoming);
		actv = (EditText) findViewById(R.id.in_pinglun);
		listview = (ListView) findViewById(R.id.huodong_pinglun_1);
		pddd=false;
		Intent intent = getIntent();
		re_name = intent.getStringExtra("name");
		re_time = intent.getStringExtra("time");
		re_place = intent.getStringExtra("place");
		re_id = intent.getIntExtra("id",0);
		thread_huodong thread_huodong = new thread_huodong();
		thread_huodong.start();
		try {
			thread_huodong.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		funhui = (TextView) findViewById(R.id.restaurantdetial_tool_text1);
		funhui.setTypeface(FontManager.tf);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		pd = new ProgressDialog(this);
		pd.setMax(100);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setCancelable(false);
		pd.setMessage("加载中...请稍后");
		tt = (TextView) findViewById(R.id.restaurantdetial_name_2);
		tt2 = (TextView) findViewById(R.id.huodong_address_2);
		tt3 = (TextView) findViewById(R.id.huodong_time_2);
		tt4 = (TextView) findViewById(R.id.huodong_detail_2);
		tt4.setText(re_introduce);
		Log.d("活动介绍",tt4.getText().toString());
		tt2.setText(re_place);
		tt3.setText(re_time);
		tt.setText(re_name);
		//获取评论
		new AysncTask_get().execute();
		xiangce=(TextView)findViewById(R.id.restaurial_name_2);
		xiangce.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it=new Intent(HuoDongDetailActivity.this,grideActivity.class);
				it.putExtra("id", re_id);
				startActivity(it);
			}
		});
		xiangce.setTypeface(FontManager.tf);//相册按钮
		funhui.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});//返回按钮
		bt2 = (Button) findViewById(R.id.send_pinglun);//发送评论按钮
		bt2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(actv.getText().toString().equals("")){
					Toast.makeText(HuoDongDetailActivity.this, "评论不可为空!!",
							Toast.LENGTH_LONG).show();
				}else{
					//插入评论
					new AysncTask_team().execute();
					Intent it = new Intent(HuoDongDetailActivity.this,HuoDongDetailActivity.class);
					it.putExtra("name", re_name);
					it.putExtra("time", re_time);
					it.putExtra("place", re_place);
					it.putExtra("id", re_id);
					finish();
					startActivity(it);
				}
			}
		});
		a.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				thread_pd tp=new thread_pd();
				tp.start();
				try{
					tp.join();
				}catch(Exception e){
					e.printStackTrace();
				}
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date date = null;
				try {
					date = format.parse(re_time);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Date date2=new Date();
				int pdd=(int) ((getMillis(date) - getMillis(date2)) / (24 * 3600 * 1000));
				if(pdd>0){
					if(leixing.equals("1")){
						thread td=new thread();
						td.start();
						try{
							td.join();
						}catch(Exception e){
							e.printStackTrace();
						}
						Toast.makeText(HuoDongDetailActivity.this, "报名成功!!",
								Toast.LENGTH_LONG).show();
						a.setVisibility(View.GONE);
						b.setVisibility(View.VISIBLE);
					}else if(leixing.equals("0")){
						if(shifou.equals("0")){
							Toast.makeText(HuoDongDetailActivity.this, "活动不对社团外的人开放!!",
									Toast.LENGTH_LONG).show();
						}else if(shifou.equals("1")){
							thread td=new thread();
							td.start();
							try{
								td.join();
							}catch(Exception e){
								e.printStackTrace();
							}
							Toast.makeText(HuoDongDetailActivity.this, "报名成功!!",
									Toast.LENGTH_LONG).show();
							a.setVisibility(View.GONE);
							b.setVisibility(View.VISIBLE);
						}
					}
				}else {
					Toast.makeText(HuoDongDetailActivity.this, "活动时间已过!!",
							Toast.LENGTH_LONG).show();
				}
				System.out.println("hhhhhhhhhhhhhhhhhhhhhh"+pdd);

			}
		});//参加活动按钮
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(HuoDongDetailActivity.this, "您已参加!!",
						Toast.LENGTH_LONG).show();
			}

		});
	}
	public static long getMillis(Date date) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c.getTimeInMillis();
	}
	//得到活动详情的线程
	public class thread_huodong extends Thread{
		public void run(){
			//todo mo
			//得到活动的各种信息
			try {

				HashMap<String, Object> params = new HashMap<>();
				params.put("activityTitle", re_name);
				String res = RequestUtils.post("/activities/getActivityDetail", params);
				Log.d("response",res);
				JSONObject jsonObject1 = new JSONObject(res);
				if (jsonObject1.getInt("code") == 200) {
					// 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
					JSONObject users = jsonObject1.getJSONObject("data");
					re_introduce=users.getString("activityIntroduce");
				}

			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//插入评论的线程
	public class thread_insert extends Thread {
		@Override
		public void run() {
			try {
				HashMap<String, Object> params = new HashMap<>();
				params = new HashMap<>();
				SharedPreferences s= LoginActivity.sp;
				params.put("userId", s.getInt("SNO",0) );
				String res = RequestUtils.post("/users/pangetuserByuserId", params);
				JSONObject jsonObject1 = new JSONObject(res);
				if (jsonObject1.getInt("code") == 200) {
					//todo mo
					JSONObject users = jsonObject1.getJSONObject("data");
					renming =users.getString("userName");
					rentupian =users.getString("userphoto");
				} else {
					Looper.prepare();
					Toast.makeText(HuoDongDetailActivity.this, "获取用户信息失败", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String messa = actv.getText().toString();
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time3=format.format(date);
			try {
				HashMap<String, Object> params = new HashMap<>();
				params = new HashMap<>();
				params.put("activityId", re_id );
				SharedPreferences s= LoginActivity.sp;

				params.put("userId", s.getInt("SNO",0) );
				params.put("sdetail", messa);
				params.put("sname", renming);
				params.put("spicture", rentupian);
				params.put("stime", time3);
				String res = RequestUtils.post("/pinglun/insertPinglun", params);
				JSONObject jsonObject1 = new JSONObject(res);
				if (jsonObject1.getInt("code") == 200) {
					//todo mo
					Toast.makeText(HuoDongDetailActivity.this, "mo：评论成功", Toast.LENGTH_LONG).show();
				} else {
					Looper.prepare();
					Toast.makeText(HuoDongDetailActivity.this, "mo：评论失败", Toast.LENGTH_LONG).show();
				}

			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	//删除评论的线程
	public class thread_delete extends Thread {
		@Override
		public void run() {
			//todo mo
			//mo根据活动id删除活动的评论
			try {
				HashMap<String, Object> params = new HashMap<>();
				params = new HashMap<>();
				params.put("activityId", re_id );
				String res = RequestUtils.post("/pinglun/moDeletePinglun", params);
				JSONObject jsonObject1 = new JSONObject(res);
				if (jsonObject1.getInt("code") == 200) {
					//todo mo
					Toast.makeText(HuoDongDetailActivity.this, "mo：删除评论成功", Toast.LENGTH_LONG).show();
				} else {
					Looper.prepare();
					Toast.makeText(HuoDongDetailActivity.this, "mo：删除评论失败", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	//记载评论的线程
	public class thread_pinglun extends Thread {
		@Override
		public void run() {
			//得到活动的信息
			try {
				HashMap<String, Object> params = new HashMap<>();
				params.put("activityId", re_id);
				String res = RequestUtils.post("/pinglun/getPinglun", params);
				JSONObject jsonObject1 = new JSONObject(res);
				Log.d("response:",res);
				if (jsonObject1.getInt("code") == 200) {
					// 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
					JSONArray list = (JSONArray) jsonObject1.get("data");
					num=list.length();
					id=new String[num];
					time=new String[num];
					name=new String[num];
					detail=new String[num];
					image=new String[num];
					for(int i=0;i<list.length();i++){
						id[i]=list.getJSONObject(i).getString("userId");
						detail[i]=list.getJSONObject(i).getString("sdetail");
						name[i]=list.getJSONObject(i).getString("sname");
						image[i]=list.getJSONObject(i).getString("spicture");
						time[i]=list.getJSONObject(i).getString("stime");
					}
					all_image=new byte[image.length][];
					imageData=new Bitmap[list.length()];
					for(int i=0;i<image.length;i++) {
						if (F_GetBitmap.isEmpty(image[i])) {
							params = new HashMap<>();
							params.put("photo", image[i]);
							String res1 = RequestUtils.post("/users/panfindphoto", params);
							Log.d("response:",res1);
							try {
								JSONObject jsonObject2 = new JSONObject(res1);
								if (jsonObject2.getInt("code") == 200) {
									// 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
									JSONArray list1 = (JSONArray) jsonObject2.get("data");
									all_image[i] = new byte[list1.length()];
									for (int j = 0; j < list1.length(); j++) {
										all_image[i][j] = (byte) list1.getInt(j);
									}
									F_GetBitmap.setInSDBitmap(all_image[i], image[i]);
									InputStream input = null;
									BitmapFactory.Options options = new BitmapFactory.Options();
									options.inSampleSize = 1;
									input = new ByteArrayInputStream(all_image[i]);
									@SuppressWarnings({"rawtypes", "unchecked"})
									SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(input, null, options));
									imageData[i] = (Bitmap) softRef.get();
								} else {
									Looper.prepare();
									Toast.makeText(HuoDongDetailActivity.this, "获取社团信息失败", Toast.LENGTH_LONG).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							imageData[i] = F_GetBitmap.getSDBitmap(image[i]);
							if (F_GetBitmap.bitmap != null && !F_GetBitmap.bitmap.isRecycled()) {
								F_GetBitmap.bitmap = null;
							}
						}
					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			initList();
		}
	}
	//得到相册的线程
public class Thread_getAl extends Thread{
		@Override
		public void run(){
			try{
				params=new HashMap<>();
				params.put("activityId",re_id);
				String res=RequestUtils.post("/activityPicture/yangGetAlbum",params);
				Log.d("response:",res);
				JSONObject jsonObject1 = new JSONObject(res);
				if (jsonObject1.getInt("code") == 200) {
					// 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
					JSONArray list1 = (JSONArray) jsonObject1.get("data");
					num = list1.length();
					image2 = new String[num];
					imageData3 = new Bitmap[num];
					all_image2 = new byte[num][];
					for (int i = 0; i < num; i++) {
						image2[i] = list1.getJSONObject(i).getString("pictureId")+".png";
					}
					Thread_pic thread_pic = new Thread_pic();
					thread_pic.start();
					try {
						thread_pic.join();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	//加载图片的线程
	public class Thread_pic extends Thread{
		@Override
		public void run(){
			try {
				for(int i=0;i<all_image2.length;i++) {
					if (F_GetBitmap.isEmpty(image2[i])) {
						Log.d("qwerty","走到这里");
						params = new HashMap<>();
						params.put("tubiao", image2[i]);
						String res1 = RequestUtils.post("/community/panfindtubiao", params);
						try {
							JSONObject jsonObject1 = new JSONObject(res1);
							if (jsonObject1.getInt("code") == 200) {
								// 注意获取到的数据的数据类型，在后台是数组，则这里是JSONArray，在后台是类，则这里是JSONObject
								JSONArray list1 = (JSONArray) jsonObject1.get("data");
								all_image2[i] = new byte[list1.length()];
								for (int j = 0; j < list1.length(); j++) {
									all_image2[i][j] = (byte) list1.getInt(j);
								}
								F_GetBitmap.setInSDBitmap(all_image2[i], image2[i]);
								InputStream input = null;
								BitmapFactory.Options options = new BitmapFactory.Options();
								options.inSampleSize = 1;
								input = new ByteArrayInputStream(all_image2[i]);
								@SuppressWarnings({ "rawtypes", "unchecked" })
								SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(input, null, options));
								imageData3[i] = (Bitmap) softRef.get();
								Log.d("bitmaptest","压缩前图片的大小"+ (imageData3[i].getByteCount())

										+ "M宽度为"+ imageData3[i].getWidth() + "高度为"+ imageData3[i].getHeight());
								System.out.println(imageData3.length);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					else{
						Log.d("qwerty","走到这里123");
						imageData3[i] = F_GetBitmap.getSDBitmap(image2[i]);
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
	//检查是否已报名参加或是否是签订社团内的人员
	private class thread_pd extends Thread{
		@Override
		public void run(){
			try {
				HashMap<String, Object> params = new HashMap<>();
				params = new HashMap<>();
				params.put("activityName",re_name);
				String res = RequestUtils.post("/activities/moGetActivityByName", params);
				JSONObject jsonObject1 = new JSONObject(res);
				if (jsonObject1.getInt("code") == 200) {
					JSONObject users = jsonObject1.getJSONObject("data");
					leixing = String.valueOf(users.getInt("leixing"));
					communityId=users.getInt("communityId");
				} else {
					Looper.prepare();
					Toast.makeText(HuoDongDetailActivity.this, "mo：检查失败", Toast.LENGTH_LONG).show();
				}

			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				HashMap<String, Object> params = new HashMap<>();
				params = new HashMap<>();
				params.put("communityId",communityId );
				String res = RequestUtils.post("/community/panfindByCommunityUser", params);
				JSONObject jsonObject1 = new JSONObject(res);

				if (jsonObject1.getInt("code") == 200) {
					JSONArray users = jsonObject1.getJSONArray("data");
					SharedPreferences s= LoginActivity.sp;
					int a=s.getInt("SNO",0);
					shifou="0";
					for(int i=0;i<users.length();i++) {
						if(users.getInt(i)==a){
							shifou="1";
						}
					}

				} else {
					Looper.prepare();
					Toast.makeText(HuoDongDetailActivity.this, "mo：检查失败", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				HashMap<String, Object> params = new HashMap<>();
				params = new HashMap<>();
				SharedPreferences s= LoginActivity.sp;

				params.put("userId", s.getInt("SNO",0) );
				params.put("activityId",re_id);
				String res = RequestUtils.post("/activities/checkInActivity", params);
				JSONObject jsonObject1 = new JSONObject(res);

				if (jsonObject1.getInt("code") == 200) {
					int num = jsonObject1.getInt("data");
					if(num>0){
						shifou2="1";
						Looper.prepare();
						Toast.makeText(HuoDongDetailActivity.this, "已报名", Toast.LENGTH_LONG).show();
					}else if(num==0){
						shifou2="0";
						Looper.prepare();
						Toast.makeText(HuoDongDetailActivity.this, "未报名", Toast.LENGTH_LONG).show();
					}
				} else {
					Looper.prepare();
					Toast.makeText(HuoDongDetailActivity.this, "mo：检查失败", Toast.LENGTH_LONG).show();
				}


			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//参加活动线程
	private class thread extends Thread{
		@Override
		public void run(){
			try {
				HashMap<String, Object> params = new HashMap<>();
				params = new HashMap<>();
				SharedPreferences s= LoginActivity.sp;
				params.put("userId", s.getInt("SNO",0) );
				params.put("activityId",re_id  );
				String res = RequestUtils.post("/activities/insertUser", params);
				JSONObject jsonObject1 = new JSONObject(res);
				if (jsonObject1.getInt("code") == 200) {
					//todo mo
					Toast.makeText(HuoDongDetailActivity.this, "mo：插入活动人员成功", Toast.LENGTH_LONG).show();
				} else {
					Looper.prepare();
					Toast.makeText(HuoDongDetailActivity.this, "mo：插入活动人员失败", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	public void initList() {
		for (int i = 0; i < detail.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", imageData[i]);
			map.put("pinglun", detail[i]);
			map.put("name", name[i]);
			map.put("time", time[i]);
			listItem.add(map);
		}
		initadp();
	}
	public void initadp()
	{
		Message msg = new Message();
		msg.what = 2;
		mHandler.sendMessage(msg);
	}
	Handler mHandler=new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
				case 2:initBaseAdapter();
					break;
				default:
					break;
			}
		}
	};
	public void initBaseAdapter() {
		base = new baseAdapter(this);
		listview.setAdapter(base);
		setListViewHeightBasedOnChildren(listview);
	}
	@SuppressLint("InflateParams")
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
				convertView = mInflater.inflate(R.layout.huodong_pinglun, null);
				myViews.image = (RoundImageView) convertView.findViewById(R.id.image2);
				myViews.name = (TextView) convertView.findViewById(R.id.name2);
				myViews.pinglun = (TextView) convertView.findViewById(R.id.name3);
				myViews.id = (TextView) convertView.findViewById(R.id.pinlun);
				convertView.setTag(myViews);
			} else {
				myViews = (ViewHolder) convertView.getTag();
			}
			myViews.image.setImageBitmap((Bitmap) data.get(position).get("image"));
			myViews.name.setText((String) data.get(position).get("name"));
			myViews.pinglun.setText((String) data.get(position).get("time"));
			myViews.id .setText( (String) data.get(position).get("pinglun"));
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
		LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		listView.setLayoutParams(params);
	}
	static class ViewHolder {
		private ImageView image;
		private TextView name;
		private TextView pinglun;
		private TextView id;
	}
	class AysncTask_team extends AsyncTask<Void, Integer, Void> {//异步加载线程
		@Override
		protected void onPreExecute() {
			pd.show();
		}
		@Override
		protected Void doInBackground(Void... params) {
			thread_insert th2 = new thread_insert();
			th2.start();
			try {
				th2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			pd.setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(Void result) {
			// 进行数据加载完成后的UI操作
			pd.dismiss();
		}
	}
	class AysncTask_get extends AsyncTask<Void, Integer, Void> {
		@Override
		protected void onPreExecute() {
			pd.show();
		}
		@Override
		protected Void doInBackground(Void... params) {

			thread_pinglun th = new thread_pinglun();
			th.start();
			try {
				th.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			pd.setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(Void result) {
			// 进行数据加载完成后的UI操作
			thread_pd th1 = new thread_pd();
			th1.start();
			try {
				th1.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 进行数据加载完成后的UI操作
			Thread_getAl th2 = new Thread_getAl();
			th2.start();
			try {
				th2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(shifou2.equals("1")){
				b.setVisibility(View.VISIBLE);
				a.setVisibility(View.GONE);
			}else{
				a.setVisibility(View.VISIBLE);
				b.setVisibility(View.GONE);
			}
			group = (ViewGroup) findViewById(R.id.viewGroup4);
			viewPager = (ViewPager) findViewById(R.id.viewPager4);
			tips = new ImageView[imageData3.length];
			for (int i = 0; i < tips.length; i++) {
				ImageView imageView = new ImageView(HuoDongDetailActivity.this);
				imageView.setLayoutParams(new LayoutParams(30, 30));
				tips[i] = imageView;
				if (i == 0) {
					tips[i].setBackgroundResource(R.mipmap.page_indicator_focused1);
				} else {
					tips[i].setBackgroundResource(R.mipmap.page_indicator_unfocused1);
				}
				ViewGroup parent = (ViewGroup) imageView.getParent();
				if (parent != null) {
					parent.removeAllViewsInLayout();
				}
				group.addView(imageView);
			}
			// 将图片装载到数组中
			mImageViews = new ImageView[imageData3.length];
			for (int i = 0; i < imageData3.length; i++) {
				ImageView imageView = new ImageView(HuoDongDetailActivity.this);
				mImageViews[i] = imageView;
				imageView.setImageBitmap(imageData3[i]);
			}
			viewPager.setAdapter(new MyAdapter());
			// 设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
			viewPager.setCurrentItem((mImageViews.length) * 100);
			// 设置监听，主要是设置点点的背景
			viewPager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageSelected(int position) {
					setImageBackground(position % mImageViews.length);
				}

				@Override
				public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
					// 页面正在滑动时间回调
				}

				@Override
				public void onPageScrollStateChanged(int state) {
					// 当pageView 状态发生改变的时候，回调

				}
			});
			handler.sendEmptyMessageDelayed(0, 2000);
			pd.dismiss();
		}
	}
	public class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(mImageViews[position % mImageViews.length]);

		}

		/**
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
		 */
		@Override
		public Object instantiateItem(View container, final int position) {
			if (mImageViews[position % mImageViews.length].getParent() != null) {
				((ViewGroup) mImageViews[position % mImageViews.length].getParent())
						.removeView(mImageViews[position % mImageViews.length]);
			}

			((ViewPager) container).addView(mImageViews[position % mImageViews.length], 0);
			currentItem = position;
			mImageViews[position % mImageViews.length].setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

				}
			});

			return mImageViews[position % mImageViews.length];

		}
	}
	public void onPageScrollStateChanged(int arg0) {

	}
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}
	public void onPageSelected(int arg0) {
		setImageBackground(arg0 % mImageViews.length);

	}
	/**
	 * 设置选中的tip的背景
	 *
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems) {
				tips[i].setBackgroundResource(R.mipmap.page_indicator_focused1);
			} else {
				tips[i].setBackgroundResource(R.mipmap.page_indicator_unfocused1);
			}
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) // 重写返回键
	{
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return false;
	}
}
