package util;

import java.util.Scanner;

public class Cliente {
    private int idcliente; //
    private String cpf; //
    private String nome; //
    private String email; //
    private String senha; //

    public Cliente(int idcliente, String cpf, String nome, String email, String senha) { //
        this.idcliente = idcliente; // CORRIGIDO: Atribui o idcliente
        this.cpf = cpf; //
        this.nome = nome; //
        this.email = email; //
        this.senha = senha; //
    }

    public String getEmail() { //
        return email; //
    }

    public String getSenha() { //
        return senha; //
    }

    public String getCpf() { //
        return cpf; //
    }
    public String getNome() { //
        return nome; //
    }
    public int getIdcliente() { //
        return idcliente; //
    }

    // CORRIGIDO: Renomeado para setIdcliente e implementado corretamente
    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public boolean login(String email, String senha) { //
        if (this.email.equals(email) && this.senha.equals(senha)) { //
            System.out.println("Login bem-sucedido!"); //
            return true; //
        } else {
            System.out.println("Email ou senha incorretos."); //
            return false; //
        }
    }

    public boolean login(String email, boolean isEmailOnly) { //
        return login(email, this.senha); //
    }

    public void autenticacao() { //
        try (Scanner scanner = new Scanner(System.in)) { //
            String email; //
            String senha; //

            do {
                System.out.println("Digite seu email: "); //
                email = scanner.nextLine(); //

                System.out.println("Digite sua senha: "); //
                senha = scanner.nextLine(); //

            } while (!login(email, senha)); //
        }
    }
}