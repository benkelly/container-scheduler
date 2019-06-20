package ie.benjamin.container.scheduler.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ie.benjamin.container.scheduler.domain.Container} entity.
 */
public class ContainerDTO implements Serializable {


    private Long id;

    @NotNull
    private String containerId;

    @NotNull
    private String name;

    private String image;

    private String status;

    private String flavour;

    private String os;

    private String cpu;


    private Long nodeId;

    private String nodeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContainerDTO containerDTO = (ContainerDTO) o;
        if (containerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), containerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContainerDTO{" +
            "id=" + getId() +
            ", containerId='" + getContainerId() + "'" +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            ", status='" + getStatus() + "'" +
            ", flavour='" + getFlavour() + "'" +
            ", os='" + getOs() + "'" +
            ", cpu='" + getCpu() + "'" +
            ", node=" + getNodeId() +
            ", node='" + getNodeName() + "'" +
            "}";
    }
}
