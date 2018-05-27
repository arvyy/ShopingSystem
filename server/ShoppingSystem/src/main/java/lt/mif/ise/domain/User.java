package lt.mif.ise.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name="user")
public class User {

    @Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", enabled=" + enabled + ", roles="
				+ roles + "]";
	}

	private String id;

    @NotNull
    @NotEmpty
    @Email(message = "Email is not valid.")
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    private boolean enabled;
    private Set<Role> roles;

    public User(){

    }

    public User(User user){
        this.enabled = user.isEnabled();
        this.id  = user.getId();
        this.password = user.getPassword();
        this.email = user.getEmail();
    }

    @Id
    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void setEnabled(boolean en){
        this.enabled = en;
    }

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
