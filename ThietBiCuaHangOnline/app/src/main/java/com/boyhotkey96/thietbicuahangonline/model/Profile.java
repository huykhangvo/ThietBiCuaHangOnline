package com.boyhotkey96.thietbicuahangonline.model;

import java.io.Serializable;

public class Profile implements Serializable {

    private String unique_id;
    private String name;
    private String email;
    private String ngaytao;
    private String image;
    private String ngaysinh;
    private String sodienthoai;
    private String diachi;

    public Profile(String unique_id, String name, String email, String ngaytao, String image, String ngaysinh, String sodienthoai, String diachi) {
        this.unique_id = unique_id;
        this.name = name;
        this.email = email;
        this.ngaytao = ngaytao;
        this.image = image;
        this.ngaysinh = ngaysinh;
        this.sodienthoai = sodienthoai;
        this.diachi = diachi;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(String ngaytao) {
        this.ngaytao = ngaytao;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}
