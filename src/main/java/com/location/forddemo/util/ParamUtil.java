package com.location.forddemo.util;

public class ParamUtil {
    public static String client_id="f8dc1e0632e6559c96e8";
    public static String client_secret="0b472665e1b0ecb2fac9e9da0e0b15e219bda5c2";
    public static String callback_uri="http://127.0.0.1:8090/account/github/callback";

    public static String returnLocationType(String name){
        if(name.indexOf("加油")>0){
            return PUBParam.LOCATION_TYPE_GS;
        }else if(name.indexOf("汽车")>0){
            return PUBParam.LOCATION_TYPE_SHOP;
        }else{
            return "0";
        }
    }
}
