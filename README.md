# fx-flights
Human-Computer Interaction Project, Polytech Paris-Sud ET3

The goal of this project was to design a graphical app showing live data of airplanes currently in the sky.
It gets data from a web API ([ADS-B Exchange](https://public-api.adsbexchange.com/)), and a list of airports from a local file.

Based on user input (departure and arrival locations), it shows current flights on the screen in a user-friendly way.

Language used is Java, UI is built using JavaFX library.


### Dependencies
The project requires `asynchttpclient`, `Jackson` and `jimObjModelImporterJFX`, as mentioned by [.classpath](.classpath) file.
