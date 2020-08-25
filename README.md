# ezDev-2.0
My own version of Skript.
Doubt this will ever reach a release, mainly for fun right now.

Some plans:
 - Define console/player only commands. Example usage:
     ```
     command website (player):
         // Do player only things
     end
   
     command sudo (console):
        // Do console only things
     end
     ```
 - Define command arguments. Example usage:
    ```
    command kill <victim>:
        kill victim
        tell victim "You got killed!"
    end
    ```
 - Developer API
    - Add custom actions + events