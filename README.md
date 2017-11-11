# TwitterApp

Twitter App is and application that can allow the user to login to twitter and show the home timeline and 
can like tweet , retweet and share tweet as well.

Application architecture : 
* Feature separation by packages , 
Instead of using a package by layer approach, application structured by package per feature. This greatly improves readability and modularizes the app in a way that parts of it can be changed independently from each other. Each key feature of the app is in its own Java package

*  Application follow MVP Design Pattern.

Application libraries : 
* ButterKnife for injecting views
* GSON for json parsing
* retrofit for network calls
* Picasso for image loading



***



_Login Screen_**. 


![ScreenShot](https://raw.github.com/MohamedElgendyGits/TwitterApp/master/screenshots/Screenshot1.png)


_Timeline Screen_**. 


![ScreenShot](https://raw.github.com/MohamedElgendyGits/TwitterApp/master/screenshots/Screenshot2.png)



