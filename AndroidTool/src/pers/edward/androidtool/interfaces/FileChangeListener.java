package pers.edward.androidtool.interfaces;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * 文件变化监听类
 * 
 * @author Edward
 * 
 */
public class FileChangeListener implements FileAlterationListener {

	private setChangeFilePath alreadyChangeFilePath;

	public void setAlreadyChangeFilePath(setChangeFilePath alreadyChangeFilePath) {
		this.alreadyChangeFilePath = alreadyChangeFilePath;
	}

	public interface setChangeFilePath {
		public void alreadyChangeFilePath(File file);
	}

	public FileChangeListener(setChangeFilePath test) {
		alreadyChangeFilePath = test;
	}

	@Override
	public void onDirectoryChange(File arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDirectoryCreate(File arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDirectoryDelete(File arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFileChange(File arg0) {
		// TODO Auto-generated method stub
		alreadyChangeFilePath.alreadyChangeFilePath(arg0);
	}

	@Override
	public void onFileCreate(File arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFileDelete(File arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(FileAlterationObserver arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStop(FileAlterationObserver arg0) {
		// TODO Auto-generated method stub

	}

}
