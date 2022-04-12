## MEDICAL RECORDS API
A medical Records application built with Java 8 (Spring Boot), MariaDB database, Spring Security with JWT authentication, Spring Method Security, Unit Testing with Mockito open source framework, Bean Validation, Custom Exception handling, Swagger Documentation and Liquibase data migration for migrating bulk sql script data into the database.

### REQUIREMENTS
For building and running the application you need:

JDK 1.8
Maven 3
Running the application locally
There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in the com.nandom.medicalrecords.app.MedicalRecordsApplication class from your IDE.

Alternatively you can use the Spring Boot Maven plugin like so:

`mvn spring-boot:run`

### TESTING THE LIVE API USING SWAGGER
[Click here to test the deployed api on swagger](http://63.250.53.24:9090/medicalrecords-api/swagger-ui.html)

### FOR POSTMAN COLLECTION
The postman collection for medical-records-api can be accessed [here](https://github.com/joenan/medical-records-api/blob/main/MEDICAL_RECORDS_API.postman_collection.json). Remember to copy the file and save it as a json file in this format. For example, MEDICAL_RECORDS_API.postman_collection.json

### ACCESSING THE APPLICATION
After running the application on your local system, the context path of the application can be accessed on the browser or on postman via this context path `http://localhost:9090/medicalrecords-api/`

### FOR SWAGGER DOCUMENTATION
Swagger documentation can be accessed via `http://localhost:9090/medicalrecords-api/swagger-ui.html/`

![Screenshot from 2022-04-11 10-22-05](https://github.com/joenan/medical-records-api/blob/main/images/MedicalAPISwagger.png)

### SIGNING IN TO MEDICAL RECORDS API
Send the JSON object below as a POST request to `http://localhost:9090/medicalrecords-api/v1/auth/signup` where role is either PATIENT, STAFF or ADMIN

```
{
  "email": "superuer@gmail.com",
  "password": "super123456",
  "role": [
    "STAFF"
  ],
  "username": "superuser"
}
```
And a success response will be with status code 200
```
{
    "code": 200,
    "message": "User registered successfully!"
}
```
### OBTAINING ACCESS TOKEN AFTER SIGNUP
The access token is needed in order to start calling endpoints. To get the access token, 
send a POST request with a sample requeest body below to `http://localhost:9090/medicalrecords-api/v1/auth/signin`

```
{
  "password": "super123456",
  "username": "superuser"
}
```
and the response if successful, will return with a response code of 200 with the JSON response below

```
{
  "id": 3,
  "username": "superuser",
  "loggedInstaffUuid": "40088146-a9a5-484d-8edc-15c4ecb13b67",
  "email": "superuer@gmail.com",
  "roles": [
    "ROLE_STAFF"
  ],
  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdXBlcnVzZXIiLCJpYXQiOjE2NDk2ODk4NDQsImV4cCI6MTY0OTc3NjI0NH0.QG705Zy-sgFGpYgjKcIY5uJO7exHu2OWe5gNJ8Il-9ciAx1nWlZWY2XRbq3fa5DvqN70lz5t9Kd3P6x7-rQn0Q",
  "type": "Bearer"
}
```

### TO CREATE NEW STAFF PROFILE
send a POST with the examble below to 
```
{
  "loggedInstaffUuid": "UUID",
  "name": "name"
}
```
where ``loggedInstaffUuid`` is the uuid of the loggedin staff and the response is shown below

```
{
  "code": 201,
  "message": "Staff record saved successfully",
  "data": {
    "id": 20,
    "name": "Ibrahim Musa",
    "staffUuid": "c155fa89-7a88-4bab-9909-6551267f8521",
    "age": null,
    "registrationDate": "2022-04-11"
  }
}
```
But if the ``loggedInstaffUuid`` is not valid, then you will get a response below

```
{
  "statusCode": 500,
  "timestamp": "2022-04-11T15:28:19.911+00:00",
  "message": "Access is denied",
  "description": "uri=/medicalrecords-api/api/v1/staff/profile/"
}
```
This is because, only despite being able to login, you cannot still create a new staff without a valid uuid

### TO UPDATE STAFF PROFILE
Send the below payload as a PUT request to `http://localhost:9090/medicalrecords-api/api/v1/staff/profile`

```
{
  "id": 1,
  "loggedInstaffUuid": "5e56b9f1-e166-400e-bb00-7691a09da063",
  "name": "Adetunji Adewale"
}
```
When successfull, you will get the response below

```
{
  "code": 200,
  "message": "Staff records have been updated successfully",
  "data": {
    "id": 1,
    "name": "Adetunji Adewale",
    "staffUuid": "6a37ec16-ecac-40ae-bcf0-0e47f86ac13c",
    "age": null,
    "registrationDate": "2022-04-11"
  }
}
```
but if the uuid of the logged in staff is invalid, you will get the response below
```
{
  "statusCode": 500,
  "timestamp": "2022-04-11T17:39:37.148+00:00",
  "message": "Access is denied",
  "description": "uri=/medicalrecords-api/api/v1/staff/profile"
}
```
### TO FETCH ALL PATIENTS PROFILE UPTO 2 YEARS
send a GET request with a sample below to `http://localhost:9090/medicalrecords-api/api/v1/patient/profiles/upto/{years}/?loggedInstaffUuid=5e56b9f1-e166-400e-bb00-7691a09da063`
where {years} in the path variable is the number of years to search for and the url parameter `loggedInstaffUuid` is the loggedin Staff uuid 

an example is beelow
`curl -X GET "http://localhost:9090/medicalrecords-api/api/v1/patient/profiles/upto/2/?loggedInstaffUuid=5e56b9f1-e166-400e-bb00-7691a09da063" -H "accept: */*" -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuYW5kb20iLCJpYXQiOjE2NDk2OTE5NjgsImV4cCI6MTY0OTc3ODM2OH0.HyR4C6EiK8WzIMq4RiBbXfTDc8UE7GAf3GM4EqmuQ5qUPlfjMt9ahkVTddZ-n0Oo9W70XWOyQpJTpsNyTE9dLw"`

if the above GET request is successful, you will receive the following response with a response code of 200

```
{
  "code": 200,
  "message": "List of all patients upto 2 year(s)",
  "data": [
    {
      "id": 8,
      "name": "Rebeeccah",
      "age": 1,
      "lastVisitDate": "2022-02-02"
    },
    {
      "id": 9,
      "name": "Alfred",
      "age": 1,
      "lastVisitDate": "2022-10-10"
    }
  ]
}
```

### TO DOWNLOAD SPECIFIC PATIENT PROFILE INTO CSV
send a GET request to `http://localhost:9090/medicalrecords-api/api/v1/patient/profile/csv/download/`
and pass `patientId` as a path variabl.And also add `loggedInstaffUuid` as a url parameter. Below is a sample

`http://localhost:9090/medicalrecords-api/api/v1/patient/profile/csv/download/{patentId}?loggedInstaffUuid=5e56b9f1-e166-400e-bb00-7691a09da063`

If the above query is successful, it will return the csv below

`1,Philips,31,2022-04-11`

### TO DELETE MULTIPLE PATIENT PROFILE BETWEEN DATE RANGE
send a DELETE request to `http://localhost:9090/medicalrecords-api/api/v1/patient/profiles/delete` with the request body below

```
{
  "dateFrom": "2022-04-11",
  "dateTo": "2022-04-11",
  "loggedInstaffUuid": "5e56b9f1-e166-400e-bb00-7691a09da063"
}
```
if successful, it will return a response code of 200 with the response message below
```
{
  "code": 200,
  "message": "6 Patient(s) records have been deleted"
}
```

## To steps below shows how to use Postman to interact with the api

### TO SIGNUP FOR MEDICAL RECORDS API
![Screenshot from 2022-04-11 10-22-05](https://github.com/joenan/medical-records-api/blob/main/images/Signup.png)


### TO SIGNIN AND OBTAIN ACCESS TOKEN
![Screenshot from 2022-04-11 10-22-05](https://github.com/joenan/medical-records-api/blob/main/images/Signin.png)


### TO PUT THE ACCESS TOKEN IN THE AUTHORIZATION HEADER
![Screenshot from 2022-04-11 10-22-05](https://github.com/joenan/medical-records-api/blob/main/images/Authorization.png)


### TO CREATE NEW STAFF PROFILE
![Screenshot from 2022-04-11 10-22-05](https://github.com/joenan/medical-records-api/blob/main/images/CreateStaff.png)


### TO UPDATE AN EXISTING STAFF PROFILE
![Screenshot from 2022-04-11 10-22-05](https://github.com/joenan/medical-records-api/blob/main/images/UpdateStaff.png)


### TO FETCH ALL PATIENTS UPTO 2 YEARS
![Screenshot from 2022-04-11 10-22-05](https://github.com/joenan/medical-records-api/blob/main/images/PatientProfileUpto2years.png)


### TO DOWNLOAD A SPECIFIC PATIENTS PROFILE INTO CSV
![Screenshot from 2022-04-11 10-22-05](https://github.com/joenan/medical-records-api/blob/main/images/PatientCSVProfileDownload.png)


### TO DELETE MULTIPLE PATIENT PROFILES BETWEEN DATE RANGE
![Screenshot from 2022-04-11 10-22-05](https://github.com/joenan/medical-records-api/blob/main/images/DeleteMultiplePatientProfileByDateRange.png)
