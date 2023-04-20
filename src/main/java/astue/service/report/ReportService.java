package astue.service.report;

import com.itextpdf.text.Image;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jfree.chart.JFreeChart;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.data.statistics.HistogramDataset;

import astue.service.interfaces.RecordService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	private final  RecordService service;
	
	public ByteArrayInputStream createRecordList(LocalDateTime from,LocalDateTime to) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		String csvFileName = "report.csv";
//		response.setContentType("text/csv");
//		String headerKey = "Content-Disposition";
//		String headerValue = String.format("attachment; filename=\"%s\"",csvFileName);
//		response.setHeader(headerKey, headerValue);
		
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(new OutputStreamWriter(out),CsvPreference.STANDARD_PREFERENCE);
		String[] header = { "name",  "ip", "active","reactive","ied","power","created"};
		csvWriter.writeHeader(header);
		
		
		service.getAllBetween(from,to).stream()
			.map(RecordDto::new).forEach(x->{
								try {
									csvWriter.write(x,header);
								} catch (IOException e) {
									throw new RuntimeException(e);
								}
							});
		csvWriter.close();
		return new ByteArrayInputStream(out.toByteArray());
	}
	
	public ByteArrayInputStream createDeviceList(LocalDateTime from,LocalDateTime to) throws IOException {
		
		 String pattern = "dd-MM-yy HH:mm";
	        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern); 
//	        String dateString = format.format(date);
		
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			Paragraph preface = new Paragraph();
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{3, 3, 3, 3});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            preface.add(new Paragraph("Power consumption report", new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD)));
            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Name", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);


            hcell = new PdfPCell(new Phrase("active, KWh", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("ied", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("created", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);


                
                service.getAllBetween(from,to).stream().limit(10)
    			.map(RecordDto::new).toList().stream().forEach(x->{ 

			                table.addCell(createCell(x.getName()));
			                table.addCell(createCell(Double.toString(x.getActive())));
			                table.addCell(createCell(x.getIed().toString()));
			                table.addCell(createCell(format.format(x.getCreated())));
    											});

            PdfWriter writer =PdfWriter.getInstance(document, out);
            document.open();
            document.add(preface);
            document.add(table);
            document.add(Image.getInstance(writer, getChart(), 1.0f));

            document.close();

        } catch (DocumentException ex) {}

        return new ByteArrayInputStream(out.toByteArray());
	}
	
	private PdfPCell createCell(String content) {
		PdfPCell cell=new PdfPCell(new Phrase(content));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setBackgroundColor(Element.);
		return cell;
	}
	
	private BufferedImage getChart() {
		double[] vals = {

                0.71477137, 0.55749811, 0.50809619, 0.47027228, 0.25281568,
                0.66633175, 0.50676332, 0.6007552, 0.56892904, 0.49553407,
                0.61093935, 0.65057417, 0.40095626, 0.45969447, 0.51087888,
                0.52894806, 0.49397198, 0.4267163, 0.54091298, 0.34545257,
                0.58548892, 0.3137885, 0.63521146, 0.57541744, 0.59862265,
                0.66261386, 0.56744017, 0.42548488, 0.40841345, 0.47393027,
                0.60882106, 0.45961208, 0.43371424, 0.40876484, 0.64367337,
                0.54092033, 0.34240811, 0.44048106, 0.48874236, 0.68300902,
                0.33563968, 0.58328107, 0.58054283, 0.64710522, 0.37801285,
                0.36748982, 0.44386445, 0.47245989, 0.297599, 0.50295541,
                0.39785732, 0.51370486, 0.46650358, 0.5623638, 0.4446957,
                0.52949791, 0.54611411, 0.41020067, 0.61644868, 0.47493691,
                0.50611458, 0.42518211, 0.45467712, 0.52438467, 0.724529,
                0.59749142, 0.45940223, 0.53099928, 0.65159718, 0.38038268,
                0.51639554, 0.41847437, 0.46022878, 0.57326103, 0.44913632,
                0.61043611, 0.42694949, 0.43997814, 0.58787928, 0.36252603,
                0.50937634, 0.47444256, 0.57992527, 0.29381335, 0.50357977,
                0.42469464, 0.53049697, 0.7163579, 0.39741694, 0.41980533,
                0.68091159, 0.69330702, 0.50518926, 0.55884098, 0.48618324,
                0.48469854, 0.55342267, 0.67159111, 0.62352006, 0.34773486};


        var dataset = new HistogramDataset();
        dataset.addSeries("key", vals, 50);

        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Sample Chart",
                "Time", 
                "Consumtion, kWh",
                dataset,
                true,
                true,
                false
    );
//        chart.setBackgroundPaint(Color.GREEN);

        return chart.createBufferedImage(500, 300);
	}

}
