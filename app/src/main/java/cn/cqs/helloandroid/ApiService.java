package cn.cqs.helloandroid;

import cn.cqs.http.BaseResponse;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {
     /********************************* 示例代码 ****************************************/
    @GET("http://v.juhe.cn/toutiao/index?key=238ca532342eb15fb820f1f7fe08c2d6")
    Observable<BaseResponse> testApi(@Query("type") String type);

    /**
     * 上传多张图片
     * @param files
     * @return
     */
//    @Multipart
//    @POST("api/uploads/trace")
//    Observable<BaseResponse<UploadBean>> uploadImages(@Part() List<MultipartBody.Part> files);

    /**
     * 文件下载
     * @param url
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}