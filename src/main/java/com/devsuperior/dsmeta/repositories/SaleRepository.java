package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleSumProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query(nativeQuery = true, value = "SELECT name, SUM(amount) as sum "
			+ "FROM tb_seller "
			+ "INNER JOIN tb_sales ON tb_seller.id = tb_sales.seller_id "
			+ "WHERE date BETWEEN :minDate AND :maxDate "
			+ "GROUP BY name")
	List<SaleSumProjection> searchSaleSum(LocalDate minDate, LocalDate maxDate);
}
