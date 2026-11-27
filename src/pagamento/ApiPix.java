package pagamento;

public class ApiPix {

	public void enviarPix(long centavos, String chave) {
		System.out.println("Pix enviado: " + centavos + " centavos para " + chave);
	}

}
