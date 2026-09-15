
## Application Overview

S8133896Assignment2 is an Android fitness dashboard application developed in Kotlin. The application demonstrates Android API integration, user interface design, navigation, dependency injection, and clean code practices.

The application connects to the NIT3213 API to authenticate a user, retrieve a dashboard of fitness exercise entities, and display detailed information for a selected exercise.

## Features

### Login Screen

- Provides input fields for a student ID and first name.
- Uses the student ID without the (s) prefix as the username.
- Uses the student's first name as the password.
- Sends a POST request to the Footscray authentication endpoint.
- Displays validation and network error messages when login is unsuccessful.
- Navigates to the Dashboard screen after successful authentication.

### Dashboard Screen

- Uses the keypass returned from successful authentication.
- Sends a GET request to the dashboard API endpoint.
- Displays the number of available exercise entities.
- Uses a RecyclerView to show a list of fitness exercises.
- Displays a summary for each entity, including exercise name, muscle group, equipment, difficulty, and calories burned per hour.
- Does not display the detailed description in the RecyclerView item summary.
- Handles loading and error states.

### Details Screen

- Opens when the user taps an exercise from the Dashboard RecyclerView.
- Displays all available information for the selected exercise.
- Displays the full detailed exercise description.
- Includes exercise name, muscle group, equipment, difficulty, calories burned per hour, and description.



## Technologies Used

- Kotlin
- Android Studio
- Android Views with XML layouts
- Retrofit for REST API communication
- Gson Converter for JSON parsing
- OkHttp logging interceptor for HTTP request logging
- Kotlin Coroutines for asynchronous operations
- Kotlin Flow and StateFlow for UI state management
- RecyclerView for dashboard list display
- Koin for dependency injection
- ViewModel for UI state and lifecycle-aware architecture
- Git and GitHub for version control

## Architecture

The application follows a simple MVVM-style architecture.

```text
Activities
    ↓
ViewModels
    ↓
Repositories
    ↓
Retrofit API Service
    ↓
NIT3213 API
```

### Activities

Activities are responsible for:

- Displaying the user interface.
- Observing ViewModel UI state.
- Updating views for loading, success, and error states.
- Navigating between Login, Dashboard, and Details screens.

### ViewModels

ViewModels are responsible for:

- Holding screen UI state.
- Performing login and dashboard loading operations.
- Running asynchronous work through Kotlin Coroutines.
- Separating UI logic from Activity code.

### Repositories

Repositories are responsible for:

- Calling the Retrofit API service.
- Handling authentication requests.
- Retrieving dashboard entities.

### Dependency Injection

Koin is used to provide application dependencies, including:

- Retrofit
- OkHttpClient
- NIT3213 API service
- Authentication repository
- Dashboard repository
- LoginViewModel
- DashboardViewModel

## Project Structure


app/src/main/java/com/example/s8133896assignment2/
├── data/
│   ├── model/
│   ├── remote/
│   └── repository/
├── di/
│   └── AppModule.kt
├── ui/
│   ├── dashboard/
│   │   ├── DashboardViewModel.kt
│   │   └── FitnessAdapter.kt
│   └── login/
│       └── LoginViewModel.kt
├── MainActivity.kt
├── DashboardActivity.kt
├── DetailsActivity.kt
└── Nit3213Application.kt


## How to Build and Run

### Requirements

- Android Studio
- Android SDK
- Internet connection, required for the NIT3213 API
- Android emulator or physical Android device

### Steps

1. Clone or download this repository.

2. Open the project in Android Studio.

3. Wait for Gradle sync to complete.

4. Connect an Android device or start an Android emulator.

5. Select the app run configuration.

6. Click the Run button in Android Studio.

7. On the Login screen, enter:
    - Student ID: enter the numeric ID without the s prefix.
    - First name: enter the first name with the correct capitalisation.

8. Tap the login button.

9. After successful authentication, the Dashboard displays the available fitness exercises.

10. Tap an exercise card to open the Details screen and view its full description.

## Error Handling

The application handles common error cases, including:

- Empty student ID input.
- Empty first name input.
- Missing dashboard keypass.
- API authentication failure.
- Dashboard network or server errors.
- Unexpected API response errors.

## Testing

The application has been manually tested using an Android emulator.

Manual test cases include:

 Test Case -> Expected Result 
 Launch application -> Login screen is displayed 
 Enter blank student ID  ->Student ID validation error is displayed 
 Enter blank first name -> First name validation error is displayed 
 Enter valid login details -> Dashboard screen opens 
 Dashboard API request succeeds -> Exercise entities appear in RecyclerView 
 Tap an exercise entity -> Details screen opens 
 Open Details screen -> All entity fields and full description are displayed 
 Press Back on Details screen -> User returns to Dashboard 



Local unit tests are included for LoginViewModel.

The tests verify that:

- A blank student ID produces the message: Please enter your student ID.
- A blank first name produces the message: Please enter your first name.

The tests use a fake implementation of AuthRepositoryInterface. This isolates the ViewModel from Retrofit and the live NIT3213 API, so no network request is made while unit tests run.

To run the tests in Android Studio:

1. Open `LoginViewModelTest.kt` under app/src/test/java.
2. Click the green run icon next to LoginViewModelTest.
3. Select Run LoginViewModelTest.

## Git Version Control

Git was used throughout development to maintain version history. Meaningful commits were created for major features, including API integration, dashboard display, details navigation, dependency injection, and ViewModel refactoring.


This repository contains the complete Android Studio project for the NIT3213 Final Assignment.

To run the project, follow the build and run instructions above.


