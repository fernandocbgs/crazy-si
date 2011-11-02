// Agent agRefem in project jasonum

/* Initial beliefs and rules */

/* Initial goals */

/* Plans */

+refemSeguir 
	<-  !!seguirSalvador;
		.print("Devo seguir o robo salvador").
+!seguirSalvador : not campoSeguro
	<- seguirRbSalvador; !!seguirSalvador.
+!seguirSalvador : campoSeguro <- .print("Campo Seguro").