package ie.benjamin.container.scheduler.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Node.
 */
@Entity
@Table(name = "node")
public class Node implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "total_capacity")
    private Integer totalCapacity;

    @Column(name = "used_capacity")
    private Integer usedCapacity;

    @Column(name = "flavour")
    private String flavour;

    @Column(name = "os")
    private String os;

    @Column(name = "cpu")
    private String cpu;

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

    public Node name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalCapacity() {
        return totalCapacity;
    }

    public Node totalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
        return this;
    }

    public void setTotalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public Integer getUsedCapacity() {
        return usedCapacity;
    }

    public Node usedCapacity(Integer usedCapacity) {
        this.usedCapacity = usedCapacity;
        return this;
    }

    public void setUsedCapacity(Integer usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    public String getFlavour() {
        return flavour;
    }

    public Node flavour(String flavour) {
        this.flavour = flavour;
        return this;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public String getOs() {
        return os;
    }

    public Node os(String os) {
        this.os = os;
        return this;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getCpu() {
        return cpu;
    }

    public Node cpu(String cpu) {
        this.cpu = cpu;
        return this;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Node)) {
            return false;
        }
        return id != null && id.equals(((Node) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Node{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", totalCapacity=" + getTotalCapacity() +
            ", usedCapacity=" + getUsedCapacity() +
            ", flavour='" + getFlavour() + "'" +
            ", os='" + getOs() + "'" +
            ", cpu='" + getCpu() + "'" +
            "}";
    }
}
