package com.boyhotkey96.thietbicuahangonline.model;

import java.io.Serializable;

public class SanPham implements Serializable {

    public int masanpham;
    public String tensanpham;
    public Integer giasanpham;
    public String hinhanhsanpham;
    public String motasanpham;
    public int maloaisanpham;

    public SanPham() {

    }

    public SanPham(int masanpham, String tensanpham, Integer giasanpham, String hinhanhsanpham, String motasanpham, int maloaisanpham) {
        this.masanpham = masanpham;
        this.tensanpham = tensanpham;
        this.giasanpham = giasanpham;
        this.hinhanhsanpham = hinhanhsanpham;
        this.motasanpham = motasanpham;
        this.maloaisanpham = maloaisanpham;
    }

    public int getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(int masanpham) {
        this.masanpham = masanpham;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public Integer getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(Integer giasanpham) {
        this.giasanpham = giasanpham;
    }

    public String getHinhanhsanpham() {
        return hinhanhsanpham;
    }

    public void setHinhanhsanpham(String hinhanhsanpham) {
        this.hinhanhsanpham = hinhanhsanpham;
    }

    public String getMotasanpham() {
        return motasanpham;
    }

    public void setMotasanpham(String motasanpham) {
        this.motasanpham = motasanpham;
    }

    public int getMaloaisanpham() {
        return maloaisanpham;
    }

    public void setMaloaisanpham(int maloaisanpham) {
        this.maloaisanpham = maloaisanpham;
    }
}
