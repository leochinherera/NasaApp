<h1 align="center">Nasa App</h1>

<p align="center">
Nasa App is a sample Android project using <a href="https://images-api.nasa.gov/">The Nasa App</a> API based on MVVM architecture. 
> 
Using the NASA Search API, i  build a simple client app that allows users to scroll through the list of Milky Way images taken in 2017 and above,when i tried to put year end 2017 there is an API error on the request which i tested with postman.
## Features That i used 
* Used 100% Kotlin on the development
* MVVM architecture
* Android architecture components and Jetpack libraries

## I chose the following Tech Stacks
* [Retrofit](http://square.github.io/retrofit/) + [OkHttp](http://square.github.io/okhttp/) - RESTful API and networking client.
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - A collections of libraries that help you design rebust, testable and maintainable apps.   
* [Paging](https://developer.android.com/topic/libraries/architecture/paging) - Pagination loading for RecyclerView.It makes you get content of the page faster and uses less memory.
** [Recycleview](https://developer.android.com/topic/libraries/architecture/recycleview) - RecyclerView prepares the view behind and ahead beyond the visible entries. It gives significant performance when you need to fetch the bitmap image in your list from a background task
    * [ViewModel](https://developer.android.com/reference/androidx/lifecycle/ViewModel) - UI related data holder, lifecycle aware.
    * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Observable data holder that notify views when underlying data changes.
* ~[RxJava](https://github.com/ReactiveX/RxJava) - Asynchronous programming with observable streams.~ 
* (Implementing) [Live Data](https://developer.android.com/kotlin/androidx.lifecycle) Concurrency design pattern for asynchronous programming.
*[Gson]a library to load a json to android application and i chose this on the basis of its simple understanding with easy implementation and extensive support of Java Generics
* (Implementing) [Jetpack Compose](https://developer.android.com/jetpack/compose) - Declarative and simplified way for UI development. 
* [Glide]- Declarative and simplified way displaying data and showed to the application fast and error less mechanisms. 
## Architectures


Nasa App

I followed  Google recommended [Guide to app architecture](https://developer.android.com/jetpack/guide) to structure our architecture based on MVVM, reactive UI using LiveData / RxJava observables and data binding.

Features that can be be added on this project are:
For better improvement to load images more quicker and faster without a network is to implement Room/sqflite  database which got offline capabilities to load the app data.




