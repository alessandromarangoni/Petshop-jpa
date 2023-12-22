package it.betacom.main;

import model.Clienti;
import model.Acquistianimali;
import org.apache.commons.csv.*;

import it.betacom.daoImpl.AcquistiDaoImpl;
import it.betacom.daoImpl.ClientiDaoImpl;
import it.betacom.singleton.JPAUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

public class ImportaDatiCSV {

    static String percorsoFile = "./PETSHOP.CSV";

    public static void leggiEImportaCSV(String percorsoFile) {
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(percorsoFile));
            reader.readLine(); //IMPORTANTE Salta prima riga o si rompe tutto
            try (CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader())) {
            	//prendo dati dal csv
                for (CSVRecord record : csvParser) {
                    String nome = record.get("Nome");
                    String cognome = record.get("Cognome");
                    String citta = record.get("Citta");
                    String tel = record.get("Tel");
                    String via = record.get("indirizzo");
                    //creo daoimpl x cliente
                    ClientiDaoImpl clientiDao = new ClientiDaoImpl();
                    //verifico se cliente esiste con funzione
                    Clienti cliente = trovaCliente(tel);
                    //se è nullo entro nell if
                    if (cliente == null) {
                    	//creo cliente
                        cliente = new Clienti(nome, cognome, citta, tel, via);
                        //aggiungo a db
                        clientiDao.CreateCliente(cliente);
                        //stampo a schermo --eliminare dopo
                        System.out.println("Cliente creato: " + nome);
                    } 
                    // creo dao impl per acquisti
                    AcquistiDaoImpl aDao = new AcquistiDaoImpl();
                    //prendo dati
                    String tipoAnimale = record.get("Tipo Animale");
                    String nomeAnimale = record.get("Nome Animale");
                    String matricola = record.get("Matricola");
                    String dataInStringa = record.get("DataAcquisto");
                    int prezzo = Integer.parseInt(record.get("Prezzo"));
                    //importante convertire la data
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                    	//faccio il parse della data presa da csv
                        Date data = formato.parse(dataInStringa);
                        //creo acquisto
                        Acquistianimali acquisto = new Acquistianimali(data, matricola, nomeAnimale, prezzo, tipoAnimale, cliente);
                        //inserisco nel db
                        aDao.createAcquisto(acquisto);
                        //aggiungo l'acquisto alla lista del cliente
                        cliente.addAcquistianimali(acquisto);
                        //faccio l'update del db con l'aggiunta del cliente
                        clientiDao.updateCliente(cliente);

                        //qui stampo a schermo volta per volta che si aggorna gli acquisti fatti 
                        List<Acquistianimali> acquistiCliente = cliente.getAcquistianimalis();
                        System.out.println("Acquisti di " + cliente.getNome() + ":");
                        for (Acquistianimali a : acquistiCliente) {
                        	System.out.println(" - " + a.getTipoAnimale() + ": " + (a.getNomeAnimale() != null && !a.getNomeAnimale().isEmpty() ? a.getNomeAnimale() : "Nessun nome") + " acquistato il " + formato.format(a.getDataAcquisto()) + " a " + a.getPrezzo() + " euro.");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Clienti trovaCliente(String telefono) {
    	//importo l istanza em da singleton
        EntityManager entityManager = JPAUtil.getEntityManager();
        try {
        	//creo query che verifica se il tel è gia esistente sul db
            Query query = entityManager.createQuery("SELECT c FROM Clienti c WHERE c.tel = :p", Clienti.class);
            //passo il parametro p che prendo dai parametri della funzione
            query.setParameter("p", telefono);
            //ritorno il singolo risultato altrimenti faccio eccezione (ho dovuto fare il cast a Clienti non so perche chiedere a daniele)
            return (Clienti) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (NonUniqueResultException e) {
            System.out.println("più di un record trovato");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
        	//chiudo em
            entityManager.close();
        }
    }

    //chiamo la funzione per prendere i dati
    public static void main(String[] args) {
        try {
            leggiEImportaCSV(percorsoFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
