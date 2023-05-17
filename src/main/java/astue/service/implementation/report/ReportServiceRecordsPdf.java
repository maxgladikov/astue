//package astue.service.implementation.report;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import org.springframework.core.io.InputStreamResource;
//
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.pdf.PdfWriter;
//
//import astue.constants.ReportServiceType;
//import astue.model.Device;
//import astue.model.PowerRecord;
//import astue.service.RecordService;
//import astue.service.ReportService;
//import astue.service.implementation.report.dto.RecordSimplePdfDto;
//import astue.util.ChartUtil;
//import astue.util.PdfUtil;
//import lombok.RequiredArgsConstructor;
//@RequiredArgsConstructor
//public class ReportServiceRecordsPdf implements ReportService{
//	
//	private final  RecordService recordService;
//
//	@Override
//	public InputStreamResource create(LocalDateTime from, LocalDateTime to) {
//		String title = "Power consumption report";
//		
//		Document document = new Document();
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		try {
//            PdfWriter writer =PdfWriter.getInstance(document, out);
//            document.open();
//            // TITLE
//            PdfUtil.addTitle( document, title);
//            // BODY
//            Map<Device,List<PowerRecord>> devices=recordService.getAllBetween(from, to).parallelStream().collect(Collectors.groupingBy(PowerRecord::getDevice));
//            devices.keySet().parallelStream().forEachOrdered(x -> {
//        																		try {
//	    																				PdfUtil.addImage(document, new ChartUtil( x.getName(),x.getRecords() ).get());
//	    																				
//        																				PdfUtil.addTable(document, x.getName(),	Arrays.asList("cons","created"), 
//        																						devices.get(x).stream().map(y -> new RecordSimplePdfDto(y).getList()).toList()
//        																						);
//        																				
//																					
//																					} catch (DocumentException e) {
//																						e.printStackTrace();
//																					} catch (MalformedURLException e) {
//																						e.printStackTrace();
//																					} catch (IOException e) {
//																						e.printStackTrace();
//																					}
//            });
////            document.add(Image.getInstance(writer, getChart(), 1.0f));
//            document.close();
//
//        } catch (DocumentException ex) {}
//
//        return new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
//		
//	}
//
//	@Override
//	public ReportServiceType getType() {
//		return ReportServiceType.RECORDS_PDF;
//	}
//
//}
