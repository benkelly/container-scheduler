package ie.benjamin.container.scheduler.web.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Container
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-06-09T15:41:36.809+01:00[Europe/Dublin]")

public class Container   {
  @JsonProperty("id")
  private UUID id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("image")
  private String image;

  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    PENDING("pending"),
    
    SCHEDULED("scheduled"),
    
    RUNNING("running");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + text + "'");
    }
  }

  @JsonProperty("status")
  private StatusEnum status;

  @JsonProperty("scheduler-hints")
  @Valid
  private Map<String, String> schedulerHints = new HashMap<>();

  public Container id(UUID id) {
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

  public Container name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  @ApiModelProperty(example = "my-super-container", required = true, value = "")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Container image(String image) {
    this.image = image;
    return this;
  }

  /**
   * The name of the image to use when creating the container
   * @return image
  */
  @ApiModelProperty(required = true, value = "The name of the image to use when creating the container")
  @NotNull


  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Container status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  @ApiModelProperty(value = "")


  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Container schedulerHints(Map<String, String> schedulerHints) {
    this.schedulerHints = schedulerHints;
    return this;
  }

  public Container putSchedulerHintsItem(String key, String schedulerHintsItem) {
    this.schedulerHints.put(key, schedulerHintsItem);
    return this;
  }

  /**
   * Get schedulerHints
   * @return schedulerHints
  */
  @ApiModelProperty(example = "{\"flavour\":\"gold\",\"os\":\"Linux\",\"CPU\":\"AMD\"}", required = true, value = "")
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
    Container container = (Container) o;
    return Objects.equals(this.id, container.id) &&
        Objects.equals(this.name, container.name) &&
        Objects.equals(this.image, container.image) &&
        Objects.equals(this.status, container.status) &&
        Objects.equals(this.schedulerHints, container.schedulerHints);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, image, status, schedulerHints);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Container {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    image: ").append(toIndentedString(image)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

