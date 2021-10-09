package com.boyhotkey96.thietbicuahangonline.ultil;

import com.boyhotkey96.thietbicuahangonline.retrofit2.DataClient;
import com.boyhotkey96.thietbicuahangonline.retrofit2.RetrofitClient;

public class Server {
    public static String localhost = "10.200.202.56:8888";
    //public static String ddloaisanpham = "http://" + localhost + "/dbthietbicuahangonline/getloaisanpham.php";
    public static String ddsanphammoinhat = "http://" + localhost + "/dbthietbicuahangonline/getsanphammoinhat.php";
    public static String dddienthoai = "http://" + localhost + "/dbthietbicuahangonline/getsanpham.php?page=";
    public static String dddonhang = "http://" + localhost + "/dbthietbicuahangonline/thongtinkhachhang.php";
    public static String ddchitietdonhang = "http://" + localhost + "/dbthietbicuahangonline/chitietdonhang.php";
    public static String ddprofile = "http://" + localhost + "/dbthietbicuahangonline/getusers.php";
    public static String ddupdateprofile = "http://" + localhost + "/dbthietbicuahangonline/updateprofile.php";
    public static String ddinsertyeuthich = "http://" + localhost + "/dbthietbicuahangonline/insertyeuthich.php";
    public static String ddgetyeuthich = "http://" + localhost + "/dbthietbicuahangonline/getyeuthich.php";
    public static String dddeleteyeuthich = "http://" + localhost + "/dbthietbicuahangonline/deleteyeuthich.php";
}