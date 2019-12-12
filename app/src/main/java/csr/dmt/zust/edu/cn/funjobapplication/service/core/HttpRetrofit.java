package csr.dmt.zust.edu.cn.funjobapplication.service.core;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * created by monkeycf on 2019/12/12
 */
public class HttpRetrofit {

    private static Retrofit sRetrofit;
    private final static String BASE_URL = "http://funjobapi.chensenran.top";

    public static Retrofit get() {
        if (sRetrofit == null) {
            new HttpRetrofit();
        }
        return sRetrofit;
    }

    private HttpRetrofit() {
        sRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // 设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
                .build();
    }
}
