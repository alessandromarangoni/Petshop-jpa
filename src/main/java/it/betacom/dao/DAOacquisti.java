package it.betacom.dao;

import java.util.List;

import model.Acquistianimali;

public interface DAOacquisti {
	List<Acquistianimali> getAllAcquisti();
	void createAcquisto(Acquistianimali acquisto);
	Acquistianimali readAcquisto(int id);
	void updateAcquisto(Acquistianimali acquisto);
	void deleteAcquisto(Acquistianimali acquisto);
}
