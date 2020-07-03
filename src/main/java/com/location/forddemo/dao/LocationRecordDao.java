package com.location.forddemo.dao;


import com.location.forddemo.entity.LocationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRecordDao extends JpaRepository<LocationRecord, Long> {
    interface LocationRecordDTO {
        Integer getUserId();
        String getLocationTypeId();
        String getLocationName();
        String getNumberOfSelect();
        String getLocationUid();
        String getLocationTypeName();
    }

    //    @Query(value="select new com.location.forddemo.entity.LocationRecord(recordId,userId,latitude,longitude,locationName) from LocationRecord",nativeQuery = true)
    @Query(value="SELECT 	a.user_id AS userId, 	a.location_Type_Id AS LocationTypeId, 	a.location_name AS locationName, 	a.number_Of_Select AS numberOfSelect, 		lt.location_type_name AS locationTypeName FROM 	( 		SELECT 			v1.user_id, 			v1.location_Type_Id, 			v1.c AS number_Of_Select, 			v2.location_name 		FROM 			( 				SELECT 					user_id, 					location_Type_Id, 					MAX(c) AS c 				FROM 					( 						SELECT 							user_id, 							location_name, 							location_Type_Id, 							COUNT(*) AS c 						FROM 							location_record 						GROUP BY 							user_id, 							location_name, 							location_Type_Id 					) v 				GROUP BY 					user_id, 					location_Type_Id 			) v1, 			( 				SELECT 					user_id, 					location_name, 					location_Type_Id, 					COUNT(*) AS c 				FROM 					location_record 				GROUP BY 					user_id, 					location_name, 					location_Type_Id 			) v2 		WHERE 			( 				v1.user_id = v2.user_id 				AND v1.location_Type_Id = v2.location_Type_Id 				AND v1.c = v2.c 			) 	) a  LEFT JOIN Location_Type lt ON lt.location_Type_Id = a.location_Type_Id",nativeQuery = true)
    List<LocationRecordDTO> getCommonLocation();
}
