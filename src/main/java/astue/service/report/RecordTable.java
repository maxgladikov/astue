package astue.service.report;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.Font;

import astue.model.PowerRecord;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class RecordTable {

	private static final Font font = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL,  BaseColor.BLACK);
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm"); 
	private final List<PowerRecord> records;
	
	public PdfPTable createTable() throws DocumentException {
		 
		 PdfPTable table = new PdfPTable(2);
		 table.setWidthPercentage(80);
         table.setWidths(new int[]{3, 3});
		 PdfPCell hcell;

		 hcell = new PdfPCell(new Phrase("created", font));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);
        
        hcell = new PdfPCell(new Phrase("active, kWh", font));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);
		
        records.stream().forEach(x -> 
        							{
        								table.addCell(createCell(format.format(x.getCreated())));
        								table.addCell(createCell(String.format("%.2f", x.getActivePowerConsumption())));
        							});
        
		 return table;
		 
	 }
	
	private PdfPCell createCell(String content) {
		PdfPCell cell=new PdfPCell(new Phrase(content));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		return cell;
	}
	
	 private static void addEmptyLine(Paragraph paragraph, int number) {
	        for (int i = 0; i < number; i++) {
	            paragraph.add(new Paragraph(" "));
	        }
	    }
	
}
