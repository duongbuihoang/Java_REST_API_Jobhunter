package vn.project.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import vn.project.jobhunter.domain.Company;
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

    public List<Company> handleGetAllCompanies() {
        return this.companyRepository.findAll();
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
