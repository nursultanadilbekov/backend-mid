package com.example.midterm_project.service.impl;

import com.example.midterm_project.config.JwtService;
import com.example.midterm_project.dto.user.UserAuthRequest;
import com.example.midterm_project.dto.user.UserAuthResponse;
import com.example.midterm_project.entities.Customer;
import com.example.midterm_project.entities.Repairer;
import com.example.midterm_project.entities.User;
import com.example.midterm_project.enums.Role;
import com.example.midterm_project.exception.BadCredentialsException;
import com.example.midterm_project.exception.BadRequestException;
import com.example.midterm_project.repositories.CustomerRepository;
import com.example.midterm_project.repositories.RepairerRepository;
import com.example.midterm_project.repositories.UserRepository;
import com.example.midterm_project.service.interfaces.MailService;
import com.example.midterm_project.service.interfaces.AuthenticateService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthenticateServiceImpl implements AuthenticateService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RepairerRepository repairerRepository;
    private final MailService emailService;

    @Override
    public void register(UserAuthRequest userAuthRequest) {
        if (userRepository.findByEmail(userAuthRequest.getEmail()).isPresent())
            throw new BadRequestException("user with this email is already exist!: " + userAuthRequest.getEmail());

        User user = new User();
        user.setRole(Role.valueOf(userAuthRequest.getRole()));
        user.setEmail(userAuthRequest.getEmail());
        user.setName(userAuthRequest.getName());
        user.setPassword(passwordEncoder.encode(userAuthRequest.getPassword()));

        String verificationToken = UUID.randomUUID().toString();
        user.setVerificationToken(verificationToken);
        user.setVerificationTokenExpiration(LocalDateTime.now().plusHours(1));

        if (user.getRole() == Role.REPAIRER) {
            Repairer repairer = new Repairer();
            repairer.setName(user.getName());
            repairerRepository.save(repairer);
            user.setRepairer(repairer);
        }

        if (user.getRole() == Role.CUSTOMER) {
            Customer customer = new Customer();
            customer.setName(user.getName());
            customerRepository.save(customer);
            user.setCustomer(customer);
        }

        userRepository.save(user);
        try {
            emailService.sendVerificationEmail(user.getEmail(), verificationToken);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserAuthResponse login(UserAuthRequest userAuthRequest) {
        User user = userRepository.findByEmail(userAuthRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isEmailVerified()) {
            throw new BadRequestException("Please verify your email address.");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userAuthRequest.getEmail(),
                            userAuthRequest.getPassword()
                    )
            );
        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credentials provided.");
        }

        return convertToAuthResponse(user);
    }

    @Override
    public UserAuthResponse refreshToken(String refreshToken) {
        String email = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new RuntimeException("Refresh token is invalid or expired.");
        }

        return convertToAuthResponse(user);
    }

    @Override
    public User getUsernameFromToken(String token) {
        String[] chunks = token.substring(7).split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        JSONParser jsonParser = new JSONParser();
        JSONObject object;
        try {
            object = (JSONObject) jsonParser.parse(decoder.decode(chunks[1]));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return userRepository.findByEmail(String.valueOf(object.get("sub"))).orElseThrow(() -> new RuntimeException("user can be null"));
    }

    private UserAuthResponse convertToAuthResponse(User user) {
        UserAuthResponse userAuthResponse = new UserAuthResponse();
        userAuthResponse.setEmail(user.getEmail());
        userAuthResponse.setRole(user.getRole().name());

        Map<String, Object> extraClaims = new HashMap<>();
        String accessToken = jwtService.generateToken(extraClaims, user);
        String refreshToken = jwtService.generateRefreshToken(user);

        userAuthResponse.setToken(accessToken);
        userAuthResponse.setRefreshToken(refreshToken);
        return userAuthResponse;
    }
}
