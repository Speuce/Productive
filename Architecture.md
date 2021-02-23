 


                                   +---------------+                          +---------+
                                   |UserPersistence|XX    +--------------+    |User     XX    +-----------------+
                                  X|Manager        |  XXX |              |XXXX|Access   |XXXXX|                 |
                                 XX+---------------+    XXX  Runnable    |    +---------+     +-----------------+
                              XXXX                        |  Executor    |                    |    Db           |
              +-------------+XX    +---------------+      X              |                    +-----------------+
              |  Persistent |      |TaskPersistence|   XXX+--------------+XX  +----------+    | (Through Room)  |
              |  Android    XXXXXXX|Manager        |XXX                    XXXXTask      XXXXXX------------------+
              |  DataManager+      +---------------+                          |Access    |    |                 |
              +-------X+---XX                                                 +----------+    +-----------------+
 Data Layer           X      XXX
+---------------------X--------XXX
                      X           XX
                      X            XXX                 +----------------+
                      X              XX                |                |
            +-------------+    +-------------+         |  Title         |
            |             |    |             XXXXXXXXXXX  Manager       |
            |  TaskManager|    | UserManager |         |                |
            |             |    |             XXXX      +----------------+
            |             |    +--------X----+   XXX +-------------------+
            +-------X-----+             X          XXX  Reward           |
                    XXX                 X            |  Manager          |
                       XX               X            |                   |
                        XXX     +-------X-----+     X|                   |
                          XXXX  |             |  XXX +-------------------+
                             XXX|  Event      |XX
                                |  Dispatch   |
                                |             |
                                +-------------+
