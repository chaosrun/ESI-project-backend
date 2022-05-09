package esi.project.ils.materials;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import esi.project.ils.extension_requests.ExtensionRequest;
import esi.project.ils.loan_requests.LoanRequest;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "materials")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id")
    private int id;

    @NotBlank
    @NotNull
    @Column(name = "home_library")
    private String homeLibrary;

    @NotBlank
    @NotNull
    @Column(name = "title")
    private String title;

    @NotBlank
    @NotNull
    @Column(name = "author")
    private String author;

    @NotBlank
    @NotNull
    @Column(name = "call_number", unique = true)
    private String callNumber;

    @NotNull
    @Column(name = "published_at")
    private Date publishedAt;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToMany(mappedBy = "material", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<LoanRequest> loanRequests;

    @OneToMany(mappedBy = "material", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ExtensionRequest> extensionRequests;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHomeLibrary() {
        return homeLibrary;
    }

    public void setHomeLibrary(String homeLibrary) {
        this.homeLibrary = homeLibrary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<LoanRequest> getLoanRequests() {
        return loanRequests;
    }

    public void setLoanRequests(Set<LoanRequest> loanRequests) {
        this.loanRequests = loanRequests;
    }

    public Set<ExtensionRequest> getExtensionRequests() {
        return extensionRequests;
    }

    public void setExtensionRequests(Set<ExtensionRequest> extensionRequests) {
        this.extensionRequests = extensionRequests;
    }

}
