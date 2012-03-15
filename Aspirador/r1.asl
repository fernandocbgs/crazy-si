// mars robot 1

/* Initial beliefs */

at(P) :- pos(P,X,Y) & pos(r1,X,Y).

/* Initial goal */

!check(slots). 

/* Plans */

+!check(slots) : not garbage(r1)
   <- next(slot);
      !!check(slots).
+!check(slots). 


+garbage(r1) : true
   <- !take(grav,r1).

-garbage(r1) : true
   <- !check(slots).
   
+!take(S,L) : true
  <- !ensure_pick(S); 
    !at(L);
    drop(S);
    -garbage(r1).
   
+!ensure_pick(S) : garbage(r1)
   <- pick(garb);
      !ensure_pick(S).
+!ensure_pick(_).