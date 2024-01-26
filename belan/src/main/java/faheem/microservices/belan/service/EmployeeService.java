package faheem.microservices.belan.service;

import faheem.microservices.belan.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class EmployeeService {

    private static final String URL_EMP = "http://localhost:9092/emp/get/";

    @Autowired
    private RestTemplate restTemplate;

    public Employee getEmployeeById(Integer employeeId) {
        log.info("EmployeeService.getEmployeeById() method is called from Belan API");
        Employee employee = restTemplate.getForObject(URL_EMP+employeeId, Employee.class);
        if(employee!=null){
            return employee;
        }
        else {
            throw new NoSuchElementException("Employee not found");
        }
    }
}
