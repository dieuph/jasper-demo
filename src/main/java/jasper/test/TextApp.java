package jasper.test;

import jasper.test.model.Address;
import jasper.test.model.Point;
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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.util.AbstractSampleApp;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXmlExporterOutput;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class TextApp extends AbstractSampleApp {

	/**
	 *
	 */
	public static void main(String[] args) {
		main(new TextApp(), args);
	}

	/**
	 *
	 */
	public void test() throws JRException {
		fill();
		pdf();
		xml();
		text();
	}

	/**
	 *
	 */
	public void fill() throws JRException {
		long start = System.currentTimeMillis();
		// Preparing parameters
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ReportTitle", "Address Report");
		parameters.put("FilterClause", "'Boston', 'Chicago', 'Oslo'");
		parameters.put("OrderClause", "City");

		JasperFillManager.fillReportToFile("target/jasper/TextReport.jasper",
				parameters, getDemoHsqldbConnection());
		System.err.println("Filling time : "
				+ (System.currentTimeMillis() - start));
	}

	/**
	 *
	 */
	public void print() throws JRException {
		long start = System.currentTimeMillis();
		JasperPrintManager
				.printReport("target/jasper/TextReport.jrprint", true);
		System.err.println("Printing time : "
				+ (System.currentTimeMillis() - start));
	}

	/**
	 *
	 */
	public void text() throws JRException {
		// long start = System.currentTimeMillis();
		// JRTextExporter exporter = new JRTextExporter();
		// File sourceFile = new File("target/jasper/TextReport.jrprint");
		// JasperPrint jasperPrint =
		// (JasperPrint)JRLoader.loadObject(sourceFile);
		// File destFile = new File(sourceFile.getParent(),
		// jasperPrint.getName() + ".txt");
		//
		// exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		// exporter.setExporterOutput(new SimpleWriterExporterOutput(destFile));
		// exporter.exportReport();
		//
		// System.err.println("Text creation time : " +
		// (System.currentTimeMillis() - start));

		try {
			String dataFilePath = "src/main/resources/address.csv";
			FileInputStream fis = new FileInputStream(dataFilePath);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(isr);
			
			String line = reader.readLine();
			String[] cols = new String[] {};

			List<Address> addresses = new ArrayList<>();
			Address address = null;

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
			ObjectComparator c = new ObjectComparator();
			c.addKey("getCity", true);
			Collections.sort(addresses, c);
			
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(addresses);

			String reportTemplateFilePath = "src/main/jasperreports/TextReport.jrxml";
			String reportOutputFilePath = "target/jasper/TextReport.txt";

			JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplateFilePath);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
			File destFile = new File(reportOutputFilePath);

			JRTextExporter exporter = new JRTextExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleWriterExporterOutput(destFile));
			exporter.exportReport();
			
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void text1() throws JRException {
		try {
			String dataFilePath = "src/main/resources/points.csv";
			FileInputStream fis = new FileInputStream(dataFilePath);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(isr);

			String line = reader.readLine();
			String[] cols = new String[] {};

			List<Point> points = new ArrayList<>();
			Point point = null;

			while ((line = reader.readLine()) != null) {
				cols = line.split(",");

				point = new Point();

				point.setUserName(cols[0]);
				point.setLastAccessed(cols[1]);
				point.setIsActive(cols[2]);
				point.setPoints(cols[3]);

				points.add(point);
			}
			JRBeanCollectionDataSource pointDataSource = new JRBeanCollectionDataSource(points);

			String reportTemplateFilePath = "src/main/jasperreports/PointsReport.jrxml";
			String reportOutputFilePath = "target/jasper/TextReport.txt";

			JasperReport jasperReport = JasperCompileManager
					.compileReport(reportTemplateFilePath);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, null, pointDataSource);
			File destFile = new File(reportOutputFilePath);

			JRTextExporter exporter = new JRTextExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleWriterExporterOutput(destFile));
			exporter.exportReport();

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	public void pdf() throws JRException {
		long start = System.currentTimeMillis();
		JasperExportManager
				.exportReportToPdfFile("target/jasper/TextReport.jrprint");
		System.err.println("PDF creation time : "
				+ (System.currentTimeMillis() - start));
	}

	/**
	 *
	 */
	public void xml() throws JRException {
		long start = System.currentTimeMillis();
		File sourceFile = new File("target/jasper/TextReport.jrprint");

		JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);

		File destFile = new File(sourceFile.getParent(), jasperPrint.getName()
				+ ".jrpxml");

		JRXmlExporter exporter = new JRXmlExporter();

		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleXmlExporterOutput(destFile));

		exporter.exportReport();

		System.err.println("XML creation time : "
				+ (System.currentTimeMillis() - start));
	}

}
