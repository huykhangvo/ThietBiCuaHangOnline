package com.boyhotkey96.thietbicuahangonline.retrofit2;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

//gui nhung yeu cau len cho Server (get, put)
public interface DataClient {

    @Multipart
    @POST("uploadhinh.php")
    Call<String> UploadPhoto(@Part MultipartBody.Part photo);

    @FormUrlEncoded
    @POST("inserthinh.php")
    Call<String> InsertData(@Field("unique_id") String unique_id,
                            @Field("image") String hinhanh,
                            @Field("name") String name,
                            @Field("ngaysinh") String ngaysinh,
                            @Field("sodienthoai") String sodienthoai,
                            @Field("diachi") String diachi);
}
