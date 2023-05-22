package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exception.InvalidChaveException;

public class Chave {

	Enum_chave tipo;
	private String chave;

//	Metodos de validacao
	public static boolean validarCPF(String cpf) {
		cpf = cpf.replaceAll("[^\\d]", "");
		if (cpf.length() != 11) {
			return false;
		}
		if (cpf.matches("(\\d)\\1{10}")) {
			return false;
		}

		int soma = 0;
		for (int i = 0; i < 9; i++) {
			soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
		}
		int digito1 = 11 - (soma % 11);
		if (digito1 > 9) {
			digito1 = 0;
		}
		soma = 0;

		for (int i = 0; i < 10; i++) {
			soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
		}
		int digito2 = 11 - (soma % 11);
		if (digito2 > 9) {
			digito2 = 0;
		}

		if (digito1 == Character.getNumericValue(cpf.charAt(9))
				&& digito2 == Character.getNumericValue(cpf.charAt(10))) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean validarTelefone(String telefone) {
	    telefone = telefone.replaceAll("[^\\d]", "");

	    if (telefone.length() < 10 || telefone.length() > 11) {
	        return false;
	    }

	    int ddd = Integer.parseInt(telefone.substring(0, 2));
	    if (ddd < 11 || ddd > 99) {
	        return false;
	    }

	    return true;
		
    }
	public static boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
	
//  Construtor
	public Chave() {};
	
//	Metodos Getters and Setters
	public void setChave(String chave) throws InvalidChaveException  {
		
		switch (tipo) {
		case CPF: {
			if (validarCPF(chave)) {
				this.chave = chave;
			}else {
				throw new InvalidChaveException();
			}
			break;
		}
		case TELEFONE: {
			if (validarTelefone(chave)) {
				this.chave = chave;
			}else {
				throw new InvalidChaveException();
			}
			break;
		}
		case EMAIL: {
			if (validarEmail(chave)) {
				this.chave = chave;
			}else {
				throw new InvalidChaveException();
			}
			break;
		}
		default:
			this.chave = "ERRO";
		}
		
		

	}
	public String getChave() {
		return chave;
	}
	public Enum_chave getTipo() {
		return tipo;
	}
	public String getStringTipo() {
		return tipo.name();
	}
	public void setTipo(Enum_chave tipo) {
		this.tipo = tipo;
	}



}
