package br.com.mathew.screenmatch;

import br.com.mathew.screenmatch.model.DadosEpisodio;
import br.com.mathew.screenmatch.model.DadosSerie;
import br.com.mathew.screenmatch.model.DadosTemporada;
import br.com.mathew.screenmatch.service.ConsumoApi;
import br.com.mathew.screenmatch.service.CoverterDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

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

		List<DadosTemporada> temporadas = new ArrayList<>();

		for(int i=1; i<=dados.totalTemporadas(); i++){
			json = consumoApi.obterDados("http://www.omdbapi.com/?t=gilmore+girls&Season=" + i + "&apikey=c142b0d2");

			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		temporadas.forEach(System.out::println);
	}
}
