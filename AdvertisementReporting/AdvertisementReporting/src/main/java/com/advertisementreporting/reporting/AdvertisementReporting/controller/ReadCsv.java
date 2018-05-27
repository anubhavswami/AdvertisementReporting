package com.advertisementreporting.reporting.AdvertisementReporting.controller;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.advertisementreporting.reporting.AdvertisementReporting.entities.CsvData;
import com.advertisementreporting.reporting.AdvertisementReporting.entities.CsvDataRepository;
import com.advertisementreporting.reporting.AdvertisementReporting.service.ReportDataToDBService;

@Component
public class ReadCsv {	
	
	@Value("${app.filepath}")
	private String fileDirectory;
	
	public List<CsvData> persistData() {
				
		List<CsvData> listData = new ArrayList<>();		
		List<String> file = parseForCsvFiles(fileDirectory);
		for (int i = 0; i < file.size(); i++) {
			listData.addAll(parseCSVFile(file.get(i)));
		}
		return listData;
	}

	public static List<CsvData> parseCSVFile(String file) {
		
		List<CsvData> listData = new ArrayList<>();
		String csvFile = file;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String month = getMonthForInt(Integer.parseInt(getMonth(file))-1);
				
		try {
			br = new BufferedReader(new FileReader(csvFile));
			String headerLine = br.readLine();
			while ((line = br.readLine()) != null) {	
				CsvData CsvData = new CsvData();

				// use comma as separator
				String[] report = line.split(cvsSplitBy);

				CsvData.setSite(report[0]);
				CsvData.setRequests(Long.parseLong(report[1].trim()));
				CsvData.setImpressions(Long.parseLong(report[2].trim()));
				CsvData.setClicks(Long.parseLong(report[3].trim()));
				CsvData.setConversions(Long.parseLong(report[4].trim()));
				CsvData.setRevenue(Double.parseDouble(report[5].trim()));
				CsvData.setMonth(month);	
				
				double CTR = calculateClickThroughRate(CsvData.getClicks(),CsvData.getImpressions());
				double CR = calculateConversionRate(CsvData.getConversions(), CsvData.getImpressions());
				double fillRate = calculateFillRate(CsvData.getImpressions(), CsvData.getRequests());
				double eCPM = calculateRevenue(CsvData.getRevenue(), CsvData.getImpressions());
				
				CsvData.setCTR(CTR);
				CsvData.setCR(CR);
				CsvData.setFillRate(fillRate);
				CsvData.set_eCPM(eCPM);
				
				listData.add(CsvData);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return listData;

	}
	
	/**
	 * @param clicks
	 * @param impressions
	 * @return
	 */
	private static double calculateClickThroughRate(Long clicks, Long impressions) {
		return roundedOff((clicks/impressions)*100);
	}
	
	/**
	 * @param conversions
	 * @param impressions
	 * @return
	 */
	private static double calculateConversionRate(Long conversions, Long impressions) {
		return roundedOff((conversions/impressions)*100);
	}
	
	/**
	 * @param revenue
	 * @param impressions
	 * @return
	 */
	private static double calculateRevenue(double revenue, Long impressions) {
		return roundedOff((long) ((revenue*1000)/impressions));
	}

	/**
	 * @param impressions
	 * @param requests
	 * @return
	 */
	private static double calculateFillRate(Long impressions, Long requests) {
		return roundedOff((impressions/requests)*100);
	}

	/**
	 * @param l
	 * @return
	 */
	private static double roundedOff(long l) {
		
		double finalValue = 0;
		double value = (double)l;
		DecimalFormat df=new DecimalFormat("0.00");
		String formate = df.format(value); 
		finalValue = Double.parseDouble(formate);
		
		return finalValue;
	}

	/**
	 * @param parentDirectory
	 * @return
	 */
	public static List<String> parseForCsvFiles(String parentDirectory) {
		List<String> fileFound = new ArrayList<>();
		File[] filesInDirectory = new File(parentDirectory).listFiles();
		for (File f : filesInDirectory) {
			if (f.isDirectory()) {
				parseForCsvFiles(f.getAbsolutePath());
			}
			String filePath = f.getAbsolutePath();
			String fileExtenstion = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
			if ("csv".equals(fileExtenstion)) {
				fileFound.add(filePath);
			}
		}
		return fileFound;
	}
	
	/**
	 * @param str
	 * @return
	 */
	public static String getMonth(String str) {

		String month = null;
		StringTokenizer st2 = new StringTokenizer(str, "_");
		
		while (st2.hasMoreElements()) {
			st2.nextElement();
			month=st2.nextElement().toString();
			st2.nextElement();
		}
		return month;
	}
	
	/**
	 * @param m
	 * @return
	 */
	public static String getMonthForInt(int m) {
		System.out.println(m);
	    String month = "invalid";
	    DateFormatSymbols dfs = new DateFormatSymbols();
	    String[] months = dfs.getMonths();
	    if (m >= 0 && m <= 11 ) {
	        month = months[m];
	    }
	    return month;
	}
}
