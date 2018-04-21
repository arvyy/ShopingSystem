package lt.mif.ise.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.GUIDGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {

    private String id;

    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}