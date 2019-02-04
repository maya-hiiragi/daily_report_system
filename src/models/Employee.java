package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
        @NamedQuery(name = "getAllEmployees", query = "select e from Employee as e order by e.id desc "),
        @NamedQuery(name = "getEmployeesCount", query = "select count(e) from Employee as e "),
        @NamedQuery(name = "checkRegisteredCode", query = "select count(e) from Employee as e where e.code = :code "),
        @NamedQuery(name = "checkLoginCodeAndPassword", query = "select e from Employee as e where e.delete_flg = 0 and e.code = :code and e.password = :pass")
})
@Table(name = "employees")
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", length = 64, nullable = false)
    private String password;

    @Column(name = "admin_flg", nullable = false)
    private int admin_flg;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "delete_flg", nullable = false)
    private int delete_flg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAdmin_flg() {
        return admin_flg;
    }

    public void setAdmin_flg(int admin_flg) {
        this.admin_flg = admin_flg;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public int getDelete_flg() {
        return delete_flg;
    }

    public void setDelete_flg(int delete_flg) {
        this.delete_flg = delete_flg;
    }
}