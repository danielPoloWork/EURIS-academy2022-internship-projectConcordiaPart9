package com.danielpolo.game.dataPersistences.dataArchetypes;

public interface DtoArchetype{

	default ModelArchetype toModel() {
		return null;
	}
}