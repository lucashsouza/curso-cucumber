package br.com.lucashsouza.steps;

import br.com.lucashsouza.entidades.Filme;
import br.com.lucashsouza.entidades.NotaAluguel;
import br.com.lucashsouza.entidades.TipoAluguel;
import br.com.lucashsouza.servicos.AluguelService;
import br.com.lucashsouza.utils.DateUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class AlugarFilmesSteps {

    private Filme filme;
    private final AluguelService service = new AluguelService();
    private NotaAluguel nota;
    private String erro;
    private TipoAluguel tipoAluguel;

    @Dado("um filme")
    public void umFilme(DataTable dataTable) {
        Map<String, String> map = dataTable.asMap(String.class, String.class);
        filme = new Filme();
        filme.setEstoque(Integer.parseInt(map.get("estoque")));
        filme.setAluguel(Integer.parseInt(map.get("preco")));
        String tipo = map.get("tipo");
        tipoAluguel = tipo.equals("semanal") ? TipoAluguel.SEMANAL : tipo.equals("extendido") ? TipoAluguel.EXTENDIDO : TipoAluguel.COMUM;
    }

    @Dado("um filme com estoque de {int} unidade\\(s)")
    public void umFilmeComEstoqueDeUnidades(Integer int1) {
        filme = new Filme();
        filme.setEstoque(int1);
    }

    @Dado("que o preço do aluguel seja R$ {int}")
    public void queOPreçoDoAluguelSejaR$(Integer int1) {
        filme.setAluguel(int1);
    }

    @Quando("alugar")
    public void alugar() {
        try {
            nota = service.alugar(filme, tipoAluguel);
        } catch (RuntimeException e) {
            erro = e.getMessage();
        }
    }

    @Então("o preço do aluguel será R$ {int}")
    public void oPreçoDoAluguelSeráR$(int arg1) {
        Assert.assertEquals(arg1, nota.getPreco());
    }

    @Então("o estoque do filme será {int} unidade")
    public void oEstoqueDoFilmeSeráUnidade(int int1) {
        Assert.assertEquals(int1, filme.getEstoque());
    }

    @Então("não será possível por falta de estoque")
    public void nãoSeráPossívelPorFaltaDeEstoque() {
        Assert.assertEquals("Filme sem estoque", erro);
    }

    @Dado("que o tipo do aluguel seja {string}")
    public void queOTipoDoAluguelSeja(String tipo) {
        tipoAluguel = tipo.equals("semanal") ? TipoAluguel.SEMANAL : tipo.equals("extendido") ? TipoAluguel.EXTENDIDO : TipoAluguel.COMUM;
    }

    @Então("a data de entrega será em {int} dia\\(s)")
    public void aDataDeEntregaSeráEmDiaS(int int1) {
        Date dataEsperada = DateUtils.obterDataDiferencaDias(int1);
        Date dataReal = nota.getDataEntrega();

        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Assert.assertEquals(sdf.format(dataEsperada), sdf.format(dataReal));
    }

    @Então("a pontuação recebida será de {int} ponto\\(s)")
    public void aPontuaçãoRecebidaSeráDePontos(int int1) {
        Assert.assertEquals(int1, nota.getPontuacao());
    }

}
