package pagamento;

public class PagamentoPixAdapter implements ProcessadorPagamento {
	
	private ApiPix pix = new ApiPix();

	@Override
	public boolean pagar(double valor) {
		pix.enviarPix(Math.round(valor * 100), "pousada@pix.com");
		return true;
	}

}
