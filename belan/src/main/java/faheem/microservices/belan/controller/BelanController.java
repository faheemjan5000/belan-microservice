package faheem.microservices.belan.controller;

import faheem.microservices.belan.entity.Belan;
import faheem.microservices.belan.exceptions.BelanNotAddedException;
import faheem.microservices.belan.exceptions.BelanNotFoundException;
import faheem.microservices.belan.service.BelanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/belan")
public class BelanController {

    @Autowired
    private BelanService belanService;

    @PostMapping("/add")
    public ResponseEntity<Object> addBelan(@RequestBody Belan belan){
        log.info("BelanController.addBelan() method is called...");
    
        // Detailed validation of the Belan object
        if (belan == null) {
            log.warn("Attempt to add null Belan object");
            return ResponseEntity.badRequest().body("Belan object cannot be null");
        }
        if (invalidString(belan.getBelanName())) {
            log.warn("Attempt to add Belan with invalid name");
            return ResponseEntity.badRequest().body("Belan name is required");
        }
        if (invalidString(belan.getBelanColor())) {
            log.warn("Attempt to add Belan with invalid color");
            return ResponseEntity.badRequest().body("Belan color is required");
        }
    
        try {
           Belan belanAdded = belanService.addBelan(belan);
            log.info("Belan added successfully: {}", belanAdded);
            return new ResponseEntity<>(belanAdded, HttpStatus.CREATED);
        } catch (BelanNotAddedException e) {
            log.error("Error while adding Belan: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getBelanById(@PathVariable("id") Integer belanId){
        log.info("BelanController.getBelanById() method is called...");
    try {
        Belan belan = belanService.getBelanById(belanId);
        return ResponseEntity.ok(belan);
    }catch (NoSuchElementException e){
           log.error("No belan found : {}",e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<Object> getBelanByColor(@PathVariable("color") String belanColor){
        log.info("BelanController.getBelanByColor() method is called...");
        if(invalidString(belanColor)){
            log.warn("belanColor is invalid");
            return ResponseEntity.badRequest().body("belanColor is required!");
        }
        
        try {
           Belan belanByColorRetrieved = belanService.getBelanByColor(belanColor);
           return ResponseEntity.ok(belanByColorRetrieved);
        }catch(BelanNotFoundException e){
            log.error("Error while retrieving Belan: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
          
        }
    }
   
    @GetMapping("/name/{name}")
    public ResponseEntity<Object> getBelanByName(@PathVariable("name") String belanName){
        log.info("BelanController.getBelanByName() method is called...");
        if(invalidString(belanName)){
            log.warn("belanName is invalid");
            return ResponseEntity.badRequest().body("belanName is required!");
        }
        try{ 
            Belan belanByNameRetrieved = belanService.getBelanByName(belanName);
            return ResponseEntity.ok(belanByNameRetrieved);
        }catch(BelanNotFoundException e){
         log.error("error while retrieving Belan: {}",e.getMessage(),e);
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllBelans(){
        log.info("BelanController.getAllBelans() method is called...");
        try {
            List<Belan> allBelans = belanService.getAllBelans();
            if (allBelans.isEmpty()) {
                log.info("No Belan records found");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Belan records found");
            }
            return ResponseEntity.ok(allBelans);
        } catch (Exception e) {
            log.error("Error while retrieving all Belans: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving Belan records");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeBelan(@PathVariable("id") Integer belanId){
        log.info("BelanController.removeBelan() method is called...");
        if(belanId==null){
            log.warn("belanId is null!");
            return ResponseEntity.badRequest().body("belanId cannot be null!");
        }
        try{
        belanService.removeBelanById(belanId);
        return ResponseEntity.ok().body("belan with id "+belanId+" deleted successfully!");
        }catch(BelanNotFoundException e){
          log.error("Error while deleting belan: {}",e.getMessage(),e);
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(Exception e){
            log.error("Error while deleting belan: {}",e.getMessage(),e);
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occured while deleting belan");
        }
    }

    private boolean invalidString(String input){
        return input==null || input.trim().isEmpty();
        
     }

}
