package csr.dmt.zust.edu.cn.funjobapplication.service.upload;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * created by monkeycf on 2019/12/18
 */
public class UploadPicture {
    private static Retrofit sRetrofit;
    private static IUploadPicture sRequest;

    /**
     * 上传图片
     */
    private UploadPicture() {
        sRetrofit = new Retrofit.Builder()
                .baseUrl("http://api.chensenran.top") // 设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
                .build();
        sRequest = sRetrofit.create(IUploadPicture.class);
    }

    public static IUploadPicture getRequest() {
        if (sRequest == null) {
            new UploadPicture();
        }
        return sRequest;
    }
}
