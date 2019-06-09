package ie.benjamin.container.scheduler.web.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NodeCapacity
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-06-09T15:41:36.809+01:00[Europe/Dublin]")

public class NodeCapacity   {
  @JsonProperty("total")
  private Integer total;

  @JsonProperty("used")
  private Integer used;

  public NodeCapacity total(Integer total) {
    this.total = total;
    return this;
  }

  /**
   * Get total
   * @return total
  */
  @ApiModelProperty(example = "10", value = "")


  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public NodeCapacity used(Integer used) {
    this.used = used;
    return this;
  }

  /**
   * Get used
   * @return used
  */
  @ApiModelProperty(example = "4", value = "")


  public Integer getUsed() {
    return used;
  }

  public void setUsed(Integer used) {
    this.used = used;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NodeCapacity nodeCapacity = (NodeCapacity) o;
    return Objects.equals(this.total, nodeCapacity.total) &&
        Objects.equals(this.used, nodeCapacity.used);
  }

  @Override
  public int hashCode() {
    return Objects.hash(total, used);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NodeCapacity {\n");
    
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    used: ").append(toIndentedString(used)).append("\n");
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

