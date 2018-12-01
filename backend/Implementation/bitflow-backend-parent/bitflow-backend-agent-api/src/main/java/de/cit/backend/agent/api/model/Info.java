/*
 * bitflow-process-agent: REST-API
 * Process-Agent-REST-API
 *
 * OpenAPI spec version: 1.0.0
 * Contact: sven.carlin@campus.tu-berlin.de
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package de.cit.backend.agent.api.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import de.cit.backend.agent.api.model.Tag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Info
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-01-22T23:30:39.496+01:00")
public class Info {
  @SerializedName("Hostname")
  private String hostname = null;

  @SerializedName("Tags")
  private Tag tags = null;

  @SerializedName("NumCores")
  private Integer numCores = null;

  @SerializedName("TotalMem")
  private Long totalMem = null;

  @SerializedName("UsedCpuCores")
  private List<Double> usedCpuCores = null;

  @SerializedName("UsedCpu")
  private Double usedCpu = null;

  @SerializedName("UsedMem")
  private Long usedMem = null;

  @SerializedName("NumProcs")
  private Integer numProcs = null;

  @SerializedName("Goroutines")
  private Integer goroutines = null;

  public Info hostname(String hostname) {
    this.hostname = hostname;
    return this;
  }

   /**
   * Get hostname
   * @return hostname
  **/
  @ApiModelProperty(example = "worker12", value = "")
  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public Info tags(Tag tags) {
    this.tags = tags;
    return this;
  }

   /**
   * Get tags
   * @return tags
  **/
  @ApiModelProperty(value = "")
  public Tag getTags() {
    return tags;
  }

  public void setTags(Tag tags) {
    this.tags = tags;
  }

  public Info numCores(Integer numCores) {
    this.numCores = numCores;
    return this;
  }

   /**
   * Get numCores
   * @return numCores
  **/
  @ApiModelProperty(example = "6", value = "")
  public Integer getNumCores() {
    return numCores;
  }

  public void setNumCores(Integer numCores) {
    this.numCores = numCores;
  }

  public Info totalMem(Long totalMem) {
    this.totalMem = totalMem;
    return this;
  }

   /**
   * Get totalMem
   * @return totalMem
  **/
  @ApiModelProperty(example = "456464", value = "")
  public Long getTotalMem() {
    return totalMem;
  }

  public void setTotalMem(Long totalMem) {
    this.totalMem = totalMem;
  }

  public Info usedCpuCores(List<Double> usedCpuCores) {
    this.usedCpuCores = usedCpuCores;
    return this;
  }

  public Info addUsedCpuCoresItem(Double usedCpuCoresItem) {
    if (this.usedCpuCores == null) {
      this.usedCpuCores = new ArrayList<Double>();
    }
    this.usedCpuCores.add(usedCpuCoresItem);
    return this;
  }

   /**
   * Get usedCpuCores
   * @return usedCpuCores
  **/
  @ApiModelProperty(example = "[2.3,4.6,3.4567]", value = "")
  public List<Double> getUsedCpuCores() {
    return usedCpuCores;
  }

  public void setUsedCpuCores(List<Double> usedCpuCores) {
    this.usedCpuCores = usedCpuCores;
  }

  public Info usedCpu(Double usedCpu) {
    this.usedCpu = usedCpu;
    return this;
  }

   /**
   * Get usedCpu
   * @return usedCpu
  **/
  @ApiModelProperty(example = "2.3", value = "")
  public Double getUsedCpu() {
    return usedCpu;
  }

  public void setUsedCpu(Double usedCpu) {
    this.usedCpu = usedCpu;
  }

  public Info usedMem(Long usedMem) {
    this.usedMem = usedMem;
    return this;
  }

   /**
   * Get usedMem
   * @return usedMem
  **/
  @ApiModelProperty(example = "65243", value = "")
  public Long getUsedMem() {
    return usedMem;
  }

  public void setUsedMem(Long usedMem) {
    this.usedMem = usedMem;
  }

  public Info numProcs(Integer numProcs) {
    this.numProcs = numProcs;
    return this;
  }

   /**
   * Get numProcs
   * @return numProcs
  **/
  @ApiModelProperty(example = "247", value = "")
  public Integer getNumProcs() {
    return numProcs;
  }

  public void setNumProcs(Integer numProcs) {
    this.numProcs = numProcs;
  }

  public Info goroutines(Integer goroutines) {
    this.goroutines = goroutines;
    return this;
  }

   /**
   * Get goroutines
   * @return goroutines
  **/
  @ApiModelProperty(example = "6", value = "")
  public Integer getGoroutines() {
    return goroutines;
  }

  public void setGoroutines(Integer goroutines) {
    this.goroutines = goroutines;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Info info = (Info) o;
    return Objects.equals(this.hostname, info.hostname) &&
        Objects.equals(this.tags, info.tags) &&
        Objects.equals(this.numCores, info.numCores) &&
        Objects.equals(this.totalMem, info.totalMem) &&
        Objects.equals(this.usedCpuCores, info.usedCpuCores) &&
        Objects.equals(this.usedCpu, info.usedCpu) &&
        Objects.equals(this.usedMem, info.usedMem) &&
        Objects.equals(this.numProcs, info.numProcs) &&
        Objects.equals(this.goroutines, info.goroutines);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hostname, tags, numCores, totalMem, usedCpuCores, usedCpu, usedMem, numProcs, goroutines);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Info {\n");
    
    sb.append("    hostname: ").append(toIndentedString(hostname)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    numCores: ").append(toIndentedString(numCores)).append("\n");
    sb.append("    totalMem: ").append(toIndentedString(totalMem)).append("\n");
    sb.append("    usedCpuCores: ").append(toIndentedString(usedCpuCores)).append("\n");
    sb.append("    usedCpu: ").append(toIndentedString(usedCpu)).append("\n");
    sb.append("    usedMem: ").append(toIndentedString(usedMem)).append("\n");
    sb.append("    numProcs: ").append(toIndentedString(numProcs)).append("\n");
    sb.append("    goroutines: ").append(toIndentedString(goroutines)).append("\n");
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
