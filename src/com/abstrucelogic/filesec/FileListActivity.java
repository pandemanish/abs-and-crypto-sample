package com.abstrucelogic.filesec;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author MANISH
 *
 */
public class FileListActivity extends Activity {

	public final static String FILE_PATH = "file_path";
	public final static String ALLOW_HIDDEN_FILES = "show_hidden_files";
	public final static String ACCEPTED_FILE_EXTENSIONS = "accepted_file_extensions";
	private final static String DEFAULT_INITIAL_DIRECTORY = "/";
	public static String Path ="";
	protected File mDirectory;
	protected ArrayList<File> mFiles;
	protected FileListAdapter mAdapter;
	private ListView listView;
	private Button button;
	private TextView pathTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Keeping the Orientation PORTRAIT
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
		//remove the title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//To Make app full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		//setting UI from XML 
		setContentView(R.layout.file_picker_activity);

		listView = (ListView)findViewById(R.id.fileList);
		button =(Button)findViewById(R.id.setPathButton);
		pathTextView =(TextView)findViewById(R.id.pathTextView);
		// Set the view to be shown if the list is empty
		LayoutInflater inflator = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View emptyView = inflator.inflate(R.layout.file_picker_empty_view, null);
		((ViewGroup) listView.getParent()).addView(emptyView);
		listView.setEmptyView(emptyView);
		// Set initial directory
		mDirectory = new File(DEFAULT_INITIAL_DIRECTORY);
		// Initialize the ArrayList
		mFiles = new ArrayList<File>();
		// Set the ListAdapter
		mAdapter = new FileListAdapter(this, mFiles);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> view, View v, int position,
					long id) {
				File nfile = (File)view.getItemAtPosition(position);

				if(nfile.isFile()) {
					//File Encripe hear
					displayAlertDialog(nfile);
					// Finish the activity
					finish();
				} else {
					mDirectory = nfile;
					// Update the files list
					refList();
				}
			}

		});


		// Get intent extras
		if(getIntent().hasExtra(FILE_PATH)) {
			mDirectory = new File(getIntent().getStringExtra(FILE_PATH));
		}

		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if(mDirectory.getParentFile() != null) {
					// Go to parent directory
					mDirectory = mDirectory.getParentFile();
					refList();
					return;
				}
			}
		});
	}

	private void displayAlertDialog(final File nfile) {
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		if(nfile.getName().startsWith(getString(R.string.enc_))){
			alertDialog.setTitle(getString(R.string.encript));
			alertDialog.setMessage(getString(R.string.enc_message));
		}else{
			alertDialog.setTitle(getString(R.string.decript));
			alertDialog.setMessage(getString(R.string.decr_message));
		}
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,getString(R.string.yes), new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				if(nfile.getName().startsWith(getString(R.string.enc_))){
					Toast.makeText(FileListActivity.this,"Encrypting...", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(FileListActivity.this,"Decripting...", Toast.LENGTH_SHORT).show();
				}
			}
		});
		alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,getString(R.string.no), new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				alertDialog.dismiss();
			}
		});

		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.show();


	}

	@Override
	protected void onResume() {
		refList();
		super.onResume();
	}

	/**
	 * Updates the list view to the current directory
	 */
	protected void refList() {

		// Clear the files ArrayList
		mFiles.clear();
		pathTextView.setText(mDirectory.getAbsolutePath());
		// Get the files in the directory
		File[] files = mDirectory.listFiles();
		if(files != null && files.length > 0) {
			for(File f : files) {
				if(f.isHidden()) {
					// Don't add the file
					continue;
				}
				// Add the file the ArrayAdapter
				mFiles.add(f);
			}

			Collections.sort(mFiles);
		}
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onBackPressed() {
		if(mDirectory.getParentFile() != null) {
			// Go to parent directory
			mDirectory = mDirectory.getParentFile();
			refList();
			return;
		}

		super.onBackPressed();
	}



	private class FileListAdapter extends ArrayAdapter<File> {

		private List<File> mObjects;

		public FileListAdapter(Context context, List<File> objects) {
			super(context, R.layout.file_picker_list_item, android.R.id.text1, objects);
			mObjects = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View row = null;

			if(convertView == null) { 
				LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.file_picker_list_item, parent, false);
			} else {
				row = convertView;
			}

			File object = mObjects.get(position);

			ImageView imageView = (ImageView)row.findViewById(R.id.file_picker_image);
			TextView textView = (TextView)row.findViewById(R.id.file_picker_text);
			// Set single line
			textView.setSingleLine(true);

			textView.setText(object.getName());
			if(object.isFile()) {
				// Show the file icon
				imageView.setImageResource(R.drawable.file);
			} else {
				// Show the folder icon
				imageView.setImageResource(R.drawable.folder);
			}

			return row;
		}

	}
}
