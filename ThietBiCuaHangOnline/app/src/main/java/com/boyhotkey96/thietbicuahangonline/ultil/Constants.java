package com.boyhotkey96.thietbicuahangonline.ultil;

import com.boyhotkey96.thietbicuahangonline.retrofit2.DataClient;
import com.boyhotkey96.thietbicuahangonline.retrofit2.RetrofitClient;

public class Constants {
    public static final String BASE_URL = "http://10.200.202.56:8888/";
    public static final String REGISTER_OPERATION = "register";
    public static final String LOGIN_OPERATION = "login";
    public static final String CHANGE_PASSWORD_OPERATION = "chgPass";
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String UNIQUE_ID = "unique_id";
    public static final String TAG = "LearnAPI";

    public static final String Base_Url = BASE_URL + "dbthietbicuahangonline/";
    public static DataClient getData() {
        return RetrofitClient.getClient(Base_Url).create(DataClient.class);
    }
}
