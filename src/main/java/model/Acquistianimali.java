package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the acquistianimali database table.
 * 
 */
@Entity
@NamedQuery(name="Acquistianimali.findAll", query="SELECT a FROM Acquistianimali a")
public class Acquistianimali implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACQUISTIANIMALI_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACQUISTIANIMALI_ID_GENERATOR")
	private int id;

	@Temporal(TemporalType.DATE)
	private Date dataAcquisto;

	private String matricola;

	private String nomeAnimale;

	private int prezzo;

	private String tipoAnimale;

	//bi-directional many-to-one association to Clienti
	@ManyToOne
	@JoinColumn(name="ClienteID")
	private Clienti clienti;

	public Acquistianimali() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDataAcquisto() {
		return this.dataAcquisto;
	}

	public void setDataAcquisto(Date dataAcquisto) {
		this.dataAcquisto = dataAcquisto;
	}

	public String getMatricola() {
		return this.matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public String getNomeAnimale() {
		return this.nomeAnimale;
	}

	public void setNomeAnimale(String nomeAnimale) {
		this.nomeAnimale = nomeAnimale;
	}

	/**
	 * @param dataAcquisto
	 * @param matricola
	 * @param nomeAnimale
	 * @param prezzo
	 * @param tipoAnimale
	 * @param clienti
	 */
	public Acquistianimali(Date dataAcquisto, String matricola, String nomeAnimale, int prezzo,
			String tipoAnimale, Clienti clienti) {
		super();
		this.dataAcquisto = dataAcquisto;
		this.matricola = matricola;
		this.nomeAnimale = nomeAnimale;
		this.prezzo = prezzo;
		this.tipoAnimale = tipoAnimale;
		this.clienti = clienti;
	}

	public int getPrezzo() {
		return this.prezzo;
	}

	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}

	public String getTipoAnimale() {
		return this.tipoAnimale;
	}

	public void setTipoAnimale(String tipoAnimale) {
		this.tipoAnimale = tipoAnimale;
	}

	public Clienti getClienti() {
		return this.clienti;
	}

	public void setClienti(Clienti clienti) {
		this.clienti = clienti;
	}

}