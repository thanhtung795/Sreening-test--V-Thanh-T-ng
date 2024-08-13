-- Create a new database
CREATE DATABASE qlTeachers;

-- Use the newly created database
USE qlTeachers;

-- Create a table with specified columns
CREATE TABLE Teachers (
    Teacher_ID VARCHAR(30),
    Last_Name VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    First_Name VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    Teacher_Type BIT,
    Qualification VARCHAR(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    Base_Salary DECIMAL(10, 2),
    Employment_Date DATE,
    Employment_Status BIT,
    Image VARCHAR(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Add a primary key to the table
ALTER TABLE Teachers
ADD PRIMARY KEY (Teacher_ID);

-- Insert data into the table
INSERT INTO Teachers (Teacher_ID, Last_Name, First_Name, Teacher_Type, Qualification, Base_Salary, Employment_Date, Employment_Status, Image)
VALUES 
('tungvt', 'Võ Thanh', 'Tùng', 1, 'Cử nhân cao đẳng', 10000000.00, '2024-08-12', 1, 'avatar.jpg'),
('hanhvu', 'Hạnh Vũ', 'Quang', 0, 'Cử nhân đại học' , 12000000.00, '2024-07-20', 1, 'avatar.jpg'),
('thanhng', 'Thanh Nguyễn', 'Mai', 1,'Thạc sĩ', 15000000.00, '2024-06-15', 0, 'avatar.jpg'),
('hoangda', 'Hoàng Đạt', 'Dũng', 0, 'Tiến sĩ', 11000000.00, '2024-05-10', 1, 'avatar.jpg'),
('minhthu', 'Minh Thư', 'Hà', 1, 'Tiến sĩ', 13000000.00, '2024-04-25', 1, 'avatar.jpg');
/*
select Teacher_ID, 
Last_Name,
 First_Name, 
 Teacher_Type, 
 Qualification, 
 Base_Salary,
 Employment_Date,
 Employment_Status, 
 Image 
from Teachers;


delete from Teachers where Teacher_ID = 'T001';

UPDATE Teachers
SET Last_Name = 'Võ',
    First_Name = 'Trung',
    Teacher_Type = 1,
    Qualification = 'Cử nhân cao đẳng',
    Base_Salary = 100000,
    Employment_Date = '2024-05-10',
    Employment_Status = 0,  -- Sửa lại tên cột đúng
    Image = 'nguyenvana.png'
WHERE Teacher_ID = 'T001';

update Teachers set Image = 'avatar.jpg'
*/