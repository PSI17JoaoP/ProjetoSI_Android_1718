package pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by JAPorelo on 06-12-2017.
 * Project ProjetoSI_Android_1718 - pt.ipleiria.estg.tesp.psi.projsi.sistematrocas.projetosi_android_1718.helpers
 */

/**
 * Cria a base de dados SQLite, caso não estiver criada.
 * Implementa os métodos abstratos para a execução de queries de CRUD (SELECT, INSERT, UPDATE, DELETE).
 * Estes métodos terão diferentes implementações nas respetivas sub-classes. Recebem o tipo genérico de objeto definido por T.
 *
 * @param <T> Tipo de objeto a ser manipulado. Correspondente aos modelos da aplicação.
 */
abstract class BDHelper<T> extends SQLiteOpenHelper {

    private static final String BD_NAME = "SisTrocasBD";
    private static int BD_VERSION = 1;

    protected SQLiteDatabase database;

    BDHelper(Context context) {
        super(context, BD_NAME, null, BD_VERSION);
        this.database = this.getWritableDatabase();
    }

    /**
     * Métodos de seleção de dados (SELECT).
     * @return Lista de objetos do tipo T.
     */
    public abstract ArrayList<T> select();

    /**
     * @param whereClause Query WHERE para filtragem de dados.
     * @param whereArgs Argumentos a passar em parâmetro.
     * @return Lista de objetos do tipo T, correspondentes aos dados de filtro passados.
     */
    public abstract ArrayList<T> select(String whereClause, String[] whereArgs);

    /**
     * @param id ID do Objeto.
     * @return Objeto do tipo T.
     */
    public abstract T selectByID(Long id);

    /**
     * Método de inserção de um objeto numa tabela (INSERT).
     * @param type Objeto do Tipo T.
     * @return Objeto que foi inserido, com o id correspondente ao mesmo da tabela.
     */
    public abstract T insert(T type);

    /**
     * Método de alteração de um objeto existente numa tabela (UPDATE).
     * @param type Objeto do Tipo T.
     * @return True, caso seja bem sucedido. False, caso tenha ocorrido um erro.
     */
    public abstract boolean update(T type);

    /**
     * Método de eliminação de um objeto existente numa tabela (DELETE).
     * @param id ID do objeto a ser eliminado
     * @return True, caso seja bem sucedido. False, caso tenha ocorrido um erro.
     */
    public abstract boolean delete(Long id);
}
