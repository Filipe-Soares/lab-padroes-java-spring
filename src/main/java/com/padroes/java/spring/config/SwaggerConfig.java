package com.padroes.java.spring.config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI configuration(){
		MavenXpp3Reader reader = new MavenXpp3Reader();
		Model model = null; 				
				
		if ((new File("pom.xml")).exists()) {
  	      try {
			model = reader.read(new FileReader("pom.xml"));
			} catch (IOException | XmlPullParserException e) {
				e.printStackTrace();
			}
  	      }
		return new OpenAPI().info(new Info().title("Patterns Java") 
				.description("Back-end para salvar usuários e endereços no BD, integrado com do ViaCep")
				.version(model != null ? model.getVersion() : getClass().getPackage().getImplementationVersion())
				.license(new License().name("Apach 2.0").url("http://springdoc.org")));
	}
	
}