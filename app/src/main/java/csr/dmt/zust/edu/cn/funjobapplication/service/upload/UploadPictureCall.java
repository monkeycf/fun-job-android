package csr.dmt.zust.edu.cn.funjobapplication.service.upload;

import java.io.File;
import java.util.Formatter;
import java.util.Timer;
import java.util.TimerTask;

import csr.dmt.zust.edu.cn.funjobapplication.view.note.NoteCreateActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * created by monkeycf on 2019/12/18
 */
public class UploadPictureCall {
    private Call<UploadResModule> mCall;
    private NoteCreateActivity mContext;

    public UploadPictureCall(NoteCreateActivity context) {
        mContext = context;
    }

    public Call<UploadResModule> getInstance(String filePath) {
        // 创建网络请求接口的实例
        File imageFile = new File(filePath);
        RequestBody requestFile =
                RequestBody.create(imageFile, MediaType.parse("multipart/form-data"));
        MultipartBody.Part part = MultipartBody.Part.createFormData("img", imageFile.getName(), requestFile);
        //对发送请求进行封装
        mCall = UploadPicture.getRequest().upload(part);

        // 10s主动关闭请求
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            //定时任务执行方法
            @Override
            public void run() {
                System.out.println(new Formatter().format("图片上传超时%s", filePath).toString());
                mCall.cancel();
            }
        }, 10000);

        //发送网络请求(异步)
        mCall.enqueue(new Callback<UploadResModule>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<UploadResModule> call, Response<UploadResModule> response) {
                UploadResModule r = response.body();
                System.out.println(new Formatter().format("success file:::%s", r.getUrl()).toString());
                mContext.addSuccessPicture(r.getUrl());
                timer.cancel();
            }

            @Override
            public void onFailure(Call<UploadResModule> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        return mCall;
    }
}
