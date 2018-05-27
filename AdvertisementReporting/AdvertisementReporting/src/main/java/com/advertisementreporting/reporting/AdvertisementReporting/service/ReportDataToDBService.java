package com.advertisementreporting.reporting.AdvertisementReporting.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.advertisementreporting.reporting.AdvertisementReporting.entities.CsvData;
import com.advertisementreporting.reporting.AdvertisementReporting.entities.CsvDataRepository;

@Service
@Transactional
public class ReportDataToDBService {
	
	@Autowired
	CsvDataRepository repository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public int insert(CsvData data) {
		entityManager.persist(data);
		return data.getId();	
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	public List<CsvData> getAllReports() {
		return (List<CsvData>) repository.findAll();
	}

	
	public List<CsvData> findByMonthAndSite(String month, String site) {
		return repository.findByMonthAndSite(month, site);
	}

	public List<CsvData> getAllReportForMonth(String pathMonth) {
		return repository.getAllReportForMonth(pathMonth);
	}

	public List<CsvData> getAllReportForSite(String site) {
		return repository.getAllReportForSite(site);
	}

	
}
