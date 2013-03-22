package org.universaal.tools.packaging.tool.util;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class Dialog {

	public File open(Shell s, String[] filterExt, boolean open, String topText){

		FileDialog fd;
		if(open)
			fd = new FileDialog(s, SWT.OPEN);
		else
			fd = new FileDialog(s, SWT.SAVE);
		fd.setText(topText);
		fd.setFilterPath("C:/");
		fd.setFileName("");
		//String[] filterExt = {"*.uapp"};
		fd.setFilterExtensions(filterExt);
		String selected = fd.open();

		return new File(selected);
	}

	public File open(Shell s, String filename, String[] filterExt, boolean open, String topText){

		FileDialog fd;
		if(open)
			fd = new FileDialog(s, SWT.OPEN);
		else
			fd = new FileDialog(s, SWT.SAVE);
		fd.setText(topText);
		fd.setFilterPath("C:/");
		fd.setFileName(filename);
		//String[] filterExt = {"*.uapp"};
		fd.setFilterExtensions(filterExt);
		String selected = fd.open();

		return new File(selected);
	}
}