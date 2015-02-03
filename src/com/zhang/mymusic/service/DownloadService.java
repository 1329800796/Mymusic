package com.zhang.mymusic.service;

import com.zhang.mymusic.AppConstant;
import com.zhang.mymusic.HomeActivity;
import com.zhang.mymusic.R;
import com.zhang.mymusic.domain.Mp3Info;
import com.zhang.mymusic.downloder.HttpDownloder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		// ��ȡ����
		Mp3Info mp3 = (Mp3Info) intent.getSerializableExtra("mp3Info");

		// �����߳�
		DownloadThead downloadThead = new DownloadThead(mp3);
		Thread thread = new Thread(downloadThead);
		thread.start();

		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 
	 * @author Administrator
	 * 
	 */
	class DownloadThead implements Runnable {
		private Mp3Info mp3 = null;

		public DownloadThead(Mp3Info mp3) {
			this.mp3 = mp3;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// ���ص�ַhttp://192.168.1.100:8080/mp3/a1.mp3
			String mp3url = AppConstant.URL.BASE_URL + mp3.getMp3Name();
			String lrcurl = AppConstant.URL.BASE_URL + mp3.getLrcName();
			HttpDownloder downloder = new HttpDownloder();

			// url + sd ������λ��+MP3 ����
			int result = downloder.downFile(mp3url, "mp3/", mp3.getMp3Name());
			int lrcresult = downloder
					.downFile(lrcurl, "mp3/", mp3.getLrcName());

			String resultmessage = null;
			if (result == -1) {
				resultmessage = "����ʧ��";
			} else if (result == 0) {
				resultmessage = "�ļ����سɹ�";
			} else if (result == 1) {
				resultmessage = "�ļ��Ѿ�����";
			}
			// ʹ��Notification ��ʾ�ͻ����ؽ��
			CreateInform(resultmessage);

		}

	}

	public void CreateInform(String resultmessage) {
		// ����һ��PendingIntent�����û����֪ͨʱ����ת��ĳ��Activity(Ҳ���Է��͹㲥��)
		Intent intent = new Intent(this, HomeActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, 0);

		// ����һ��֪ͨ
		Notification notification = new Notification(R.drawable.ic_launcher,
				resultmessage, System.currentTimeMillis());
		notification
				.setLatestEventInfo(this, "����鿴", "����鿴��ϸ����", pendingIntent);

		// ��NotificationManager��notify����֪ͨ�û����ɱ�������Ϣ֪ͨ
		NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nManager.notify(100, notification);// id��Ӧ����֪ͨ��Ψһ��ʶ
		// ���ӵ����ͬid��֪ͨ�Ѿ����ύ����û�б��Ƴ����÷������ø��µ���Ϣ���滻֮ǰ��֪ͨ��
	}

}
