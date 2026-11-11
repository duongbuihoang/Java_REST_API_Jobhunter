package vn.project.jobhunter.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.project.jobhunter.domain.Company;
import vn.project.jobhunter.domain.dto.Meta;
import vn.project.jobhunter.domain.dto.ResultPaginationDTO;
import vn.project.jobhunter.repository.CompanyRepository;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company handleCreateCompany(Company reqCompany) {
        return this.companyRepository.save(reqCompany);
    }

    public ResultPaginationDTO fetchAllCompany(Pageable pageable) {
        Page<Company> companyPage = this.companyRepository.findAll(pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        Meta meta = new Meta();

        meta.setPage(companyPage.getNumber() + 1);
        meta.setPageSize(companyPage.getSize());
        meta.setPages(companyPage.getTotalPages());
        meta.setTotal(companyPage.getTotalElements());

        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(companyPage.getContent());

        return resultPaginationDTO;
    }

    public Company handleUpdateCompany(Company reqCompany) {
        Optional<Company> CompanyOptional = this.companyRepository.findById(reqCompany.getId());
        if (CompanyOptional.isPresent()) {
            Company existingCompany = CompanyOptional.get();
            existingCompany.setName(reqCompany.getName());
            existingCompany.setDescription(reqCompany.getDescription());
            existingCompany.setAddress(reqCompany.getAddress());
            existingCompany.setLogo(reqCompany.getLogo());
            return this.companyRepository.save(existingCompany);
        }
        return null;
    }

    public void handleDeleteCompany(Long id) {
        this.companyRepository.deleteById(id);
    }
}
