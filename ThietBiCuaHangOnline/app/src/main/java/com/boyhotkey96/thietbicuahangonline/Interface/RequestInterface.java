package com.boyhotkey96.thietbicuahangonline.Interface;

import com.boyhotkey96.thietbicuahangonline.model.ServerRequest;
import com.boyhotkey96.thietbicuahangonline.model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {
    @POST("dbthietbicuahangonline/index.php")
    Call<ServerResponse> operation(@Body ServerRequest request);
}
