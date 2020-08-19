
## Description
This is a sample app that is supposed to display list of data with title, description and image.

## Design Pattern
- Follow MVVM design pattern
- Used ViewModel and Live Date
- Databinding and DI is not used

## Tech Stack
- Retrofit 2 - OkHttp3 - request/response API
- Glide - for image loading.
- RxJava 2 - reactive programming paradigm
- LiveData - use LiveData to see UI update with data changes.
- ViewModel - bind UI components in layouts to data sources
- AndroidX
- Espresso - for ui testing
- Junit and Mockito - for unit testing and mocking the object
- AndroidX Fragment Navigation

## Overview of app arch.
- follow the rules from Architecture guidelines recommended by Google.
- keep Activity only responsible for UI related code
- ViewModel provides data required by the UI class
- Repository layer provides data to ViewModel classes. (single source of truth)

## Unit testing
- Written unit test cases for api success and error case

## Ui testing
- Written Ui test cases for recycler view

## What's New
- Add offline cache implementation for no internet scenario