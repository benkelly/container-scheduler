package ie.benjamin.container.scheduler.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Container.
 */
@Entity
@Table(name = "container")
public class Container implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "status")
    private String status;

    @Column(name = "flavour")
    private String flavour;

    @Column(name = "os")
    private String os;

    @Column(name = "cpu")
    private String cpu;

    @ManyToOne
    @JsonIgnoreProperties("containers")
    private Node node;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Container name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public Container image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public Container status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlavour() {
        return flavour;
    }

    public Container flavour(String flavour) {
        this.flavour = flavour;
        return this;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public String getOs() {
        return os;
    }

    public Container os(String os) {
        this.os = os;
        return this;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getCpu() {
        return cpu;
    }

    public Container cpu(String cpu) {
        this.cpu = cpu;
        return this;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public Node getNode() {
        return node;
    }

    public Container node(Node node) {
        this.node = node;
        return this;
    }

    public void setNode(Node node) {
        this.node = node;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Container)) {
            return false;
        }
        return id != null && id.equals(((Container) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Container{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            ", status='" + getStatus() + "'" +
            ", flavour='" + getFlavour() + "'" +
            ", os='" + getOs() + "'" +
            ", cpu='" + getCpu() + "'" +
            "}";
    }
}
