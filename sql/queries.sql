# ---------------------------------------------------------------------- #
# Step 4: Test the queries.                                              #
# ---------------------------------------------------------------------- # 

# Query 1: Get all dealerships
SELECT * FROM `dealerships`;

# Query 2: Find all vehicles for a specific dealership
SELECT v.* FROM `vehicles` v
JOIN `inventory` i ON v.`VIN` = i.`VIN`
WHERE i.`dealership_id` = 1;

# Query 3: Find a car by VIN 
SELECT * FROM `vehicles` WHERE `VIN` = '1HGCM82633A123456';

# Query 4: Find the dealership where a certain car is located, by VIN 
SELECT d.* FROM `dealerships` d
JOIN `inventory` i ON d.`dealership_id` = i.`dealership_id`
WHERE i.`VIN` = '1HGCM82633A123456';

# Query 5: Find all Dealerships that have a certain car type 
SELECT DISTINCT d.* FROM `dealerships` d
JOIN `inventory` i ON d.`dealership_id` = i.`dealership_id`
JOIN `vehicles` v ON i.`VIN` = v.`VIN`
WHERE v.`make` = 'Ford' AND v.`model` = 'Mustang' AND v.`color` = 'Red';

# Query 6: Get all sales information for a specific dealer for a specific date range 
SELECT s.* FROM `sales_contracts` s
JOIN `inventory` i ON s.`VIN` = i.`VIN`
WHERE i.`dealership_id` = 1 AND s.`sale_date` BETWEEN '2024-01-01' AND '2024-12-31';