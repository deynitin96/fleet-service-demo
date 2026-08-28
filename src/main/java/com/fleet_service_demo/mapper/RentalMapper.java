package com.fleet_service_demo.mapper;

import com.fleet_service_demo.entity.Rental;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface RentalMapper {

    @Insert("""
            INSERT INTO rental (truck_id,
                                 customer_name,
                                 rental_start_date,
                                 expected_return_date,
                                 status)
                   VALUES (#{truckId},
                              #{customerName},
                              #{rentalStartDate},
                              #{expectedReturnDate},
                              #{status})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int createRental(Rental rental);
}
