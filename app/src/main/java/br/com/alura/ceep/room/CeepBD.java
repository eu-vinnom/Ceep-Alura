package br.com.alura.ceep.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.room.dao.NotaDAO;

@Database(entities = Nota.class, version = 1)
public abstract class CeepBD extends RoomDatabase {

	private static final String CEEP_DB = "ceep.db";
	private static CeepBD instance;

	public static CeepBD getInstance(Context contexto) {
		if(instance == null) {
			instance = Room.databaseBuilder(contexto, CeepBD.class, CEEP_DB).build();
			return instance;
		}

		return instance;
	}

	public abstract NotaDAO getNotaDao();

}
