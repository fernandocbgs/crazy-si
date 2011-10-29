// Agent agInimigo in project jasonum

/* Initial beliefs and rules */

/* Initial goals */

!bloquear.

/* Plans */

+!bloquear : agenteProximo
	<-  
		.print("OPA TENHO QUE TRABALHAR");
		!!bloquear.
//+!bloquear : not agenteProximo <- .print("EITA").
//+!bloquear <- !bloquear. //aguarda
//+!bloquear : not agenteProximo <- .print("Ok").
