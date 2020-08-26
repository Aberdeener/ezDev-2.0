# ezDev-2.0
My own version of Skript.
Doubt this will ever reach a release, mainly for fun right now.

### Some plans:
 - Let `Actions` have multiple `length`s.
 - Define console/player only commands. Example usage:
     ```
     command website (player):
         // Do player only things
     end
   
     command sudo (console):
        // Do console only things
     end
   
     command sudo:
        // Do whatever things
     end
     ```
 - Define command arguments. Example usage:
    ```
    command kill <victim>:
        kill victim
        tell victim "You got killed!"
    end
    ```
 - Allow events other than child `PlayerEvents`
 
### Developer API
Greetings, developer!
Assuming you are familiar with getting a simple maven project started, do that.
In your pom.xml, add these:
```
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```
```
<dependency>
    <groupId>com.github.Aberdeener</groupId>
    <artifactId>ezDev-2.0</artifactId>
    <version>0.1.0</version>
</dependency>
```
For ezDev's AddonLoader to use your Addon correctly, it requires a `main.txt` file in your /src/main/resources folder which contains only the name of your Main class.

Check out the [DemoAddon](https://github.com/Aberdeener/ezDev) for the basics of using the classes.