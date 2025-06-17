#Supermarket Shift Planner (Console Application)

##Description
#Console-based Java application that provide:
-A single default ADMIN to log in and manage employees
-ADMIN can create EMPLOYEE users
-EMPLOYEEs can submit in wishbook shift preferences(ensuring exactly 2 different employees per day)
-ADMIN plan the daily schedule based on the employees preferences
-Both ADMIN and EMPLOYEES VIEW can view schedule for a specific date

Data is persistent in a H2 file-based database

**Default credentials:**
- URL: `jdbc:h2:./data/shiftplanner`
- User: `sa`
- Password: *(blank by default)

On first run, a default ADMIN(admin/admin123)

##Prerequisites
-Java 11+ instaled
-Maven instaled

##How to clone&structure
git clone https://github.com/mar363/shiftplannerJavaCore.git
cd shiftplannerJavaCore
shiftplannerJavaCore/
├── data/                     # Folder pentru fișierele de date (dacă există)
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── demo/
│       │           └── shiftplanner/
│       │               ├── controller/
│       │               ├── dao/
│       │               ├── exceptions/
│       │               ├── model/
│       │               ├── service/
│       │               └── util/
│       └── resources/       
├── target/
│ 
├── README.md
└── pom.xml 

###Build and Run
compile & build the project
mvn clean install

#run the application


