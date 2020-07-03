package com.location.forddemo.controller;


import com.location.forddemo.entity.LocationRecord;
import com.location.forddemo.entity.LocationType;
import com.location.forddemo.entity.User;
import com.location.forddemo.dao.LocationRecordDao;

import com.location.forddemo.util.ParamUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/location")
public class LocationRecordController {
    static {

    }
    @Autowired
    private LocationRecordDao locationRecordDao;


    @RequestMapping("/list")
    public String list(){
        return "list";
    }
    @RequestMapping("/getCommonLocationByUser")
    @ResponseBody
    public List<LocationRecordDao.LocationRecordDTO> getCommonLocationByUser(){
        List<LocationRecordDao.LocationRecordDTO> list=locationRecordDao.getCommonLocation();
        return list;
    }


    @RequestMapping(value="/addLocationRecord",method = RequestMethod.POST)
    @ResponseBody
    public String addLocationRecord(@RequestBody String  locationArray, HttpSession session){
        List<Map<String,Object>> locationList=null;
        LocationRecord locationRecord=null;
        User user=null;
        try {

            user=(User)session.getAttribute("user");
            locationArray=URLDecoder.decode(locationArray, "utf-8").toString();
            JSONArray obj=JSONArray.fromObject(locationArray);
            locationList=JSONArray.toList(obj,Map.class);
            for (Map<String,Object> map:
                    locationList) {
                locationRecord=new LocationRecord();
                locationRecord.setUserId(user.getUserId());
              /*  Map point= (Map)  JSONObject.toBean(JSONObject.fromObject(map.get("point")));
                locationRecord.setLongitude(new BigDecimal(point.get("lng").toString()));
                locationRecord.setLongitude(new BigDecimal(point.get("lat").toString()));*/
                locationRecord.setLocationName(map.get("title").toString());
                locationRecord.setLocationUid(map.get("uid").toString());
                locationRecord.setLocationTypeId(ParamUtil.returnLocationType(map.get("title").toString()));
                locationRecordDao.save(locationRecord);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return locationRecordDao.findAll().toString();
    }
}
