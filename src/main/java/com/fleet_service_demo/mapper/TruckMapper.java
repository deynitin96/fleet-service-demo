package com.fleet_service_demo.mapper;

import com.fleet_service_demo.entity.Truck;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface TruckMapper {

@Insert("""
        INSERT INTO truck (truck_number,
                             truck_type,
                              status,
                              location,
                              mileage,
                              model,
                              manufacturing_year)
               VALUES (#{truckNumber},
                          #{truckType},
                          #{status},
                          #{location},
                          #{mileage},
                          #{model},
                          #{manufacturingYear}
                          )
        """)
@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
int insert(Truck truck);

}
