package com.example.final_project.Repository;

import com.example.final_project.Model.TaxReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaxReportsRepository extends JpaRepository<TaxReports,Integer> {

    TaxReports findTaxReportsById(Integer id);

    List<TaxReports> findAllByPaymentDate(LocalDate paymentDate);

    List<TaxReports> findAllByPaymentDateIsNotNullAndStatusNot(String status);

    List<TaxReports> findAllByAuditorId(Integer auditorId);


    Long countByAuditorId(Integer auditorId);


    Long countByAuditorIdAndStatus(Integer auditorId, String status);



    @Query("select r from TaxReports r where r.auditor.id = ?1 order by r.end_date desc")
    List<TaxReports> findTopByAuditorIdOrderByEnd_dateDesc(Integer auditorId);

    @Query("select t from TaxReports t where t.business.taxPayer.id=?1")
    List<TaxReports> findTaxReportsByTaxPayer(Integer taxPayerId);


//    List<TaxReports> findTaxReportsByBranch(Integer branchId);

    @Query("SELECT t FROM TaxReports t WHERE t.status <> 'Approved'")
    List<TaxReports> findAllUnapproved();

}
