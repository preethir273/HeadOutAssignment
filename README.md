How to run:
Download Maven dependencies for the project.
Run the "testng.xml" as TestNG Suite

Assumptions:
- Price of all product is not a decimal number.  
- Test cases will work for search = "shoes". Might not work for other search eg:mobiles, as the results are displayed differently
- Available first data is chosen for tests

Cases/conditions not handled:
- Seats selection is not done based on the category (color)
- some data flow between pages is not verified (eg: selected date, seats are reflected in the next page)
- Wait is not handled properly. 2 to 10 seconds of wait using Thread.sleep() is used to handle delay.
- Logs are not maintained
