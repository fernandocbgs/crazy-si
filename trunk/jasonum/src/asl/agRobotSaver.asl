// Agent agRobotSaver in project jasonum

/* Initial beliefs and rules */
//at(P) :- pos(P,X,Y) & pos(r3,X,Y).
//perto refem será uma percepção dada via código 
//pertoRefem.

/* Initial goals */

!aproximarRefem. //tentar se aproximar do refem

//!check(slots).

/* Plans */

+!aproximarRefem : not pertoRefem & not pertoInimigo
	<- aproximar;
	   !!aproximarRefem;
	   .print("Tentando se aproximar do refem").
+!aproximarRefem : pertoRefem <- .print("SIM ! estou proximo").
+!aproximarRefem : pertoInimigo 
	<-  ludibriar;
	    !!aproximarRefem;
		.print("perto inimigo").


	
//+!check(slots) : not visited(r1)
//   <- next(slot);
//      !!check(slots).

//+!at(L) : at(L).
//+!at(L) <- ?pos(L,X,Y);
//           move_towards(X,Y);
//           !at(L).

