package demo.luquick.com.netframeofdesign;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.luquick.download.DownloadManager;
import com.luquick.download.http.impl.HttpManager;
import com.luquick.download.http.interf.DownloadCallBack;
import com.luquick.download.utils.Logger;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;

    private String mUrl;
    /**
     * todo
     * CallBack会被回掉两次，此变量用于过滤。不是很友好呀。。。。。
     */
    private int count;
    private int count2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        mImageView = this.findViewById(R.id.show_imgv);
        //图片
//        mUrl = "https://img4.mukewang.com/szimg/59b8a486000107fb05400300.jpg";
//        mUrl = "https://img.mukewang.com/5b1e47160001d8a918720632.jpg";

        //apk
//        mUrl = "http://a8.pc6.com/zm7/yujianpotian.apk";
        mUrl = "http://gyxz.hwm6b6.cn/a31/rj_sp1/kumanhua.apk";
    }

    /**
     * @param view
     */
    public void downloadFile(View view) {
        //用来校验Md5
        //File file  = FileStorageManager.getInstance().getFileByName("http://www.imooc.com");
        //Logger.debug("nate","file path = " + file.getAbsolutePath());

        //异步文件下载
        HttpManager.getInterface().asyncRequestDownload(mUrl, new DownloadCallBack() {
            @Override
            public void success(File file) {
                if (count < 1) {
                    count++;
                    return;
                }
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                ////这里使用的是lambda ...匿名函数
                runOnUiThread(() -> mImageView.setImageBitmap(bitmap));
                Logger.debug("nate", "success : " + file.getAbsolutePath());
            }

            @Override
            public void fail(int errorCode, String errorMessage) {
                Logger.debug("nate", "fail errorCode : "
                        + errorCode + "\n errorMessage : " + errorMessage);
            }

            @Override
            public void progress(int progress) {

            }
        });
    }

    /**
     * @param view
     */
    public void multiThreadDownload(View view) {
        DownloadManager.getInstance().download(mUrl,
                new DownloadCallBack() {
                    @Override
                    public void success(File file) {
                        if (count2 < 1) {
                            count2++;
                            return;
                        }
                        Logger.debug("Hello", "success --------");
                        //todo 测试下载APK
                        installApk(file);

                        //todo 测试下载图片
                       /* Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        runOnUiThread(() -> mImageView.setImageBitmap(bitmap));*/
                        Logger.debug("nate", "success + " + file.getAbsolutePath());
                    }

                    @Override
                    public void fail(int errorCode, String errorMessage) {
                        Logger.debug("nate",
                                "fail + errorCode = " + errorCode
                                        + ";\nerrorMellage = " + errorMessage);
                    }

                    @Override
                    public void progress(int progress) {
                    }
                });
    }

    /**
     * 用于测试
     * 需要下载的APK
     */
    private void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setDataAndType(Uri.parse("file://" + file.getAbsoluteFile().toString()), "application/vnd.android.package-archive");
        startActivity(intent);

    }

    /**
     * 跳转页面
     *
     * @param view
     */
    public void startAnotherActivity(View view) {
        Intent intent = new Intent(this, AnotherActivity.class);
        startActivity(intent);
    }
}
