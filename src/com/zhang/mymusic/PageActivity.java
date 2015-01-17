package com.zhang.mymusic;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * �Է�ҳ����ʽ��Զ�̺ͱ��صĽ��� ������������С�
 * 
 * @author Administrator
 * 
 */
public class PageActivity extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page);
		// �õ�host����
		TabHost tabHost = getTabHost();
		// ������ͼ��ָ��һ���
		Intent homeit = new Intent();
		homeit.setClass(this, HomeActivity.class);
		// ����һҳ
		TabHost.TabSpec homeSpec = tabHost.newTabSpec("Home");
		Resources res = getResources();
		homeSpec.setIndicator("Home",
				res.getDrawable(android.R.drawable.stat_sys_download));
		homeSpec.setContent(homeit);
		tabHost.addTab(homeSpec);

		// ������ͼ��ָ��һ���
		Intent localit = new Intent();
		localit.setClass(this, LocalHostActivity.class);
		// ����һҳ
		TabHost.TabSpec localitSpec = tabHost.newTabSpec("Local");
		localitSpec.setIndicator("Local",
				res.getDrawable(android.R.drawable.stat_sys_upload));
		localitSpec.setContent(localit);
		tabHost.addTab(localitSpec);

	}

}
