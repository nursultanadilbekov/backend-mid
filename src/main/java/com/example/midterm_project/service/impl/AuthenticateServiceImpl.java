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
import com.example.midterm_project.service.interfaces.AuthenticateService;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minidev.json.parser.ParseException;

@Service
@AllArgsConstructor
public class AuthenticateServiceImpl implements AuthenticateService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RepairerRepository repairerRepository;
    @Override
    public void register(UserAuthRequest userAuthRequest) {
        if (userRepository.findByEmail(userAuthRequest.getEmail()).isPresent())
            throw new BadRequestException("user with this email is already exist!: "+userAuthRequest.getEmail());
        User user = new User();
        user.setRole(Role.valueOf(userAuthRequest.getRole()));
        user.setEmail(userAuthRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userAuthRequest.getPassword()));


        if (userAuthRequest.getRole().equals(Role.REPAIRER)){

            Repairer repairer = new Repairer();
            repairer.setName(user.getName());
            repairerRepository.save(repairer);

            user.setRepairer(repairer);
            userRepository.save(user);

        }

        if(userAuthRequest.getRole().equals(Role.CUSTOMER)){
            Customer customer = new Customer();
            customer.setName(user.getName());
            customerRepository.save(customer);

            user.setCustomer(customer);
            userRepository.save(user);
        }

        userRepository.save(user);
    }

    @Override
    public UserAuthResponse login(UserAuthRequest userAuthRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userAuthRequest.getEmail(),
                    userAuthRequest.getPassword()));
        } catch (BadCredentialsException e){
            throw new BadCredentialsException("...");
        }
        User user = userRepository.findByEmail(userAuthRequest.getEmail()).orElseThrow();
        return convertToAuthResponse(user);
    }

    @Override
    public User getUsernameFromToken(String token){

        String[] chunks = token.substring(7).split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        JSONParser jsonParser = new JSONParser();
        JSONObject object = null;
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

        String token = jwtService.generateToken(extraClaims, user);
        System.out.println("Generated Token: " + token);

        userAuthResponse.setToken(token);
        return userAuthResponse;
    }
}
