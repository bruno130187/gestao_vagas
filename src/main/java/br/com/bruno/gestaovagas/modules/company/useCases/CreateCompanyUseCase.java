package br.com.bruno.gestaovagas.modules.company.useCases;

import br.com.bruno.gestaovagas.exceptions.UserFoundException;
import br.com.bruno.gestaovagas.modules.company.entities.CompanyEntity;
import br.com.bruno.gestaovagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity companyEntity) {
        this.companyRepository.findByUsernameOrNameOrEmail(
                companyEntity.getUsername(),
                companyEntity.getName(),
                companyEntity.getEmail())
                .ifPresent(user -> {
                    throw new UserFoundException();
                });

        var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);

        return this.companyRepository.save(companyEntity);
    }
}
