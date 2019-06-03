package com.community.shetuanbao.activity;
import java.io.ByteArrayInputStream;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.community.shetuanbao.R;
import com.community.shetuanbao.utils.F_GetBitmap;

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
	String id=null;
	@Override
	public void onCreate(Bundle savedInstanceState){ 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.gride);
        Intent intent = getIntent();
        //得到活动的相册
        id = intent.getStringExtra("id");
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
			//更具活动的id得到活动的相册
//			imagey = NetInfoUtil.gethuodongxiangce(id);
			all=new String[imagey.size()][imagey.get(0).length];
			image=new String[all.length];
			name=new String[all.length];
			imageData=new Bitmap[all.length];
			all_image=new byte[all.length][];
            if(imagey.get(0)[0].equals("")){
				
			}else{
				for(int i=0;i<imagey.size();i++){
					for(int j=0;j<imagey.get(i).length;j++){
						all[i][j]=imagey.get(i)[j];
			            name[i]="图片"+(i+1)+"";
						image[i]=all[i][0]+".png";
					}
				}
				for (int i = 0; i < imagey.size(); i++) {
					if (F_GetBitmap.isEmpty(image[i])) {
//						all_image[i] = NetInfoUtil.getPicture(image[i]);
						F_GetBitmap.setInSDBitmap(all_image[i], image[i]);
						InputStream input = null;
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inSampleSize = 2;
						input = new ByteArrayInputStream(all_image[i]);
						@SuppressWarnings({ "rawtypes", "unchecked" })
						SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(input, null, options));
						imageData[i] = (Bitmap) softRef.get();
						//System.out.println(imageData.length);
					} else {
						imageData[i] = F_GetBitmap.getSDBitmap(image[i]);// �õ�����BitMap���͵�ͼƬ���
						if (F_GetBitmap.bitmap != null && !F_GetBitmap.bitmap.isRecycled()) {
							F_GetBitmap.bitmap = null;
						}
					}
				}
				initList();
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
