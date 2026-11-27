package pagamento;

public class PagamentoCartaoAdapter implements ProcessadorPagamento {

	private GatewayCartao cartao = new GatewayCartao();

	@Override
	public boolean pagar(double valor) {
		return cartao.autorizar("Titular", valor, 1);
	}

}
