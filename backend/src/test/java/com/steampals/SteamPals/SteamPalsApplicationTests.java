package com.steampals.steampals;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@SpringBootTest
class SteampalsApplicationTests {

	@Test
	void contextLoads() {
		//Este test verifica que el contexto de la aplicacion se carga correctamente
		//y que no hay errores en la configuracion de los beans
		//de la aplicacion. Si el contexto no se carga correctamente, el test fallara.

		//El contexto de la aplicacion se carga a partir de la clase principal y le da el nombre de la aplicacion
		//a la clase de configuracion. En este caso, la clase principal es SteampalsApplication.

		
	 try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SteampalsApplication.class)) {
		//Se obtiene el bean de la aplicacion y se verifica que no sea nulo

		 assertNotNull(context, "El contexto de la aplicacion no se ha cargado correctamente");

		 //Un bean es un objeto que es instanciado, ensamblado y administrado por el contenedor de Spring
	 } catch (Exception e) {
		 e.printStackTrace();
		 throw new RuntimeException("Failed to load application context: " + e.getMessage());
	 }
	}

}
