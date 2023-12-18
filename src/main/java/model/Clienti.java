package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the clienti database table.
 * 
 */
@Entity
@NamedQuery(name="Clienti.findAll", query="SELECT c FROM Clienti c")
public class Clienti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CLIENTI_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CLIENTI_ID_GENERATOR")
	private int id;

	private String citta;

	private String cognome;

	private String indirizzo;

	private String nome;

	private String tel;

	//bi-directional many-to-one association to Acquistianimali
	@OneToMany(mappedBy="clienti")
	private List<Acquistianimali> acquistianimalis;

	public Clienti() {
	}

	/**
	 * @param citta
	 * @param cognome
	 * @param indirizzo
	 * @param nome
	 * @param tel
	 * @param acquistianimalis
	 */
	public Clienti(String nome,String cognome, String citta,String tel,  String indirizzo) {
		super();
		this.citta = citta;
		this.cognome = cognome;
		this.indirizzo = indirizzo;
		this.nome = nome;
		this.tel = tel;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCitta() {
		return this.citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public List<Acquistianimali> getAcquistianimalis() {
		return this.acquistianimalis;
	}

	public void setAcquistianimalis(List<Acquistianimali> acquistianimalis) {
		this.acquistianimalis = acquistianimalis;
	}

	public Acquistianimali addAcquistianimali(Acquistianimali acquistianimali) {
		getAcquistianimalis().add(acquistianimali);
		acquistianimali.setClienti(this);

		return acquistianimali;
	}

	public Acquistianimali removeAcquistianimali(Acquistianimali acquistianimali) {
		getAcquistianimalis().remove(acquistianimali);
		acquistianimali.setClienti(null);

		return acquistianimali;
	}

}