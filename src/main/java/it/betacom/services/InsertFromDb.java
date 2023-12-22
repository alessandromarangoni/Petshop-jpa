package it.betacom.services;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;

import it.betacom.daoImpl.AcquistiDaoImpl;
import it.betacom.daoImpl.ClientiDaoImpl;
import model.Acquistianimali;
import model.Clienti;

public class InsertFromDb {
	
	public static void loadData(){
		
		ClientiDaoImpl cd = new ClientiDaoImpl(); 
		AcquistiDaoImpl ad = new AcquistiDaoImpl();
		
		List<Clienti> listaClienti = cd.getAllClienti();
		List<Acquistianimali> listaAcquisti = ad.getAllAcquisti();
		LocalDate dataAttuale = LocalDate.now();
			 PdfWriter writer;
			try {
				writer = new PdfWriter("PetShopData.pdf");
				PdfDocument pdf = new PdfDocument(writer);
				Document document = new Document(pdf);
				Paragraph intestazioneClienti = new Paragraph("Clienti registrati entro il " + dataAttuale);
				document.add(intestazioneClienti);
				for (Clienti c : listaClienti) {
					String cliente ="| Id: " + c.getId() + " | nome: " + c.getNome() + " | cognome: " + c.getCognome()  + " | Città : " + c.getCitta() + " | Tel: "  + c.getTel() + " | indirizzo: "  +c.getIndirizzo();
					Paragraph p = new Paragraph(cliente);
					document.add(p);
				}
				Paragraph intestazioneAnimali = new Paragraph("acqquisti registrati entro il: "+ dataAttuale);
				document.add(intestazioneAnimali);
				
				for(Acquistianimali a : listaAcquisti) {
					String acquisto = "| marticola: " + a.getMatricola().trim() + " | tipo animale: " + a.getTipoAnimale() + " | nome animale: " + a.getNomeAnimale() + " | prezzo: " + a.getPrezzo() + " | proprietario: " + cd.readCliente(a.getClienti().getId()).getNome();
					Paragraph p = new Paragraph(acquisto);
					document.add(p);
				}
				document.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}

	
	public static void loadDataRelated(){
		ClientiDaoImpl cd = new ClientiDaoImpl(); 
		AcquistiDaoImpl ad = new AcquistiDaoImpl();
		
		List<Clienti> listaClienti = cd.getAllClienti();
		LocalDate dataAttuale = LocalDate.now();
		 PdfWriter writer;
		try {
			writer = new PdfWriter("PetShopDataRelated.pdf");
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf);
			Paragraph intestazioneClienti = new Paragraph("Clienti registrati entro il " + dataAttuale + " e transazioni annesse");
			intestazioneClienti.setFontSize(16);
			intestazioneClienti.setFontColor(ColorConstants.RED);
			document.add(intestazioneClienti);
			for (Clienti c : listaClienti) {
				String cliente ="Id: " + c.getId() + " | nome: " + c.getNome() + " | cognome: " + c.getCognome()  + " | Città : " + c.getCitta() + " | Tel: "  + c.getTel() + " | indirizzo: "  +c.getIndirizzo();
				Paragraph p = new Paragraph(cliente);
				document.add(p);
				
				List<Acquistianimali> acquisti = c.getAcquistianimalis();
				Collections.sort(acquisti, (a1, a2) -> a1.getDataAcquisto().compareTo(a2.getDataAcquisto()));
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				
				for(Acquistianimali a : acquisti){
					String formattata = formatter.format(a.getDataAcquisto());
					String acquisto = "- Data di acquisto: " + formattata +  " |  marticola: " + a.getMatricola().trim() + " | tipo animale: " + a.getTipoAnimale() + " | nome animale: " + a.getNomeAnimale() + " | prezzo: " + a.getPrezzo();
					Paragraph p1 = new Paragraph(acquisto);
					p1.setPaddingLeft(20);
					document.add(p1);
				}
			}
			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		
		loadData();
		loadDataRelated();

	}

}
