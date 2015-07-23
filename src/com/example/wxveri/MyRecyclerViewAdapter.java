package com.example.wxveri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.StaticLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
	
	
	public enum DataType{
		FIX_WIDTH_FIX_HEIGHT,
		FIX_WIDTH_RANDOM_HEIGHT,
		RANDOM_WIDTH_FIX_HEIGHT,
		RANDOM_WIDTH_RANDOM_HEIGHT,
	}	
	
	public static class DataModel{
		private String text;
		private int width;
		private int height;
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
	}
	
	public interface OnItemClickLitener
	{
		void onItemClick(View view, int position);

		void onItemLongClick(View view, int position);
	}

	private OnItemClickLitener mOnItemClickLitener;
	
	public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
	{
		this.mOnItemClickLitener = mOnItemClickLitener;
	}
	
	public static class MyViewHolder extends RecyclerView.ViewHolder{
		TextView textView;
		
		public MyViewHolder(View v) {
			// TODO Auto-generated constructor stub
			super(v);
			textView = (TextView) v.findViewById(R.id.item_text_view);
		}
	}

	private static final String TAG = "MyRecyclerViewAdapter";
	
	private ArrayList<DataModel>	mDatas = new ArrayList<DataModel>();
	
	private DataType mDataType = DataType.FIX_WIDTH_FIX_HEIGHT;

	private Context	mContext;
	private LayoutInflater	mInflater;

	MyRecyclerViewAdapter(Context context){
		mContext = context;
		loadDatas();
		mInflater = LayoutInflater.from(context);
	}
	
	public DataType getDataType() {
		return mDataType;
	}

	public void setDataType(DataType mDataType) {
		this.mDataType = mDataType;
		loadDatas();
	}
	
	public void loadDatas() {
		// TODO Auto-generated method stub
		mDatas.clear();
		Random random = new Random();
		for (char c = 'A'; c < 'Z'; c++) {
			DataModel data = new DataModel();
			data.setText(Character.toString(c));
			if(mDataType == DataType.RANDOM_WIDTH_RANDOM_HEIGHT){
				data.setWidth( (int) (100 + Math.random() * 300));
				data.setHeight( (int) (100 + Math.random() * 300));
			}
			else if(mDataType == DataType.FIX_WIDTH_RANDOM_HEIGHT){
				data.setWidth(-1);
				data.setHeight( (int) (100 + Math.random() * 300));
			}
			else if(mDataType == DataType.RANDOM_WIDTH_FIX_HEIGHT){
				data.setWidth( (int) (100 + Math.random() * 300));
				data.setHeight(-1);
			}
			else{
				data.setWidth(-1);
				data.setHeight(-1);
			}
			mDatas.add(data);
		}
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// TODO Auto-generated method stub
		View view = mInflater.inflate(R.layout.item_view, parent, false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder,  final int position) {
		// TODO Auto-generated method stub
		LayoutParams lp = holder.textView.getLayoutParams();
		if(mDataType == DataType.RANDOM_WIDTH_RANDOM_HEIGHT){
			lp.height = mDatas.get(position).getHeight();
			lp.width = mDatas.get(position).getWidth();
		}
		else if(mDataType == DataType.FIX_WIDTH_RANDOM_HEIGHT){
			lp.height = mDatas.get(position).getHeight();
		}
		else if(mDataType == DataType.RANDOM_WIDTH_FIX_HEIGHT){
			lp.width = mDatas.get(position).getWidth();
		}
		Log.e(TAG , TAG + "onBindViewHolder lp.height:" + lp.height);
		holder.textView.setLayoutParams(lp);
		holder.textView.setText(mDatas.get(position).getText());	
		//if(lp.height > 0)
			//holder.textView.setHeight(mDatas.get(position).getHeight());
		{
			holder.itemView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					mOnItemClickLitener.onItemClick(holder.itemView, position);
				}
			});

			holder.itemView.setOnLongClickListener(new OnLongClickListener()
			{
				@Override
				public boolean onLongClick(View v)
				{
					mOnItemClickLitener.onItemLongClick(holder.itemView, position);
					removeData(position);
					return false;
				}
			});
		}
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}
	
	public void addData(int position)
	{
		DataModel model = new DataModel();
		model.setText("Insert One");
		model.setHeight((int) (100 + Math.random() * 300));
		mDatas.add(position, model);
		notifyItemInserted(position);
	}

	public void removeData(int position)
	{
		mDatas.remove(position);
		notifyItemRemoved(position);
	}
	
	
	

}
