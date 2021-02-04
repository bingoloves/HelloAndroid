package cn.cqs.common.utils;

import java.io.IOException;

import cn.cqs.common.log.LogUtils;
import cn.cqs.http.HttpConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LogUtils.e("TokenInterceptor");
        String tokenKey = HttpConfig.getHttpConfig().getToken();
//        String token = MMKVHelper.decodeString(tokenKey);
//        if (!TextUtils.isEmpty(token)){
//            Request updateRequest = request.newBuilder().header(tokenKey, token).build();
//            return chain.proceed(updateRequest);
//        }
        return chain.proceed(request);
    }
}
