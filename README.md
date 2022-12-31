# Day Trainer

## Deployed Site
http://3.229.131.97:8080/DayTrainer/#/home

## Description

For our final project to culminate the completion of our learning experience at Skill Distillery Full-Stack Software Development course, our team of developers created Day Trainer. Day Trainer is an investing application that allows users to trade stocks with simulated money. It allows users to gain experience trading and investing without the high stakes. Users can place buy and sell orders and track their accounts performance over time.

The motivation for this project comes from personal interactions with friends who have stated that they have always wanted to get started with investing and either didn't have the money to spare or they didn't feel like they had the knowledge and experience to get started on their own. Day Trainer is the perfect platform to get experience buying and selling stocks without having to risk any money. The ability of a user to track their performance over time helps fine tune strategies and shows a user real metrics for measuring growth. With Day Trainer, failure only means learning from your mistakes. After using Day Trainer, we are confident our users will feel ready to invest for real, and will have the confidence to make bold decisions and calculate risk.

Day Trainer also welcomes social interaction. Users can also work towards making their way onto our leaderboard, which displays users ranked in order by greatest return on their trades.

The site consumes two 3rd Party APIs in order to leverage real time stock data. This way each trade transaction mimics real world trades and track realistic metrics based on market data.

---
## Key Features

* User Dashboard - Users personalized account data

* Top Movers- Page that displays the top 10 stock gainers and "top" 10 stock losers of the previous day

* Make a trade- Page for individual Stock with interactive candle stick chart and data. Form for user to buy/sell

* Leader Board- Hierarchical display that ranks users with the most profitable accounts

* Account Settings- Allows user to update profile information

---
## Lessons Learned

This was our first project implementing Angular technology with TypeScript. It was a good learning experience creating a second full-stack application utilizing different technologies than our previous projects. This project also allowed us the opportunity to learn how to implement third party APIs that gives us the ability to incorporate and display real-time data from the internet.

Another new lesson learned while working on this project was how to develop a method that was set to execute on a schedule. We used this to facilitate user account deposits on a weekly basis. We did this using the @Scheduled annotation in a method in our User Controller. This method has a fixedDelay variable, which  correlated to the number of days in the (TimeUnit.DAYS) - designating the amount of time between method execution.

---
## Key Technologies Used

* Angular - TypeScript
* Java
* JavaScript
* CSS
* HTML
* MySQL
* SpringBoot
* Bootstrap
* JPA
* GitHub
* AWS
* Trello
* Figma
* Slack
* Zoom
* Gradle
* Object-Relational Mapping (ORM)
* Object-Oriented Programming
* Test Driven Development using JUNIT Juniper
* Postman

---
## Meet the Team

Cecelia Guerrero

* developer
* GitHub: https://github.com/Cagugu
* LinkedIn: https://www.linkedin.com/in/cecelia-guerrero/

Anthony Butler

* Database Administrator / developer
* GitHub: https://github.com/anthonyb0824
* LinkedIn: https://www.linkedin.com/in/anthony-tyler-butler/

Miguel Barrios

* developer
* GitHub: https://github.com/MiguelBarrios
* LinkedIn: https://www.linkedin.com/in/miguelbarriosdavila/

Daniel Kregstein

* developer
* GitHub: https://github.com/Akregstein
* LinkedIn: https://www.linkedin.com/in/daniel-kregstein-364883240/
