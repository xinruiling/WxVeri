package com.example.wxveri;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import com.example.wxveri.MyRecyclerViewAdapter.DataType;
import com.example.wxveri.MyRecyclerViewAdapter.OnItemClickLitener;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	private RecyclerView mRecyclerView;

	private MyRecyclerViewAdapter mAdapter;
	
	HashSet<String> set = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		mAdapter = new MyRecyclerViewAdapter(this);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
				LinearLayoutManager.VERTICAL, false));
		initEvent();
	}

	private void initEvent() {
		mAdapter.setOnItemClickLitener(new OnItemClickLitener() {
			@Override
			public void onItemClick(View view, int position) {
				Toast.makeText(MainActivity.this, position + " click",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onItemLongClick(View view, int position) {
				Toast.makeText(MainActivity.this, position + " long click",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_vertical_listview) {
			showVertiacListView();
			return true;
		} else if (id == R.id.action_horizontal_listview) {
			showHorizontalListView();
			return true;
		} else if (id == R.id.action_grid_view) {
			showGridView();
			return true;
		} else if (id == R.id.action_stagger_list_view) {
			showStaggerListView();
			return true;
		} else if (id == R.id.action_add_item) {
			addItem();
			return true;
		} else if (id == R.id.action_remove_item) {
			removeItem();
			return true;
		} else if (id == R.id.action_shot_screen) {
			shotScreen();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void shotScreen() {
		// TODO Auto-generated method stub
		View view = getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		Rect rect = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		int statusBarHeight = rect.top;

		int width = getWindowManager().getDefaultDisplay().getWidth();
		int height = getWindowManager().getDefaultDisplay().getHeight();

		Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, statusBarHeight, width,
				height - statusBarHeight);
		savePic(bitmap2);
		view.destroyDrawingCache();
		view.setDrawingCacheEnabled(false);

	}

	private void savePic(Bitmap bitmap) {
		String fileName;
		if (android.os.Environment.MEDIA_MOUNTED != "mounted") {
			fileName = "/sdcard/shot_screen.png";
		} else {
			fileName = "/data/data/" + this.getPackageName()
					+ "/shot_screen.png";
		}
		// TODO Auto-generated method stub

		try {
			FileOutputStream out = new FileOutputStream(fileName);
			if (out != null) {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void removeItem() {
		// TODO Auto-generated method stub
		mAdapter.removeData(1);
	}

	private void addItem() {
		// TODO Auto-generated method stub
		mAdapter.addData(1);
	}

	private void showStaggerListView() {
		// TODO Auto-generated method stub
		mAdapter.setDataType(DataType.FIX_WIDTH_RANDOM_HEIGHT);
		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
				StaggeredGridLayoutManager.VERTICAL));
	}

	private void showGridView() {
		// TODO Auto-generated method stub
		mAdapter.setDataType(DataType.FIX_WIDTH_FIX_HEIGHT);

		mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5,
				GridLayoutManager.HORIZONTAL, false));
	}

	private void showHorizontalListView() {
		// TODO Auto-generated method stub
		mAdapter.setDataType(DataType.FIX_WIDTH_FIX_HEIGHT);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
				LinearLayoutManager.HORIZONTAL, false));

	}

	private void showVertiacListView() {
		// TODO Auto-generated method stub
		mAdapter.setDataType(DataType.FIX_WIDTH_FIX_HEIGHT);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
				LinearLayoutManager.VERTICAL, false));
	}

}
