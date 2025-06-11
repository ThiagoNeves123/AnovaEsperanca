package dao;

import java.util.ArrayList;

public interface BaseDAO {
    void salvar(Object var1); // Remova @Override aqui

    Object buscarPorId(int var1); // Remova @Override aqui

    ArrayList<Object> listarTodosLazyLoading(); // Remova @Override aqui

    ArrayList<Object> listarTodosEagerLoading(); // Remova @Override aqui

    void atualizar(Object var1); // Remova @Override aqui

    void excluir(int var1); // Remova @Override aqui
}