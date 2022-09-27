package com.danielpolo.game.dataPersistences.dataArchetypes;

public interface ModelArchetype {

	default DtoArchetype toDto() {
		return null;
	}
}