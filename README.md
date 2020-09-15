# Restaurant voting system
Design and implement a REST API using Hibernate/Spring/SpringMVC **without frontend**.

## The task is:
Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

## API documentation and couple curl commands
Application deployed at application context `lunch`
Test login and password:
 * admin@gmail.com:admin   
 * user1@yandex.ru:password
 * user2@yandex.ru:password
 * user3@yandex.ru:password
 * user4@yandex.ru:password
 * user5@yandex.ru:password

### For Work
**server tomcat, db- HSQLDB(inmemory), maven**


#### Required commands  for test application:

 Сreate restaurant:
http://localhost:8080/restaurant/admin/create

Create meal
http://localhost:8080/restaurant/admin/{restaurantId}/menu/addMeal/

Update meal
http://localhost:8080/restaurant/admin/{restaurantId}/menu/upMeal/{mealId}

Update restaurant name
http://localhost:8080/restaurant/admin/updateName/{restaurantId}

Get all restaurants with a menu 
http://localhost:8080/restaurant/

Get restaurant
http://localhost:8080/restaurant/admin/{restaurantId}

Delete meal
http://localhost:8080/restaurant/admin/{restaurantId}/menu/deleteMeal/{mealId}

Delete restaurant
http://localhost:8080/restaurant/admin/{restaurantId}

Get vote restaurant
http://localhost:8080/restaurant/admin/vote/{restaurantId}

Get all vote in map ({restaurantId}:countVote)
http://localhost:8080/restaurant/admin/allvote

Save vote
http://localhost:8080/restaurant/vote/{restaurantId}

