package util;

import java.util.Scanner;

public class Cliente {
    private int idcliente;
    private String cpf;
    private String nome;
    private String email;
    private String senha;

    public Cliente(int idcliente, String cpf, String nome, String email, String senha) {
        this.idcliente = idcliente;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Cliente(String cpf, String nome, String email, String senha) {
        this(0, cpf, nome, email, senha);
    }

    public int getIdcliente() {
        return idcliente;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean login(String email, String senha) {
        if (this.email.equals(email) && this.senha.equals(senha)) {
            System.out.println("Login bem-sucedido!");
            return true;
        } else {
            System.out.println("Email ou senha incorretos.");
            return false;
        }
    }

    public boolean login(String email, boolean isEmailOnly) {
        return login(email, this.senha);
    }

    public void autenticacao() {
        try (Scanner scanner = new Scanner(System.in)) {
            String email;
            String senha;

            do {
                System.out.println("Digite seu email: ");
                email = scanner.nextLine();

                System.out.println("Digite sua senha: ");
                senha = scanner.nextLine();

            } while (!login(email, senha));
        }
    }

    @Override
    public String toString() {
        return "Cliente{" + "idcliente=" + idcliente + ", cpf='" + cpf + '\'' + ", nome='" + nome + '\'' + ", email='" + email + '\'' + ", senha='[PROTECTED]'" + '}';
    }
}