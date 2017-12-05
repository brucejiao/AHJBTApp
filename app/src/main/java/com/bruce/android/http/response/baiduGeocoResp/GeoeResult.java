package com.bruce.android.http.response.baiduGeocoResp;

/**
 * Created by JiaoJianJun on 2017/9/1.
 */
public class GeoeResult {
    private String location;
    private String formatted_address;
    private String business;
    private String addressComponent;
    private String cityCode;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getAddressComponent() {
        return addressComponent;
    }

    public void setAddressComponent(String addressComponent) {
        this.addressComponent = addressComponent;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}