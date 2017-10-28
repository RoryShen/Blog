package rory.tech.blog;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String downloadUrl = "http://www.rory.tech/TestData/1.xls";
    private SharedPreferences mPreferences;
    private String testRes = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btrun = (Button) findViewById(R.id.bt_choose);
        mPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        if (mPreferences.getBoolean("testData", false)) {
            btrun.setEnabled(true);
        } else {
            updateDialog();
        }

    }


    public void choose(View view) {

        Toast.makeText(this, "不可用！", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("application/vnd.ms-excel");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case Activity.RESULT_OK:
                Uri uri = data.getData();
                Toast.makeText(this, "文件路劲：" + uri.getPath().toString(), Toast.LENGTH_SHORT).show();
                mPreferences.edit().putString("dataUrl", uri.getPath().toString()).apply();
                if (!mPreferences.getBoolean("testData", false)) {
                    mPreferences.edit().putBoolean("testData", true).apply();
                }
                System.out.println("ABC" + mPreferences.getBoolean("testData", false));
                break;
            case Activity.RESULT_CANCELED:
                Toast.makeText(this, "您没选择任何文件哦！", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void run(View view) {
        if (!mPreferences.getBoolean("testData", false)) {
            Toast.makeText(this, "请下载或选择测试数据！", Toast.LENGTH_SHORT).show();

        } else {
            new UiautomatorThread().start();
            // startActivity(new Intent(this,));

        }
    }


    protected void updateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("是否下载运行数据？");
        builder.setMessage("您还没下载过测试数据，下载进行下载？？");
        builder.setPositiveButton("好哒，下载！", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Download", "Start Download test data");
                download();
            }
        });
        builder.setNegativeButton("不了!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "请手动准备测试数据哦！", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();


    }

    public void download() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //创建下载链接
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
            //设置允许下载的网络环境
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
            //自定义通知栏显示
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setTitle("测试数据下载中！");
            request.setDescription("正在下载测试所需数据！");

            //设置保存路劲
            final String DIR = "TestData";
            final String RES = "1.xls";
            final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
            request.setDestinationInExternalPublicDir(DIR, RES);

            //拿到下载ID 同时开始下载
            DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            long id = downloadmanager.enqueue(request);


        }
    }

    /**
     * 运行uiautomator是个费时的操作，不应该放在主线程，因此另起一个线程运行
     */
    class UiautomatorThread extends Thread {
        @Override
        public void run() {
            super.run();
            String command = generateCommand("test.ckt.testcase", "DataDrivenTestCase", "OpenAppFormList");
            CMDUtils.CMD_Result rs = CMDUtils.runCMD(command, true, true);
            Log.e("TAG", "run: " + rs.error + "-------" + rs.success);
        }

        /**
         * 生成命令
         *
         * @param pkgName 包名
         * @param clsName 类名
         * @param mtdName 方法名
         * @return
         */
        public String generateCommand(String pkgName, String clsName, String mtdName) {
            String command = "am instrument  -w -r   -e debug false -e class "
                    + pkgName + "." + clsName + "#" + mtdName + " "
                    + pkgName + ".test/android.support.test.runner.AndroidJUnitRunner";
            Log.e("test1: ", command);
            return command;
        }
    }
}
