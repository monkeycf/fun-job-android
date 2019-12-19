package csr.dmt.zust.edu.cn.funjobapplication.service.upload;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * created by monkeycf on 2019/12/18
 */
public interface IUploadPicture {
    // 上传图片
    @Multipart
    @POST("/upload/img")
    Call<UploadResModule> upload(@Part() MultipartBody.Part file);
}
