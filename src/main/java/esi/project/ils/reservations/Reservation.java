package esi.project.ils.reservations;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import esi.project.ils.materials.Material;
import esi.project.ils.users.User;

@Entity
@Table(name="reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reservation_id")
    private int id;

    @NotNull
    @NotBlank
    @Column(name = "start_date")
    private String start_date;

    @NotNull
    @NotBlank
    @Column(name = "end_date")
    private String end_date;

    @NotNull
    @NotBlank
    @Column(name = "status")
    private String status;

   
    @ManyToOne
    private User user;

    

    @ManyToOne
    private Material material;


    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Reservation() {
        
    }

    public Reservation(int id, @NotNull @NotBlank String start_date, @NotNull @NotBlank String end_date,String status, Integer user_id,Integer material_id) {
        this.id = id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.user = new User(user_id,"","","","");
        this.material = new Material(material_id,"","","","","");

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart_date() {
        return this.start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    

    public String getEnd_date() {
        return this.end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}
