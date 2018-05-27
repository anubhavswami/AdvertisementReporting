package com.advertisementreporting.reporting.AdvertisementReporting.entities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsvDataRepository extends JpaRepository<CsvData, Long> {

	List<CsvData> findByMonthAndSite(String month, String site);

	List<CsvData> getAllReportForMonth(String pathMonth);

	List<CsvData> getAllReportForSite(String site);



}
