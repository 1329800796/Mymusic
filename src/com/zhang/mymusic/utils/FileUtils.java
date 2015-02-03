package com.zhang.mymusic.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.zhang.mymusic.domain.Mp3Info;

import android.os.Environment;

public class FileUtils {
	private String SDPATH;

	private int FILESIZE = 4 * 1024;

	public String getSDPATH() {
		return SDPATH;
	}

	public FileUtils() {
		// �õ���ǰ�ⲿ�洢�豸��Ŀ¼( /SDCARD )
		SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator;
	}

	/**
	 * ��SD���ϴ����ļ�
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public File createSDFile(String fileName, String dir) throws IOException {
		File file = new File(SDPATH + dir + File.separator + fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * ��SD���ϴ���Ŀ¼
	 * 
	 * @param dirName
	 * @return
	 */
	public File createSDDir(String dirName) {
		File dir = new File(SDPATH + dirName + File.separator);
		dir.mkdirs();
		return dir;
	}

	/**
	 * �ж�SD���ϵ��ļ����Ƿ����
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean isFileExist(String fileName, String path) {
		File file = new File(SDPATH + path + File.separator + fileName);
		return file.exists();
	}

	/**
	 * ��һ��InputStream���������д�뵽SD����
	 * 
	 * @param path
	 * @param fileName
	 * @param input
	 * @return
	 */
	public File write2SDFromInput(String path, String fileName,
			InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			createSDDir(path);
			file = createSDFile(fileName, path);
			output = new FileOutputStream(file);
			byte[] buffer = new byte[FILESIZE];

			/*
			 * ������ԣ���ο��������⣬��������������ṩ�� while((input.read(buffer)) != -1){
			 * output.write(buffer); }
			 */

			/* �����ṩ begin */
			int length;
			while ((length = (input.read(buffer))) > 0) {
				output.write(buffer, 0, length);
			}
			/* �����ṩ end */

			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * ��ȡĿ¼�е�Mp3�ļ������ֺʹ�С
	 */
	public List<Mp3Info> getMp3Files(String path) {
		List<Mp3Info> mp3Infos = new ArrayList<Mp3Info>();
		File file = new File(SDPATH + File.separator + path);
		File[] files = file.listFiles();
		FileUtils fileUtils = new FileUtils();
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().endsWith("mp3")) {
				Mp3Info mp3Info = new Mp3Info();
				mp3Info.setMp3Name(files[i].getName());
				mp3Info.setMp3Size(files[i].length() + "");
				String temp [] = mp3Info.getMp3Name().split("\\.");
				String eLrcName = temp[0] + ".lrc";
				if(fileUtils.isFileExist(eLrcName, "/mp3")){
					mp3Info.setLrcName(eLrcName);
				}
				mp3Infos.add(mp3Info);
			}
		}
		return mp3Infos;
	}

}
