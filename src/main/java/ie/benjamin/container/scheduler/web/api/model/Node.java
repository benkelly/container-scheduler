package ie.benjamin.container.scheduler.web.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import ie.benjamin.container.scheduler.web.api.model.NodeCapacity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Node
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-06-09T15:41:36.809+01:00[Europe/Dublin]")

public class Node   {
  @JsonProperty("id")
  private UUID id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("capacity")
  private NodeCapacity capacity = null;

  @JsonProperty("scheduler-hints")
  @Valid
  private Map<String, String> schedulerHints = new HashMap<>();

  public Node id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(example = "d290f1ee-6c54-4b01-90e6-d701748f0851", required = true, value = "")
  @NotNull

  @Valid

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Node name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  @ApiModelProperty(example = "my-super-node", required = true, value = "")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Node capacity(NodeCapacity capacity) {
    this.capacity = capacity;
    return this;
  }

  /**
   * Get capacity
   * @return capacity
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public NodeCapacity getCapacity() {
    return capacity;
  }

  public void setCapacity(NodeCapacity capacity) {
    this.capacity = capacity;
  }

  public Node schedulerHints(Map<String, String> schedulerHints) {
    this.schedulerHints = schedulerHints;
    return this;
  }

  public Node putSchedulerHintsItem(String key, String schedulerHintsItem) {
    this.schedulerHints.put(key, schedulerHintsItem);
    return this;
  }

  /**
   * Get schedulerHints
   * @return schedulerHints
  */
  @ApiModelProperty(example = "{\"flavour\":\"silver\",\"os\":\"Windows\",\"CPU\":\"Intel\"}", required = true, value = "")
  @NotNull


  public Map<String, String> getSchedulerHints() {
    return schedulerHints;
  }

  public void setSchedulerHints(Map<String, String> schedulerHints) {
    this.schedulerHints = schedulerHints;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Node node = (Node) o;
    return Objects.equals(this.id, node.id) &&
        Objects.equals(this.name, node.name) &&
        Objects.equals(this.capacity, node.capacity) &&
        Objects.equals(this.schedulerHints, node.schedulerHints);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, capacity, schedulerHints);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Node {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    capacity: ").append(toIndentedString(capacity)).append("\n");
    sb.append("    schedulerHints: ").append(toIndentedString(schedulerHints)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

