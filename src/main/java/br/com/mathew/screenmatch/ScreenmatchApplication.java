package br.com.mathew.screenmatch;

import br.com.mathew.screenmatch.model.DadosEpisodio;
import br.com.mathew.screenmatch.model.DadosSerie;
import br.com.mathew.screenmatch.service.ConsumoApi;
import br.com.mathew.screenmatch.service.CoverterDados;
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

		CoverterDados conversor = new CoverterDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);

		json = consumoApi.obterDados("http://www.omdbapi.com/?t=gilmore+girls&Season=1&episode=2&apikey=c142b0d2");

		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);

	}
}
