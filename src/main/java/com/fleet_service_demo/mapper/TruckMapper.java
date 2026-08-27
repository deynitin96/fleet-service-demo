package com.fleet_service_demo.mapper;

import com.fleet_service_demo.dto.FleetSummaryResponse;
import com.fleet_service_demo.entity.Truck;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

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

@Select("""
        SELECT id,
               truck_number AS truckNumber,
               truck_type AS truckType,
               status,
               location,
               mileage,
               model,
               manufacturing_year AS manufacturingYear
        FROM truck
        """)
List<Truck> findAll();

@Select("""
        SELECT id,
               truck_number AS truckNumber,
               truck_type AS truckType,
               status,
               location,
               mileage,
               model,
               manufacturing_year AS manufacturingYear
        FROM truck
        WHERE status = 'AVAILABLE'
        """)
List<Truck> findAvailableTrucks();

@Select("""
        SELECT id,
               truck_number AS truckNumber,
               truck_type AS truckType,
               status,
               location,
               mileage,
               model,
               manufacturing_year AS manufacturingYear
        FROM truck
        WHERE id = #{id}
        """)
Optional<Truck> findById(long id);

@Select("""
        SELECT id,
               truck_number AS truckNumber,
               truck_type AS truckType,
               status,
               location,
               mileage,
               model,
               manufacturing_year AS manufacturingYear
        FROM truck
        WHERE location = #{location}
        """)
List<Truck> findByLocation(String location);

@Select("""
        {CALL get_fleet_summary()}
        """)
FleetSummaryResponse getFleetSummary();
}
