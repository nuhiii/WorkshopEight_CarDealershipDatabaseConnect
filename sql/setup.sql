# ---------------------------------------------------------------------- #
# Step 1: Create the database.                                           #
# ---------------------------------------------------------------------- #

DROP DATABASE IF EXISTS dealership;
CREATE DATABASE dealership;
USE dealership;

# ---------------------------------------------------------------------- #
# Step 2: Create the tables.                                             #
# ---------------------------------------------------------------------- # 

# Table 1: dealerships
CREATE TABLE `dealerships` (
    `dealership_id` INTEGER NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `address` VARCHAR(50) NOT NULL,
    `phone` VARCHAR(12) NOT NULL,
    CONSTRAINT `PK_dealerships` PRIMARY KEY (`dealership_id`)
);

# Table 2: vehicles
CREATE TABLE `vehicles` (
    `VIN` VARCHAR(17) NOT NULL,
    `make` VARCHAR(50) NOT NULL,
    `model` VARCHAR(50) NOT NULL,
    `year` INTEGER NOT NULL,
    `color` VARCHAR(50),
    `mileage` INTEGER,
    `price` DECIMAL(10, 2),
    `SOLD` BOOLEAN DEFAULT FALSE,
    CONSTRAINT `PK_vehicles` PRIMARY KEY (`VIN`)
);

# Table 3: inventory (track which dealership has the vehicle)
CREATE TABLE `inventory` (
    `dealership_id` INTEGER NOT NULL,
    `VIN` VARCHAR(17) NOT NULL,
    CONSTRAINT `PK_inventory` PRIMARY KEY (`dealership_id`, `VIN`),
    CONSTRAINT `FK_inventory_dealerships` FOREIGN KEY (`dealership_id`) REFERENCES `dealerships` (`dealership_id`),
    CONSTRAINT `FK_inventory_vehicles` FOREIGN KEY (`VIN`) REFERENCES `vehicles` (`VIN`)
);

# Table 4: sales_contracts
CREATE TABLE `sales_contracts` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `VIN` VARCHAR(17) NOT NULL,
    `sale_date` DATETIME,
    `sale_price` DECIMAL(10, 2),
    `customer_name` VARCHAR(50),
    `customer_phone` VARCHAR(12),
    CONSTRAINT `PK_sales_contracts` PRIMARY KEY (`id`),
    CONSTRAINT `FK_sales_contracts_vehicles` FOREIGN KEY (`VIN`) REFERENCES `vehicles` (`VIN`)
);

# Table 5 (OPTIONAL): lease_contracts
CREATE TABLE `lease_contracts` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `VIN` VARCHAR(17) NOT NULL,
    `lease_start_date` DATETIME,
    `lease_end_date` DATETIME,
    `monthly_payment` DECIMAL(10, 2),
    `customer_name` VARCHAR(50),
    `customer_phone` VARCHAR(12),
    CONSTRAINT `PK_lease_contracts` PRIMARY KEY (`id`),
    CONSTRAINT `FK_lease_contracts_vehicles` FOREIGN KEY (`VIN`) REFERENCES `vehicles` (`VIN`)
);

# ---------------------------------------------------------------------- #
# Step 3: Populate the tables.                                           #
# ---------------------------------------------------------------------- # 

# Sample data 1 for dealerships
INSERT INTO `dealerships` (`name`, `address`, `phone`) VALUES 
('Best Cars', '123 Main St', '555-1234'),
('Auto World', '456 Elm St', '555-5678'),
('Car Superstore', '789 Oak St', '555-9012'),
('Premium Autos', '321 Maple St', '555-3456'),
('Budget Cars', '654 Pine St', '555-7890');

# Sample data 2 for vehicles
INSERT INTO `vehicles` (`VIN`, `make`, `model`, `year`, `color`, `mileage`, `price`, `SOLD`) VALUES 
('1HGCM82633A123456', 'Honda', 'Civic', 2020, 'Blue', 15000, 20000.00, FALSE),
('1HGCM82633A123457', 'Toyota', 'Corolla', 2019, 'Black', 20000, 18000.00, FALSE),
('1HGCM82633A123458', 'Ford', 'Mustang', 2021, 'Red', 5000, 30000.00, TRUE),
('1HGCM82633A123459', 'Chevrolet', 'Malibu', 2018, 'White', 30000, 15000.00, FALSE),
('1HGCM82633A123460', 'Nissan', 'Altima', 2022, 'Silver', 10000, 25000.00, FALSE),
('1HGCM82633A123461', 'BMW', 'X5', 2020, 'Blue', 20000, 50000.00, TRUE),
('1HGCM82633A123462', 'Audi', 'A4', 2021, 'Black', 12000, 35000.00, FALSE),
('1HGCM82633A123463', 'Tesla', 'Model 3', 2022, 'Red', 5000, 45000.00, FALSE);

# Sample data 3 for inventory
INSERT INTO `inventory` (`dealership_id`, `VIN`) VALUES 
(1, '1HGCM82633A123456'),
(2, '1HGCM82633A123457'),
(3, '1HGCM82633A123458'),
(1, '1HGCM82633A123459'),
(2, '1HGCM82633A123460'),
(3, '1HGCM82633A123461'),
(4, '1HGCM82633A123462'),
(5, '1HGCM82633A123463');

# Sample data 4 for sales_contracts
INSERT INTO `sales_contracts` (`VIN`, `sale_date`, `sale_price`, `customer_name`, `customer_phone`) VALUES 
('1HGCM82633A123458', '2024-01-15', 29500.00, 'John Doe', '555-1234'),
('1HGCM82633A123461', '2024-03-10', 49000.00, 'Alice Smith', '555-6789'),
('1HGCM82633A123459', '2024-07-22', 14000.00, 'Eve Davis', '555-2345');

# Sample data 5 for (optional) lease_contracts 
INSERT INTO `lease_contracts` (`VIN`, `lease_start_date`, `lease_end_date`, `monthly_payment`, `customer_name`, `customer_phone`) VALUES 
('1HGCM82633A123457', '2024-02-01', '2027-02-01', 350.00, 'Jane Smith', '555-5678'),
('1HGCM82633A123460', '2024-05-01', '2027-05-01', 400.00, 'Bob Johnson', '555-3456');