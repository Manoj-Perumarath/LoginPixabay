# LoginPixabay
A sample app for getting images from pixabay
This project is an Android application developed using Kotlin and follows the MVVM architecture pattern. It allows users to register, login, and view images retrieved from the Pixabay API. The app uses modern Android development practices including Coroutines, LiveData, Retrofit, Dependency Injection, and XML Data Binding for the UI.

Username: test@example.com password : password

#Features

Login

Email and Password fields with inline validation.
The email must be valid.
The password must be between 6 and 12 characters.
User can click the login button to proceed.
Successful login will navigate the user to the home page.
Error messages are shown inline for each field.
Mocked login service for local testing.
Register
Email, Password, and Age fields with validation.
The email must be valid.
The password must be between 6 and 12 characters.
The age must be between 18 and 99 years.
User can click the register button to proceed.
Successful registration will navigate the user to the home page.
Error messages are shown inline for each field.
Mocked registration service for local testing.

Home

A list of images is displayed, fetched from the Pixabay API (with pagination support).
Each row displays the image thumbnail and the name of the user who uploaded the image.
Tapping on a row navigates the user to the image details page.
Image Detail
The image details page is divided into two sections:
Section 1: Displays the image, image size, type, and tags.
Section 2: Displays user stats such as views, likes, comments, favorites, and downloads.
Technologies & Architecture
Kotlin - The main programming language used for development.
MVVM (Model-View-ViewModel) - Architecture pattern to separate concerns between the UI and business logic.
Clean Architecture (Optional) - Followed for maintaining a scalable codebase.
Repository Pattern - Used to separate data sources and provide a clean API for data operations.
Dependency Injection - Used to inject dependencies using Dagger or Hilt.
Coroutines - Used for asynchronous operations such as network requests.
LiveData & Flow - For managing UI-related data in a lifecycle-conscious way.
Retrofit - A type-safe HTTP client used for network requests.
XML & Data Binding - Used for building UI components and binding data to the UI elements.
