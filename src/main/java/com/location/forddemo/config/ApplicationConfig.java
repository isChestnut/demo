package com.location.forddemo.config;

import com.location.forddemo.dao.LocationRecordDao;
import com.location.forddemo.dao.LocationTypeDao;
import com.location.forddemo.entity.LocationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=1)
public class ApplicationConfig implements ApplicationRunner{
    @Autowired
    private LocationTypeDao locationTypeDao;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            LocationType LocationTypeGS=new LocationType(new Long(1001),"加油站");
            LocationType LocationTypeShop=new LocationType(new Long(1002),"4s店");
            locationTypeDao.save(LocationTypeShop);
            locationTypeDao.save(LocationTypeGS);
            System.out.println("ApplicationRunner钩子被使用了");
            System.out.println(locationTypeDao.findAll().toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
