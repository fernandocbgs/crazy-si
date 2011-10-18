// Agent ag_robot1 in project jasonum

/* Initial beliefs and rules */

//pos(r1,0,0). //posição inical 0,0 --> definido na classe java
at(P) :- pos(P,X,Y) & pos(r1,X,Y).

/* Initial goals */

!check(slots).
//!start.

/* Plans */

//+!start : true <- .print("hello world.").

+!check(slots) : not visited(r1)
   <- next(slot);
      !!check(slots).
+!check(slots).
 
+!at(L) : at(L).
+!at(L) <- ?pos(L,X,Y);
           move_towards(X,Y);
           !at(L).
