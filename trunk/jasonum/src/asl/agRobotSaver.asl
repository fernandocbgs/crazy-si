// Agent agRobotSaver in project jasonum

/* Initial beliefs and rules */

/* Initial goals */

!aproximarRefem. //tentar se aproximar do refem

/* Plans */

+!aproximarRefem : not pertoRefem & not pertoInimigo
	<- aproximar;
	   !!aproximarRefem;
	   .print("Tentando se aproximar do refem").
+!aproximarRefem : pertoRefem 
	<- 	!!voltarMinhaArea;
		.print("SIM ! estou proximo").
+!aproximarRefem : pertoInimigo <- ludibriar; !!aproximarRefem; .print("perto inimigo").
+!voltarMinhaArea : not voltei <- voltarMA; !!voltarMinhaArea.
+!voltarMinhaArea : voltei <- .print("Voltei a minha area").