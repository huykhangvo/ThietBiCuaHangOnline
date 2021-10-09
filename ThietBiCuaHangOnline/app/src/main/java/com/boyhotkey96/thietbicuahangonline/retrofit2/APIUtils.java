package com.boyhotkey96.thietbicuahangonline.retrofit2;

public class APIUtils {
    public static final String Base_Url = "http://192.168.1.6:8888/dbthietbicuahangonline/";
    public static DataClient getData() {
        return RetrofitClient.getClient(Base_Url).create(DataClient.class);
    }
}
