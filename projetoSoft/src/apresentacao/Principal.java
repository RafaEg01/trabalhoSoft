package apresentacao;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import dados.Gasto;
import dados.TipoGasto;
import dados.Usuario;
import sistema.Sistema;



public class Principal {

	public static Sistema sistema = Sistema.getInstance(); // Get the singleton instance
	private static Scanner scan = new Scanner(System.in);


	public static void main(String[] args) {
		showMenuInicial();



	}

	public static void menuInicial() {
		System.out.println("Escolha a operação que deseja realizar");
		System.out.println("0 - Sair");
		System.out.println("1 - Cadastrar Usuario");
		System.out.println("2 - Login Usuario");
	}

	public static void showMenuInicial() {
		int opcao = -1;
		while(opcao != 0){
			boolean entradaValida = false;
			menuInicial();
			while (!entradaValida) {
				System.out.print("Digite um número: ");
				try {
					 opcao = scan.nextInt();
					 scan.nextLine();
					entradaValida = true; //
				} catch (InputMismatchException e) {
					System.out.println("Entrada inválida! Por favor, digite um número inteiro.");
					scan.nextLine();
				}
			}

			switch(opcao) {
			case 0:
			    System.out.println("Encerrando o programa...");
                break;
			case 1:
				cadastrarUsuario();
				break;
			case 2:
				if(loginUsuario() == null) {
					System.out.println("\nLogin Invalido");
				}else {
					showMenuLogado();
				}
				break;
			default:
				System.out.println("\nEscolha invalida");
				break;
			}
		}
	}

	public static void menuLogado() {
		System.out.println("Escolha a operação que deseja realizar");
		System.out.println("0 - LogOut");
		System.out.println("1 - Cadastrar Gasto");
		System.out.println("2 - Visualizar Gastos");
		System.out.println("3 - Deletar Gasto");
		System.out.println("4 - Visualizar Gastos Anual");

	}


	public static void showMenuLogado() {
		int opcao = -1;
		while(opcao != 0){
			boolean entradaValida = false;
			menuLogado();
			while (!entradaValida) {
				System.out.print("Digite um número: ");
				try {
					 opcao = scan.nextInt();
					 scan.nextLine();
					entradaValida = true; //
				} catch (InputMismatchException e) {
					System.out.println("Entrada inválida! Por favor, digite um número inteiro.");
					scan.nextLine();
				}
			}
			switch(opcao) {
			case 1:
				cadastrarGasto(sistema.getLogadoAtual());
				break;
			case 2:
				visualizarGastos(sistema.getLogadoAtual());
				break;
			case 3:
				deletarGasto(sistema.getLogadoAtual());
				break;
			case 4:
				visualizarGastoYear(sistema.getLogadoAtual());
				break;
				
			}
		}

	}

	public static void cadastrarUsuario() {
		System.out.println("Qual Login do usuario");
		String login = scan.nextLine();
		System.out.println("Qual Senha do usuario");
		String senha = scan.nextLine();
		System.out.println("Qual Idade do usuario");
		int idade = Integer.parseInt(scan.nextLine());
		System.out.println("Qual CPF do usuario");
		String cpf = scan.nextLine();
;
		Usuario u = new Usuario(login,senha,idade,cpf);

		if(sistema.cadastrarUsuario(u)) {
			sistema.setUsuarios(u);
		}

	}

	public static Usuario loginUsuario() {
		System.out.println("\nQual Login do usuario");
		String login = scan.nextLine();
		System.out.println("\nQual Senha do usuario");
		String senha = scan.nextLine();
		return sistema.loginUsuario(login,senha);

	}

	public static boolean cadastrarGasto(Usuario u) {
		LocalDate data = null;
		System.out.println("Qual Nome do gasto");
		String nome = scan.nextLine();

		System.out.println("Qual Valor do gasto");
		float valor = Float.parseFloat(scan.nextLine());

		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (data == null) {
            System.out.print("Digite a data (dd/MM/yyyy): ");
            String entrada = scan.nextLine();

            try {
                data = LocalDate.parse(entrada, formato);
                System.out.println("Data válida: " + data);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Tente novamente.");
            }
        }

		int tipogasto = 0;
		System.out.println("Qual Tipo do Gasto");
		System.out.println("(1) Comida (2) Lazer (3) Educação (4) Saúde (5) Transporte (6) Outros");
		tipogasto = Integer.parseInt(scan.nextLine());
		Gasto g = new Gasto(nome, data, valor, TipoGasto.fromInt(tipogasto), u.getId());

		if(sistema.cadastrarGasto(u,g)) {
			System.out.println("Gasto Cadastrado com Sucesso");
			return true;
		}
		return false;


	}

	 public static void visualizarGastos(Usuario u) {
		 System.out.println(sistema.visualizarGasto(u));

	 }

	 public static void deletarGasto(Usuario u) {
		 visualizarGastos(sistema.getLogadoAtual());
		 System.out.println("\nDigite o ID do gasto que deseja deletar");
		 int id = Integer.parseInt(scan.nextLine());
		 sistema.removeGasto(u, sistema.buscarGastoPorId(u.getContas(), id));

	 }
	 
	 public static void visualizarGastoYear(Usuario u) {
		 System.out.println("Qual o ano que você deseja filtrar");
		 int i = Integer.parseInt(scan.nextLine());
		 if(sistema.visualizarGastoAnual(u,i).size() != 0) {
			 System.out.println(sistema.visualizarGastoAnual(u,i));
		 }else {
			 System.out.println("Nenhum gasto com essa data foi encontrado");
		 }
	 }


}