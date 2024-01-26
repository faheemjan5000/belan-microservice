package faheem.microservices.belan.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BELAN")
public class Belan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BELAN_ID")
    private Integer belanId;
    @Column(name = "BELAN_NAME")
    private String belanName;
    @Column(name = "BELAN_COLOUR")
    private String belanColor;

}
