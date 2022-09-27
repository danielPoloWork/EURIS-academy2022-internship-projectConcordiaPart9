package com.euris.academy2022.concordia.dataPersistences.dataArchetypes;

public interface ModelArchetype {

	default DtoArchetype toDto() {
		return null;
	}
}