package cdw.cdwproject.model.User;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.*;

public class User2 {
    @Size(min = 2,max= 10)
    @NotNull
    private String name;
    @NotNull
    @Min(18)
    private int age;
    @NotNull
    @Email
    private String email;
    public User2(){

    }
    public User2(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
