package pagamento;

public class GatewayCartao {

	public boolean autorizar(String titular, double valor, int parcelas) {
		System.out.println("Cartão de " + titular + ": " + valor + " em " + parcelas + "x");
		return valor <= 5000;
	}

}
