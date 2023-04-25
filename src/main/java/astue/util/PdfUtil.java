package astue.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.itextpdf.text.Document;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import astue.service.implementation.report.Chart;
import lombok.Getter;

public class PdfUtil {
	@Getter
	private final static Font titleFont=new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
	private final static Font paragraphNameFont=new Font(Font.FontFamily.TIMES_ROMAN, 14,Font.BOLD);
	private final static Font contentFont=new Font(Font.FontFamily.TIMES_ROMAN, 14,Font.BOLD);
	
	
	
	public static void addTitle(Document document, String title) throws DocumentException {
		document.add(PdfUtil.createTitle(title));
		document.newPage();
	}
	
	public static void addTable(Document document, String title,List<String> header,List<List<String>> body) throws DocumentException {
		document.add(PdfUtil.createParagraphName(title));
		document.add(PdfUtil.createTable(header, body));
	}
	
	private static PdfPTable createTable(List<String> header,List<List<String>> body) throws DocumentException {
		 
		// create a table
		 PdfPTable table = new PdfPTable(header.size());
		 table.setWidthPercentage(80);
//         table.setWidths(new int[]{3, 3});
		 PdfPCell cell;
		 // Header
		 header.stream().forEach(x -> table.addCell(PdfUtil.createCell(x)));
		 
		 // Cells
		 body.stream().forEach(x -> x.stream().forEach(y -> table.addCell(PdfUtil.createCell(y))));
		 
		 
		 return table;
	 }
	
	private static PdfPCell createCell(String content) {
		PdfPCell cell=new PdfPCell(new Phrase(content));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		return cell;
	}
	
	public static void addImage(Document document, BufferedImage image) throws IOException, BadElementException, DocumentException {
		document.add( new Paragraph(" "));
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		ImageIO.write( image ,"png" , baos);
		document.add(Image.getInstance( baos.toByteArray()) );
		document.add( new Paragraph(" "));
	}
	
	 private static  Paragraph createTitle( String name) {
		 
		 Paragraph paragraph=new Paragraph(name,titleFont);
		 paragraph.setAlignment(Element.ALIGN_CENTER);
		 paragraph.setSpacingAfter(50);
		 paragraph.setSpacingBefore(50);
		 return paragraph;
	 }
	 private static  Paragraph createParagraphName( String name) {
		 Paragraph paragraph=new Paragraph(name,paragraphNameFont);
		 paragraph.setAlignment(Element.ALIGN_CENTER);
		 paragraph.setSpacingAfter(10);
		 paragraph.setSpacingBefore(50);
		 return paragraph;
	 }
	

}
