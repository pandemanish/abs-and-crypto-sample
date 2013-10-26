package com.abstricelogic.filenav;

import java.io.File;
import java.util.ArrayList;

public class FileNav {

	private static String FILE_ROOT = "/";
	
	private File mCurFilePos;
	
	public FileNav() {
		this(new File(FILE_ROOT));
	}
	
	public FileNav(File startPos) {
		this.mCurFilePos = startPos;
	}
	
	public void moveInto(String childName) {
		//File childFile = new File(this.mCurFilePoschildName);
	}
	
	public void moveBack() {
		
	}
	
	public ArrayList<File> getChildren() {
		ArrayList<File> childrenFiles = null;
		if(this.mCurFilePos.isDirectory()) {
			childrenFiles = new ArrayList<File>();
			String[] childList = this.mCurFilePos.list();
			String childPath = null;
			for(int i = 0; i < childList.length; i++) {
				childrenFiles.add(new File(childPath + File.separator + childList[i]));
			}
		}
		return childrenFiles;
	}
	
}
