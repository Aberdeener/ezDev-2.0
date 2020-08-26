# ezDev-2.0
My own version of Skript.
Doubt this will ever reach a release, mainly for fun right now.

Some plans:
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
 - Developer API
    - Add custom actions + events | **In Progress**