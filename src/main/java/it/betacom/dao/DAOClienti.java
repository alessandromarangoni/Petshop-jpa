package it.betacom.dao;

import java.util.List;

import model.Clienti;

public interface DAOClienti {
	List<Clienti> getAllClienti();
	void CreateCliente(Clienti clienti);
	Clienti readCliente(int id);
	void updateCliente(Clienti clienti);
	void deleteCliente(Clienti clienti);
}
