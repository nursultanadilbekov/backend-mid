package com.example.midterm_project.service.impl;

import com.example.midterm_project.dto.repairer.RepairerRequest;
import com.example.midterm_project.dto.repairer.RepairerResponse;
import com.example.midterm_project.entities.Repairer;
import com.example.midterm_project.enums.Role;
import com.example.midterm_project.exception.BadCredentialsException;
import com.example.midterm_project.mapper.interfaces.RepairerMapper;
import com.example.midterm_project.repositories.RepairerRepository;
import com.example.midterm_project.service.interfaces.AuthenticateService;
import com.example.midterm_project.service.interfaces.RepairerService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
@AllArgsConstructor
public class RepairerServiceImpl implements RepairerService {
    final private RepairerRepository repairerRepository;
    final private RepairerMapper repairerMapper;
    final private AuthenticateService authenticateService;

    public void register(RepairerRequest repairerRequest, String token){
        if (!authenticateService.getUsernameFromToken(token).getRole().equals(Role.ADMIN))
            throw new BadCredentialsException("this function only for admin!");

        Repairer repairer=new Repairer();
        repairer.setName(repairerRequest.getName());
        repairer.setAge(repairerRequest.getAge());
        repairer.setExperience(repairerRequest.getExperience());
        repairer.setCost_of_work(repairerRequest.getCost_of_work());

        repairerRepository.save(repairer);
    }

    public void delete(Long id){
        Optional<Repairer> repairer=repairerRepository.findById(id);
        if(repairer.isEmpty()){
            System.out.println("repairer by id: "+id+" is empty");
        }else{
            repairerRepository.deleteById(id);
        }
    }

    public void update(Long id,RepairerRequest repairerRequest){
        Optional<Repairer>repairer=repairerRepository.findById(id);
        if(repairer.isEmpty()){
            System.out.println("repairer by id: "+id+" is empty");
        }else{
            repairer.get().setName(repairerRequest.getName());
            repairer.get().setAge(repairerRequest.getAge());
            repairer.get().setExperience(repairerRequest.getExperience());
            repairer.get().setCost_of_work(repairerRequest.getCost_of_work());

            repairerRepository.save(repairer.get());
        }
    }

    @Override
    public RepairerResponse find(Long id, String token) {
        Optional<Repairer>repairer=repairerRepository.findById(id);
        if(repairer.isEmpty()){
            System.out.println("repairer by id: "+id+" is empty");
            return null;
        }else{
            RepairerResponse repairerResponse=new RepairerResponse();
            return repairerMapper.toDto(repairer.get());
        }
    }

    public List<RepairerResponse> getAll(){
        return repairerMapper.toDtos(repairerRepository.findAll());
    }
}
