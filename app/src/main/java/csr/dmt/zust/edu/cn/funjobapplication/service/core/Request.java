package csr.dmt.zust.edu.cn.funjobapplication.service.core;

import csr.dmt.zust.edu.cn.funjobapplication.service.request.ITopicRequest;
import csr.dmt.zust.edu.cn.funjobapplication.service.request.IUserRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * created by monkeycf on 2019/12/12
 *
 * @param <T>
 */
public class Request<T> {

    private Call<T> mCall;

    private static final Class<ITopicRequest> TopicRequest = ITopicRequest.class;

    private static final Class<IUserRequest> UserRequest = IUserRequest.class;

    public Request(Call<T> call, final IHttpCallBack<T> IHTTPCallBack) {
        mCall = call;
        begin(IHTTPCallBack);
    }

    private void begin(final IHttpCallBack<T> IHTTPCallBack) {
        mCall.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call,
                                   Response<T> response) {
                IHTTPCallBack.SuccessCallBack(response.body());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                IHTTPCallBack.ErrorCallBack(t.getMessage());
            }
        });
    }

    public static Class<IUserRequest> getUserRequest() {
        return UserRequest;
    }

    public static Class<ITopicRequest> getTopicRequest() {
        return TopicRequest;
    }
}
