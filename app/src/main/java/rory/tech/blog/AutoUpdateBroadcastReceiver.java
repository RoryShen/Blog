package rory.tech.blog;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.widget.Toast;

/**
 * Created by kongqingwei on 2016/12/19.
 * 广播接收者
 */
public class AutoUpdateBroadcastReceiver extends BroadcastReceiver {
    private SharedPreferences mPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
            Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
            mPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
            mPreferences.edit().putBoolean("testData", true).commit();




        }
    }


}