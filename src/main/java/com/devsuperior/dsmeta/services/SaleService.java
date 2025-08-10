package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSumDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleSumProjection;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
	
	public List<SaleSumDTO> searchSaleSum(String minDate, String maxDate) {
		LocalDate initialDate, lastDate;
		if (maxDate == null) {
			lastDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		else {
			lastDate = LocalDate.parse(maxDate);
		}
		if (minDate == null) {
			initialDate = lastDate.minusYears(1L);
		}
		else {
			initialDate = LocalDate.parse(minDate);
		}
		List<SaleSumProjection> result = repository.searchSaleSum(initialDate, lastDate);
		return result.stream().map(x -> new SaleSumDTO(x)).toList();
	}
	
	public Page<SaleReportDTO> searchSaleReport(Pageable pageable, String minDate, String maxDate, String name) {
		LocalDate initialDate, lastDate;
		if (maxDate == null) {
			lastDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		else {
			lastDate = LocalDate.parse(maxDate);
		}
		if (minDate == null) {
			initialDate = lastDate.minusYears(1L);
		}
		else {
			initialDate = LocalDate.parse(minDate);
		}
		if (name == null) {
			name = "";
		}
		Page<SaleReportDTO> result = repository.searchSaleReport(pageable, initialDate, lastDate, name);
		return result;
	}
}
