package faheem.microservices.belan.service;

import faheem.microservices.belan.BelanRepository.BelanRepository;
import faheem.microservices.belan.entity.Belan;
import faheem.microservices.belan.exceptions.BelanNotAddedException;
import faheem.microservices.belan.exceptions.BelanNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class BelanService {

    @Autowired
    private BelanRepository belanRepository;

    public Belan addBelan(Belan belan) throws BelanNotAddedException{
        log.info("BelanService.addBelan() method is called...");
        if(belan!=null){
            log.info("Belan object is not null and is : {} ",belan);
         return belanRepository.save(belan);
        }
        else{
           log.info("belan object is null and cannot be added");
            throw new BelanNotAddedException("a null belan cannot be added!");
        }


    }
    public Belan getBelanById(Integer belanId){
        log.info("BelanService.getBelanById() method is called...");
        if (belanId == null) {
            log.error("Belan id is null");
            throw new IllegalArgumentException("Belan id cannot be null");
        }
        Optional<Belan> optionalBelan = belanRepository.findById(belanId);
        if(optionalBelan.isPresent()){
            log.info("Belan found with id : {}",belanId);
            return optionalBelan.get();
        }
        else{
            log.info("Belan not found with id : {}",belanId);
            throw new NoSuchElementException("belan not found!");
        }
    }
   

    public Belan getBelanByColor(String belanColor){
        log.info("BelanService.getBelanByColor method is called...");
        log.info("searching for belan with color : {}",belanColor);
        Belan belanRetrieved = belanRepository.findByBelanColor(belanColor);
        if(belanRetrieved!=null){
            log.info("belan by color found : {}",belanRetrieved);
            return belanRetrieved;
        }
        else{
            log.error("Belan not found by color : {}",belanColor);
            throw new BelanNotFoundException("belan not found!");
        }
    }

    public Belan getBelanByName(String belanName){
        log.info("BelanService.getBelanByName() method is called...");
        log.info("searching for Belan with Name : {}",belanName);
        Belan belanRetrieved =belanRepository.findByBelanName(belanName);
        if(belanRetrieved!=null){
            log.info("Belan found : {}",belanRetrieved);
            return belanRetrieved;
        }else {
            log.error("Belan not exists with Name : {}",belanName);
            throw new BelanNotFoundException("Belan not found!");
        }
    }

    public List<Belan> getAllBelans(){
        log.info("BelanService.getAllBelans() method is called...");
        return belanRepository.findAll();
    }

    public void removeBelanById(Integer belanId){
        log.info("BelanService.removeBelanById() method is called...");
        if (belanId == null) {
            log.error("Belan id is null");
            throw new IllegalArgumentException("Belan id cannot be null");
        }
        if(belanRepository.findById(belanId).isPresent()){
            log.info("belan found with id : {}",belanId);
            belanRepository.deleteById(belanId);
            log.info("Belan deleted successfully with id : {}",belanId);
        }else{
            log.error("Belan with id not found : {}",belanId);
            throw new NoSuchElementException("belan not found!");
        }
    }
}
