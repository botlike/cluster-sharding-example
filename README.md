#Akka cluster Sharding Example
This is the code for [this](https://www.google.com) blog entry.

###Run: 
Main method accepts two arguments : port for remote connections and a "input" flag

**sbt "runMain com.sharding.Main 3000"**

this will run the program with akka remote listening in the 3000 port
if you add "input" to the arguments then you can enter events manually by standard input in order to 
test the program.

**sbt "runMain com.sharding.Main 3000 input"**

then we can type events in this format : deviceId:value:clientId

So : 1:1212:12 

