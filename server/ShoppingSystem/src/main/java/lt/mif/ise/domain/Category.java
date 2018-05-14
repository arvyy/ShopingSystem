package lt.mif.ise.domain;

<<<<<<< HEAD

=======
>>>>>>> origin/master
import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    private Long id;

    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="category_name", unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
