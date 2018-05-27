package com.advertisementreporting.reporting.AdvertisementReporting.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.advertisementreporting.reporting.AdvertisementReporting.entities.CsvData;
import com.advertisementreporting.reporting.AdvertisementReporting.entities.CsvDataRepository;
import com.advertisementreporting.reporting.AdvertisementReporting.service.ReportDataToDBService;
import com.advertisementreporting.reporting.AdvertisementReporting.util.AdvertisementReportUtils;

@RestController
public class ReportController {
	
	@Autowired
	ReadCsv readCsv;
	
	@Autowired
	ReportDataToDBService DBservice;
	
	@Autowired
	CsvDataRepository repository;
	
	
	
	
	@GetMapping(value="/save")
	public int test() {
		List<CsvData> listdata = readCsv.persistData();	
		int valueId = 0;
		for(int i=0;i<listdata.size();i++) {
			valueId=DBservice.insert(listdata.get(i));
		}
		return valueId;
		
	}
	
	@GetMapping(value = "/reports")
	public List<CsvData> getAll() {
		return DBservice.getAllReports();
	}
	
	@GetMapping(value = "/reports/{month}/{site}")
	public List<CsvData> getByMonthAndSite(@PathVariable String month, @PathVariable String site) {
		String pathMonth=AdvertisementReportUtils.MonthNames(month);
		return DBservice.findByMonthAndSite(pathMonth, site);
	}
	
	
	@GetMapping(value = "/reports/month/{month}")
	public List<CsvData> getAllReportForMonth(@PathVariable String month) {
		String pathMonth=AdvertisementReportUtils.MonthNames(month);
		return DBservice.getAllReportForMonth(pathMonth);
	}
	
	@GetMapping(value = "/reports/{site}")
	public List<CsvData> getAllReportForSite(@PathVariable String site) {		
		return DBservice.getAllReportForSite(site);
	}
}
