# The Adidas Confirmed Android Challenge

## Completed Tasks
- [x] App resilence,
- [x] App stability,
- [x] Testing,
- [x] App architecture 
- [x] UI/UX ,
- [x] Building app,

The app consists of two pages implemented with fragments
- Product listing page
- Product details page

## App resilience 
- Local broadcast receiver implemented to check for network connection and a snack bar will popping out when there is no networkm connectivity 
- The page loaded will get displayed and it will not be moved to the exception screen.
- In absence of network connectivity opening the app or moving to a different page will display network exception screen 
- During server error as well the same above work flow is followed.
- The app will work in both the orientations 
- With MVVM and live data, the data is cached

## App stability 
- I had implemented Acra for logging crash analytics that needs to sent to the developer by the user action via email.
- But we should implement firebase crash analytics for logging and analyzing crashes in the app.
- For the stability of the app, the app has gone to the testing process -  viewModels, repository, service, and room Dao has been tested.

## Testing
- The ViewModel has been unit tested
- The Repository has been unit tested
- The service API has been unit tested with a mock web server
- The instrumentation testing is done for Room Dao
- The Ui testing for pages are done using Espresso

## App architecture
- Have used Clean with MVVM architecture
- Followed Solid Principle
- Rxjava for network calls
- Coroutine for room calls
- Retrofit library for API calls
- Navigation using navigation jetpack component
- Room jetpack component to store the favorite products 
- Dagger for dependency injection

## UI/UX 
- I had used Adobe XD to design my screen prior to implementation. Because before implementation is always better to visualize the output.
- So that we can know what inputs are need to fed to get the outputs

## Building app
- I have used the GitHub repository for the CI process. 
- For the deployment of the app we can go for Jenkins and other software also for the CD process(Fastlane..etc)

- I have added room DB to store and delete the favorite products for a particular user.
- Share option is implemented on the details page. So that users can share the products with others.
- Since review added is not stored in real-time. I have updated the recycler view with a review each time the review is posted using Fragment results and also based on user rating the rating also is changed in the details page for the user to visualize in real time for time being. 


## Libraries used in the application
- Androidx - Support Libraries
- Glide
- Room Persistence Library
- Live Data
- Dagger2
- RXJava
- RX Kotlin
- Material Design
- Acra
- OkHttp
- Retrofit2
- Junit
- Mockito
- Roboelectric
- Espresso
