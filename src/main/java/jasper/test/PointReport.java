package jasper.test;

import jasper.test.model.Point;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

public class PointReport {
	public static void main(String[] args) {
		text();
	}

	public static void text() {
		try {
			long start = System.currentTimeMillis();
			
			// reader file
			String dataFilePath = "src/main/resources/points.csv";
			FileInputStream fis = new FileInputStream(dataFilePath);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(isr);

			String line = reader.readLine();
			String[] cols = new String[] {};

			List<Point> points = new ArrayList<>();
			Point point = null;

			// convert to list object
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
			String reportOutputFilePath = "target/jasper/PointReport.txt";

			// create report
			JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplateFilePath);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, pointDataSource);
			File destFile = new File(reportOutputFilePath);

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
