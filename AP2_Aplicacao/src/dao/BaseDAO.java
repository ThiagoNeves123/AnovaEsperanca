package dao;

import java.util.ArrayList;

public interface BaseDAO {
    void salvar(Object var1);

    Object buscarPorId(int var1);

    ArrayList<Object> listarTodosLazyLoading();

    ArrayList<Object> listarTodosEagerLoading();

    void atualizar(Object var1);

    void excluir(int var1);
}