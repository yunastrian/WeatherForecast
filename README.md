# Weather Forecast

by : Kurniandha Sukma Yunastrian (13516106) <br>

**Weather Forecast** is an application to predict the conditions of weather for a given location and time. This application is created in Java using Open Weather Map API.

## Open Weather Map API
**Application Programming Interface (API)** is a set of subroutine definitions, protocols, and tools for building application software. In general terms, API allows programmers to use standard functions to interact with other operating systems. <br>
**Open Weather Map API** is one of the APIs that can be used for predict the conditions of weather. We can use its feature to make simple weather forecast application easily. It was created by [Open Weather Map](https://openweathermap.org/). This API provides features to look current weather, predict 5 days forecast, etc. More information can be viewed [here](https://openweathermap.org/api).

## Package Structure 
```
src
├── Process
|   └── Weather.java
└── Main
    └── Main.java
```
Packages made based on its function to make it easier for programmers to revise. **Interface Package** is used to display search field and result data. **Process Package** is used to request weather data from [Open Weather Map](https://openweathermap.org/) based on user input.

## Feature Checklist
- [X] Class Weather
- [X] Class Main

## How to Install
Dependencies : **Netbeans 8.2** <br>
Clone this repository then open **Weather Forecast** folder as project using **Netbeans**. After that, compile and run the project.

## How to Use
1. Enter the city name in the **City field**.
2. Click **Search** button.
