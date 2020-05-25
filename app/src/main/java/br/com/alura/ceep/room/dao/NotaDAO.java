package br.com.alura.ceep.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.alura.ceep.model.Nota;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface NotaDAO {

	String DEVOLVE_TODAS_NOTAS = "SELECT * FROM Nota n ORDER BY n.posicao";

	@Query(DEVOLVE_TODAS_NOTAS)
	List<Nota> todas();

	@Insert(onConflict = REPLACE)
	long[] insere(Nota... notas);

	@Update(onConflict = REPLACE)
	Void altera(Nota nota);

	@Delete
	Void remove(Nota nota);

	@Update(onConflict = REPLACE)
	Void atualiza(Nota... notas);

}
