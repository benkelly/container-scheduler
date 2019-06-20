package ie.benjamin.container.scheduler.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ie.benjamin.container.scheduler.domain.Node} entity.
 */
public class NodeDTO implements Serializable {

    private Long id;

    @NotNull
    private String nodeId;

    @NotNull
    private String name;

    private Integer totalCapacity;

    private Integer usedCapacity;

    private String flavour;

    private String os;

    private String cpu;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public Integer getUsedCapacity() {
        return usedCapacity;
    }

    public void setUsedCapacity(Integer usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NodeDTO nodeDTO = (NodeDTO) o;
        if (nodeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nodeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NodeDTO{" +
            "id=" + getId() +
            ", nodeId='" + getNodeId() + "'" +
            ", name='" + getName() + "'" +
            ", totalCapacity=" + getTotalCapacity() +
            ", usedCapacity=" + getUsedCapacity() +
            ", flavour='" + getFlavour() + "'" +
            ", os='" + getOs() + "'" +
            ", cpu='" + getCpu() + "'" +
            "}";
    }
}
