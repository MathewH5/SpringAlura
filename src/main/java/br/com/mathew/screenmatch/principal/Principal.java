package br.com.mathew.screenmatch.principal;

import br.com.mathew.screenmatch.model.DadosEpisodio;
import br.com.mathew.screenmatch.model.DadosSerie;
import br.com.mathew.screenmatch.model.DadosTemporada;
import br.com.mathew.screenmatch.model.Episodio;
import br.com.mathew.screenmatch.service.ConsumoApi;
import br.com.mathew.screenmatch.service.CoverterDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leia = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private CoverterDados conversor = new CoverterDados();
    private final String ENDERECO = "http://www.omdbapi.com/?t=" ;
    private final String API_KEY = "&apikey=c142b0d2";

    public void exibeMenu(){
        System.out.println("Digite o nome da serie para a busca");
        var nomeSerie = leia.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();
        for(int i=1; i<=dados.totalTemporadas(); i++){
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+")  +"&season=" + i + API_KEY);

            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

//        for (int i=0; i<dados.totalTemporadas(); i++){
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j=0; j<episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }
//
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

//        List<String> nomes = Arrays.asList("mathew", "lucas", "davi", "joao", "ana");
//
//        nomes.stream()
//                .sorted()
//                .limit(3)
//                .filter(f -> f.startsWith("d"))
//                .map(n -> n.toUpperCase())
//                .forEach(System.out::println);

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao))
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(),d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);
    }
}
