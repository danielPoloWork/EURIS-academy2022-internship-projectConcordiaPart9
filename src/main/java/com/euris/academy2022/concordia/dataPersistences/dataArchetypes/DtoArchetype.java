package com.euris.academy2022.concordia.dataPersistences.dataArchetypes;
public interface DtoArchetype{

	default ModelArchetype toModel() {
		return null;
	}
}