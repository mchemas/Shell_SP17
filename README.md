This was a project for my operating systems class.

The purpose of the project was to create a program that simulates a terminal.

One thing to note is that you can input multiple commands in one line, separating the them by a semicolon.

It will continuously ask the user to input a command until the user inputs "exit".

Anything else will be saved into an array, each spot being a command.

From there a thread is created and ran for each command.

The run() method is called on the thread. run() simply calls the commands method.

Inside the commands method it checks to see if the command() is either clr, echo, help, or pause.

Each one of those is handled distinctly. If it is not, it passes the command to otherProcess method.

Finally the otherProcess method creates a new process and starts it.

At this point it goes back up to the next command in the array. If there was none, asks the user to input a new command.
