# KnockoutPlugin
A makeshift plug-in for knockouts.

If you have any suggestions for changing the code, adding some function, create an issue or write to me in private message on discord: KamlaX#0001

# Requirements
To run the plug-in, you must have **java 15 or higher**. 

Supported minecraft versions: **1.13, 1.14, 1.15, 1.16, 1.17**
# For developers
I created events for developers, list events:

> **PlayerEndRescueEvent, PlayerKnockoutEvent, PlayerStartRescueEvent and PlayerStopRescueEvent**

example of use:

```java
import pl.kamlax.knockout.events.PlayerEndRescueEvent;

@EventHandler
public void onPlayerEndRescue(PlayerEndRescueEvent event) { 
  //code here
}
```

# Frequent problems
If you get this error:

![image_2021-06-13_135824](https://user-images.githubusercontent.com/40739398/121806252-6a219300-cc4f-11eb-8ce1-adbdfdc35ee8.png)

Add **--enable-preview** to your batch file that starts the server. 
It should look something like this: **-jar --enable-preview engine.jar**
