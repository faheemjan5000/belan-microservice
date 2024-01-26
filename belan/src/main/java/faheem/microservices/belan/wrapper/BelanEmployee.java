package faheem.microservices.belan.wrapper;

import faheem.microservices.belan.entity.Belan;
import faheem.microservices.belan.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor@NoArgsConstructor
public class BelanEmployee {
    private Belan belan;
    private Employee employee;
}
