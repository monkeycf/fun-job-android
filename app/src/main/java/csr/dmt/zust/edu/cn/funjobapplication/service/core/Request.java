package csr.dmt.zust.edu.cn.funjobapplication.service.core;

import csr.dmt.zust.edu.cn.funjobapplication.service.request.INoteRequest;
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

    private static final Class<ITopicRequest> sTopicRequest = ITopicRequest.class;

    private static final Class<IUserRequest> sUserRequest = IUserRequest.class;

    private static final Class<INoteRequest> sNoteRequest = INoteRequest.class;

    public static Class<IUserRequest> getUserRequest() {
        return sUserRequest;
    }

    public static Class<ITopicRequest> getTopicRequest() {
        return sTopicRequest;
    }

    public static Class<INoteRequest> getNoteRequest() {
        return sNoteRequest;
    }

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
}
