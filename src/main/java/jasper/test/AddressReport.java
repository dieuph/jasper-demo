package jasper.test;

import jasper.test.model.Address;
import jasper.test.util.ObjectComparator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

public class AddressReport {

	public static void main(String[] args) {
		text();
	}

	public static void text() {
		try {
			long start = System.currentTimeMillis();
			
			// reader file
			String dataFilePath = "src/main/resources/address.csv";
			FileInputStream fis = new FileInputStream(dataFilePath);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(isr);
			
			String line = reader.readLine();
			String[] cols = new String[] {};

			List<Address> addresses = new ArrayList<>();
			Address address = null;

			// convert to list object
			while ((line = reader.readLine()) != null) {
				cols = line.split(",");

				address = new Address();

				address.setId(Integer.parseInt(cols[0]));
				address.setFirstName(cols[1]);
				address.setLastName(cols[2]);
				address.setStreet(cols[3]);
				address.setCity(cols[4]);

				addresses.add(address);
			}
			
			// sort address by city value
			ObjectComparator c = new ObjectComparator();
			c.addKey("getCity", true);
			Collections.sort(addresses, c);
			
			// convert list object to jasper bean
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(addresses);

			// create report
			String reportTemplateFilePath = "src/main/jasperreports/AddressReport.jrxml";
			String reportOutputFilePath = "target/jasper/AddressReport.txt";

			// preparing parameters
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ReportTitle", "Address Report");
			
			JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplateFilePath);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			File destFile = new File(reportOutputFilePath);

			// setting report input & ouput
			JRTextExporter exporter = new JRTextExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleWriterExporterOutput(destFile));
			exporter.exportReport();
			
			reader.close();
			
			System.err.println("Text creation time : " + (System.currentTimeMillis() - start));
			System.err.println("Report location : " + destFile.getCanonicalPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
