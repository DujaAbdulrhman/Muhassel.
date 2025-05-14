package com.example.final_project.Service;

import com.example.final_project.Api.ApiException;
import com.example.final_project.DTO.BusinessDTO;
import com.example.final_project.DTOOUT.SalesDTO;
import com.example.final_project.Model.Branch;
import com.example.final_project.Model.Business;
import com.example.final_project.Model.Sales;
import com.example.final_project.Model.TaxPayer;
import com.example.final_project.Repository.BranchRepository;
import com.example.final_project.Repository.BusinessRepository;
import com.example.final_project.Repository.SalesRepository;
import com.example.final_project.Repository.TaxPayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessService {


    private final TaxPayerRepository taxPayerRepository;
    private final BusinessRepository businessRepository;
    private final BranchRepository branchRepository;
    private final SalesRepository salesRepository;


    public List<Business> getAllBusiness(Integer AuditId) {
        return businessRepository.findAll();
    }


    /// get one business details for taxpayer
    public BusinessDTO getMyBusiness(Integer taxPayerId, Integer bId) {
        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);

        if (taxPayer == null) {
            throw new ApiException("The Taxpayer is not found");
        }

        Business myBusiness = businessRepository.findBusinessByIdAndTaxPayer(bId, taxPayer);
        if (myBusiness == null) {
            throw new ApiException("Business is not found or does not belong to tax payer");
        }

        BusinessDTO businessDTO = new BusinessDTO(myBusiness.getBusinessName(), myBusiness.getBusinessCategory(), myBusiness.getCommercialRegistration(), myBusiness.getTaxNumber(), myBusiness.getCity(), myBusiness.getRegion(), myBusiness.getIsActive());
        return businessDTO;
    }


    /// get all business details for taxpayer

    public List<Business> getMyBusinesses(Integer taxPayerId) {

        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);
        if (taxPayer == null) {
            throw new ApiException("The Taxpayer is not found");
        }

        List<Business> myBusinesses = businessRepository.findBusinessByTaxPayer(taxPayer);

        if (myBusinesses.isEmpty()) {
            throw new ApiException("Businesses are not found or does not belong to tax payer");
        }
        return myBusinesses;
    }


    /// // number of branches for each business
    public Integer getMyBranches(Integer taxPayerId, Integer bId) {
        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);

        if (taxPayer == null) {
            throw new ApiException("The Taxpayer is not found");
        }
        Business myBusiness = businessRepository.findBusinessByIdAndTaxPayer(bId, taxPayer);
        if (myBusiness == null) {
            throw new ApiException("Business is not found or does not belong to tax payer");
        }


        List<Branch> branches = branchRepository.findBranchByBusiness(myBusiness);


        Integer NOFBranches = branches.size();

        return NOFBranches;
    }


    public void createMainBranch(Integer businessId, Branch branch) {

        Business business = businessRepository.findBusinessById(businessId);
        if (business == null)
            throw new ApiException("business not found");
        branch.setBusiness(business);
        branch.setBranchNumber("B1");
        branch.setRegion(business.getRegion());
        branch.setCity(business.getCity());
        branchRepository.save(branch);

    }


    public void addBusiness(Integer taxPayerId, BusinessDTO businessDTO) {

        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);

        if (taxPayer == null) {
            throw new ApiException("The Taxpayer is not found");

        }

        if (!taxPayer.getIsActive()) {
            throw new ApiException("Tax Payer :" + taxPayer.getMyUser().getName() + " is not Active"
                    + "--inactive taxpayers are unable to add business");
        }

        if (businessRepository.findBusinessByBusinessName(businessDTO.getBusinessName()) != null) {
            throw new ApiException("A Business With the same name already exit");

        }

        Business business = new Business();
        business.setBusinessName(businessDTO.getBusinessName());
        business.setBusinessCategory(businessDTO.getBusinessCategory());
        business.setCommercialRegistration(businessDTO.getCommercialRegistration());
        business.setTaxNumber(businessDTO.getTaxNumber());
        business.setCity(businessDTO.getCity());
        business.setRegion(businessDTO.getRegion());


        business.setTaxPayer(taxPayer);
        business.setIsActive(false);


        businessRepository.save(business);


        Branch mainBranch = new Branch();
        createMainBranch(business.getId(), mainBranch);

    }


    public void updateBusiness(Integer taxPayerId, Integer businessId, BusinessDTO businessDTO) {

        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);
        if (taxPayer == null) {
            throw new ApiException("The Taxpayer is not found");

        }
        Business business = businessRepository.findBusinessByIdAndTaxPayer(businessId, taxPayer);

        if (business == null) {
            throw new ApiException("The business is not found or not related to the tax payer");
        }


        business.setBusinessName(businessDTO.getBusinessName());
        business.setBusinessCategory(businessDTO.getBusinessCategory());
        business.setCity(businessDTO.getCity());
        business.setRegion(businessDTO.getRegion());

        businessRepository.save(business);

    }


    public void deleteBusiness(Integer taxPayerId, Integer businessID) {

        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);
        if (taxPayer == null) {
            throw new ApiException("The Taxpayer is not found");

        }
        Business business = businessRepository.findBusinessByIdAndTaxPayer(businessID, taxPayer);


        if (business == null) {
            throw new ApiException("The business is not found or not related to the tax payer");
        }


    }


    public List<SalesDTO> salesOperationOnBusiness(Integer taxPayerId, Integer businessId) {


        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);
        if (taxPayer == null) {
            throw new ApiException("The Taxpayer is not found");
        }

        Business business = businessRepository.findBusinessByIdAndTaxPayer(businessId, taxPayer);

        if (business == null) {
            throw new ApiException("The business is not found or not related to the tax payer");
        }


        List<Branch> businessBranches = branchRepository.findBranchByBusiness(business);
        if (businessBranches.isEmpty()) {
            throw new ApiException("No branches are available for this business");
        }

        List<SalesDTO> branchSalesDTO = new ArrayList<>();

        for (Branch b : businessBranches) {

            List<Sales> branchSales = salesRepository.findSalesByBranch(b);

            if (branchSales.isEmpty()) {
                continue;
            }

            for (Sales s : branchSales) {
                SalesDTO salesDTO = new SalesDTO(s.getSale_invoice(), s.getGrand_amount());
                branchSalesDTO.add(salesDTO);
            }
        }
        if (branchSalesDTO.isEmpty()) {
            throw new ApiException("No sales are available for this business");
        }
        return branchSalesDTO;

    }

    public Double businessRevenue(Integer taxPayerId, Integer businessId) {


        TaxPayer taxPayer = taxPayerRepository.findTaxBuyerById(taxPayerId);
        if (taxPayer == null) {
            throw new ApiException("The Taxpayer is not found");
        }

        Business business = businessRepository.findBusinessByIdAndTaxPayer(businessId, taxPayer);

        if (business == null) {
            throw new ApiException("The business is not found or not related to the tax payer");
        }

        Double totalRevenue = 0.0;

        List<Branch> businessBranches = branchRepository.findBranchByBusiness(business);
        if (businessBranches.isEmpty()) {
            throw new ApiException("No branches are available for this business");
        }

        for (Branch b : businessBranches) {


            List<Sales> branchSales = salesRepository.findSalesByBranch(b);

            if (branchSales.isEmpty()) {
                continue;
            }

            for (Sales s : branchSales) {
                totalRevenue = totalRevenue + s.getGrand_amount();
            }

        }


        return totalRevenue;
    }


}
