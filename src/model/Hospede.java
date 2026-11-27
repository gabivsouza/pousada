package model;

public class Hospede {

	private final String nome;
	private final String cpf;
	private String email;
	private String telefone;
	private String endereco;
	private String preferencias;

	private Hospede(Builder builder) {
		this.nome = builder.nome;
		this.cpf = builder.cpf;
		this.email = builder.email;
		this.telefone = builder.telefone;
		this.endereco = builder.endereco;
		this.preferencias = builder.preferencias;
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

	public String getEmail() {
		return email;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getPreferencias() {
		return preferencias;
	}

	@Override
	public String toString() {
		return "Hóspede: " + nome + " | CPF: " + cpf + (email != null ? " | E-mail: " + email : "")
				+ (telefone != null ? " | Tel: " + telefone : "") + (endereco != null ? " | Endereço: " + endereco : "")
				+ (preferencias != null ? " | Preferências: " + preferencias : "");
	}

	public static class Builder {

		private final String nome;
		private final String cpf;
		private String email;
		private String telefone;
		private String endereco;
		private String preferencias;

		public Hospede build() {
			if (nome == null || nome.isBlank()) {
				throw new IllegalArgumentException("Nome é obrigatório.");
			}
			if (cpf == null || cpf.replaceAll("\\D", "").length() != 11) {
				throw new IllegalArgumentException("CPF deve ter 11 dígitos.");
			}
			if (email != null && !email.contains("@")) {
				throw new IllegalArgumentException("E-mail inválido.");
			}
			return new Hospede(this);
		}

		public Builder(String nome, String cpf) {
			this.nome = nome;
			this.cpf = cpf;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder telefone(String telefone) {
			this.telefone = telefone;
			return this;
		}

		public Builder endereco(String endereco) {
			this.endereco = endereco;
			return this;
		}

		public Builder preferencias(String preferencias) {
			this.preferencias = preferencias;
			return this;
		}

	}

}
