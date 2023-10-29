# springboot-3rdparty-api-service
This Spring Boot project demonstrates the integration of third-party APIs for user authentication and customer management. The project includes API endpoints and a basic web interface with three screens: Login, Customer List, and Add a New Customer.

User Authentication:

POST request to authenticate a user using Bearer authentication.
Path: https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp
Method: POST
Body:
{
  "login_id" : "test@sunbasedata.com",
  "password" :"Test@123"
}
Receives a bearer token for subsequent API calls.
Create a New Customer:
POST request to create a new customer.
Path: https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp
Method: POST
Parameters:
cmd: create
Header:
Authorization: Bearer token_received_in_authentication_API_call
Request body:
{
  "first_name": "Jane",
  "last_name": "Doe",
  "street": "Elvnu Street",
  "address": "H no 2",
  "city": "Delhi",
  "state": "Delhi",
  "email": "sam@gmail.com",
  "phone": "12345678"
}
Response:
Success: 201, Successfully Created
Failure: 400, First Name or Last Name is missing

Get Customer List:

GET request to retrieve a list of customers.
Path: https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp
Method: GET
Parameters:
cmd: get_customer_list
Header:
Authorization: Bearer token_received_in_authentication_API_call
Response: 200
[  {    "first_name": "Jane",    "last_name": "Doe",    "street": "Elvnu Street",    "address": "H no 2",    "city": "Delhi",    "state": "Delhi",    "email": "sam@gmail.com",    "phone": "12345678"  }]
Delete a Customer:

POST request to delete a specific customer.
Path: https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp
Method: POST
Parameters:
cmd: delete
uuid: UUID of a specific customer
Header:
Authorization: Bearer token_received_in_authentication_API_call
Response:
200, Successfully deleted
500, Error Not deleted
400, UUID not found

Update a Customer:

POST request to update a specific customer.
Path: https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp
Method: POST
Parameters:
cmd: update
uuid: UUID of a specific customer
Header:
Authorization: Bearer token_received_in_authentication_API_call
Request body:
{
  "first_name": "Jane",
  "last_name": "Doe",
  "street": "Elvnu Street",
  "address": "H no 2",
  "city": "Delhi",
  "state": "Delhi",
  "email": "sam@gmail.com",
  "phone": "12345678"
}
Response:
200, Successfully Updated
500, UUID not found
400, Body is Empty
