package com.abstrucelogic.filesec;

import com.abstrucelogic.filesec.R;
import com.abstrucelogic.filesec.view.FileListItem;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends Activity implements OnItemClickListener {

	private ListView mFileListView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    
    @Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Toast.makeText(this, "item tapped", Toast.LENGTH_SHORT).show();
	}
    
    private void init() {
    	mFileListView = (ListView) this.findViewById(R.id.file_list);
    	mFileListView.setAdapter(new FileListAdapter(this));
    	mFileListView.setOnItemClickListener(this);
    }
    
    private class FileListAdapter extends BaseAdapter {

    	private Context mCurContext;
    	
    	public FileListAdapter(Context context) {
    		this.mCurContext = context;
    	}
    	
		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			FileListItem listItem = null;
			if(convertView == null) {
				listItem = new FileListItem(this.mCurContext);
			} else {
				listItem = (FileListItem) convertView;
			}
			return listItem;
		}
    	
    }
}
