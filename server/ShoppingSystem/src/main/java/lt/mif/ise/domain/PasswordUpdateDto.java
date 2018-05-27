package lt.mif.ise.domain;

public class PasswordUpdateDto {
    String oldPassword;
    String password;
    String confirmPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getOldPassword() { return oldPassword; }

    public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }
}

