package br.com.bruno.gestaovagas.modules.company.auth;

import br.com.bruno.gestaovagas.modules.company.dto.AuthCompanyDTO;
import br.com.bruno.gestaovagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.bruno.gestaovagas.modules.company.repositories.CompanyRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.token.secret.company}")
    private String secretKey;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("username/password incorrect.")
        );

        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(),company.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        } else {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            var expiresIn = Instant.now().plus(Duration.ofHours(2));

            var token =  JWT.create()
                    .withIssuer("javagas")
                    .withSubject(company.getId().toString())
                    .withClaim("roles", Arrays.asList("COMPANY"))
                    .withExpiresAt(expiresIn)
                    .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                    .sign(algorithm);

            var authCompanyResponseDTO = AuthCompanyResponseDTO.builder()
                    .access_token(token)
                    .expires_in(expiresIn.toEpochMilli())
                    .build();

            return authCompanyResponseDTO;
        }
    }
}
