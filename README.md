// DB and Table creation
CREATE DATABASE fleet_db;

USE fleet_db;

create table truck(
id bigint primary key auto_increment,
truck_number varchar(255) not null unique,
truck_type varchar(55) not null,
status varchar(55) not null,
location varchar(100) not null,
mileage bigint default 0,
model varchar(100),
manufacturing_year int,
created_at timestamp default current_timestamp,
updated_at timestamp default current_timestamp
);

INSERT INTO truck
(truck_number, truck_type, status, location, mileage, model, manufacturing_year)
VALUES
('TRK-1001', 'HEAVY_DUTY', 'AVAILABLE', 'HYDERABAD', 45000, 'Freightliner', 2024),
('TRK-1002', 'MEDIUM_DUTY', 'RENTED', 'PUNE', 32000, 'Isuzu', 2023),
('TRK-1003', 'LIGHT_DUTY', 'AVAILABLE', 'HYDERABAD', 21000, 'Ford', 2025),
('TRK-1004', 'HEAVY_DUTY', 'MAINTENANCE', 'BANGALORE', 78000, 'Volvo', 2022);

select * from truck;

// Created Stored Procedure

DELIMITER $$

CREATE PROCEDURE get_fleet_summary()
BEGIN

    SELECT
        COUNT(*) AS totalTrucks,
        SUM(CASE WHEN status = 'AVAILABLE' THEN 1 ELSE 0 END) AS availableTrucks,
        SUM(CASE WHEN status = 'RENTED' THEN 1 ELSE 0 END) AS rentedTrucks,
        SUM(CASE WHEN status = 'MAINTENANCE' THEN 1 ELSE 0 END) AS maintenanceTrucks
    FROM truck;

END $$

DELIMITER ;

CALL get_fleet_summary();



API Documentation: https://documenter.getpostman.com/view/57559450/2sBYAuRAjB 
