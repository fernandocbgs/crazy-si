// Agent agInimigo in project jasonum

/* Initial beliefs and rules */

/* Initial goals */
//!bloquear.

/* Plans */

+bloquear 
	<-  !!bloquearSalvador;
		.print("OPA TENHO QUE TRABALHAR").
+!bloquearSalvador : not pararBloquear
	<- bloquearAction; !!bloquearSalvador.
+!bloquearAction : pararBloquear <- .print("#Parar de bloquear robo salvador").