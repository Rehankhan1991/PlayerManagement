# PlayerManagement


Step 1: Checkout main branch https://github.com/Rehankhan1991/PlayerManagement and pull the project
Step 2: Root folder of the project is named as GameManagement.Import the project in Eclipse as a gradel project and refresh the project by right click and selecting gradel option.
Step 3: Postman Test
          Step 1: Run spring Application the main class is named as GameManagementApplication right click and run java application server will start at port 8080
          Step 2: In postman base reference will be http://localhost:8080/ and Root End Point is configured with /players
                  So in order to call any API  http://localhost:8080/players/ then API End Point
                  For example: http://localhost:8080/players/createScore and 
                                sample request body {"player":"Virat","score":90,"time":"2020-03-01 16:49:53"}
Step 4: in order to run junit test cases navigate to src/test/java package right click on PlayerControllerTest class and run as junit test
