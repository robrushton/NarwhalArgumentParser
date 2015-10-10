# Team Narwhal: Argument Parser

##Description
This argument parser takes premade arguments and flags through the terminal and store the data. There are three types of arguments it can hold.

![alt text](http://i.imgur.com/APXgX6a.jpg "Normal Argument")

Normal arguments are positional and must be entered in order of creation. The value for a normal argument is entered as the data in the terminal. For example, if you have 3 normal arguments ,length, width, and height, you would enter there data (datatype dependant) into the terminal.

#####Ex: java ArgumentParser 3 4 5

![alt text](http://i.imgur.com/omQlrav.jpg "Named Argument")

Named arguments are optional arguments that are entered by using two dashes, the name of the argument, and then the value.

#####Ex: java ArgumentParser 3 4 5 --Cheese swiss

![alt text](http://i.imgur.com/EcTZ9ag.jpg "Flag Argument")

Flag Arguments are also optional arguments, but they can only have a boolean value. Flags are entered as a dash followed by a letter. If the flag is entered, it is true. If the flag is ommitted, it is false.

#####Ex: java ArgumentParser 3 4 5 --Cheese swiss -f

![alt text](http://i.imgur.com/6GjE5f8.jpg "Example Setup")

This is a simple example showing all three types of arguments.

##Instructions
**This program requies:**
- **Java JRE/JDK**
- **Gradle Build Tool**
- **JUnit**
- **RobotFramework**

![alt text](http://i.imgur.com/6VOEyBL.jpg "Build With Gradle In Terminal")

First you have to use gradle to build the project.

![alt text](http://i.imgur.com/kuEMUVG.jpg "Example Code For Setup")

This is an example of code you could use to test the argument parser. This example uses a string array to pass the arguments into the parser (same as using args in the main with the terminal). If you want to run it through the terminal, take out the string array and replace it with the args string array from the main. Then you can pass the arguments by terminal.

#####Ex: java ArgumentParser Narwhal --drink tea -p
