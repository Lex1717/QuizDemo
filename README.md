# QuizDemo
Small demo app for solving quizzes

App was created as **single-activity multi-module project**. Created in **Kotlin** with **Clean architecture** approach, using **MVP** for the presentational layer.
In the project I used **RxJava 2, Dagger 2 and Realm**.

Wrote two demo test:
```
unit - puzzler/src/test/java/com/alexeeff/golangpuzzler/puzzler/data/CourseRepositoryImplTest.kt
```
```
integrational - app/src/androidTest/java/com/alexeeff/golangpuzzler/
```
I wasn't aiming for decent code coverage in the project, I was trying to implement different aspects of testing: mocking, Rx testing, PageObject approach, custom espresso matchers