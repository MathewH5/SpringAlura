package br.com.mathew.screenmatch;

import br.com.mathew.screenmatch.service.ConsumoApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("http://www.omdbapi.com/?t=gilmore+girls&Season=1&apikey=c142b0d2");
		System.out.println(json);
//		json = consumoApi.obterDados("http://www.omdbapi.com/?t=dark&Season=1&apikey=c142b0d2");
//		System.out.println(json);


	}
}
