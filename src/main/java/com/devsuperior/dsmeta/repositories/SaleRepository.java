package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleSumProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query(nativeQuery = true, value = "SELECT name, SUM(amount) as sum "
			+ "FROM tb_seller "
			+ "INNER JOIN tb_sales ON tb_seller.id = tb_sales.seller_id "
			+ "WHERE date BETWEEN :minDate AND :maxDate "
			+ "GROUP BY name")
	List<SaleSumProjection> searchSaleSum(LocalDate minDate, LocalDate maxDate);
	
	@Query("SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(obj.id, obj.date, obj.amount, obj.seller.name) "
			+ "FROM Sale obj "
			+ "WHERE obj.date BETWEEN :minDate AND :maxDate AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name,'%'))")
	Page<SaleReportDTO> searchSaleReport(Pageable pageable, LocalDate minDate, LocalDate maxDate, String name);
}
