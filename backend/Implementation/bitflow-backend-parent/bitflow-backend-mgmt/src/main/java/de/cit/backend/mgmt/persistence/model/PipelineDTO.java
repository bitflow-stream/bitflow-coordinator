package de.cit.backend.mgmt.persistence.model;
// Generated 10.12.2017 16:49:45 by Hibernate Tools 5.2.3.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import de.cit.backend.mgmt.persistence.model.enums.StepTypeEnum;

/**
 * Pipeline generated by hbm2java
 */
@Entity
@Table(name = "PIPELINE", catalog = "citBitDB")
public class PipelineDTO extends BaseIdEntity implements java.io.Serializable {

	private Integer id;
	private String name;
	private String status;
	private Date lastChanged;
	private List<ProjectDTO> projects = new ArrayList<>();
	private List<PipelineStepDTO> pipelineSteps = new ArrayList<>();
	private List<PipelineHistoryDTO> pipelineHistory = new ArrayList<>();

	public PipelineDTO() {
	}

	public PipelineDTO(String status, Date lastChanged, List<PipelineStepDTO> pipelineSteps) {
		this.status = status;
		this.lastChanged = lastChanged;
		this.pipelineSteps = pipelineSteps;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "STATUS", length = 32)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_CHANGED", length = 19)
	public Date getLastChanged() {
		return this.lastChanged;
	}

	public void setLastChanged(Date lastChanged) {
		this.lastChanged = lastChanged;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="PIPELINE_ID", referencedColumnName="ID")
	public List<PipelineStepDTO> getPipelineSteps() {
		return this.pipelineSteps;
	}

	public void setPipelineSteps(List<PipelineStepDTO> pipelineSteps) {
		this.pipelineSteps = pipelineSteps;
	}

	@ManyToMany(mappedBy="pipelines")
	public List<ProjectDTO> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectDTO> projects) {
		this.projects = projects;
	}

	@Column(name = "NAME") 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(mappedBy="pipeline", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true )
	@OrderBy("startedAt DESC")
	public List<PipelineHistoryDTO> getPipelineHistory() {
		return pipelineHistory;
	}

	public void setPipelineHistory(List<PipelineHistoryDTO> pipelineHistory) {
		this.pipelineHistory = pipelineHistory;
	}

	@Transient
	public boolean isSequence(){
		for(PipelineStepDTO step : this.pipelineSteps){
			if(step.getSuccessors().size() > 1){
				return false;
			}
		}
		return true;
	}
	
	@Transient
	public int countForks(){
		int count = 0;
		for(PipelineStepDTO step : this.pipelineSteps){
			if(step.getSuccessors().size() > 1){
				count++;
			}
		}
		return count;
	}
	
	@Transient
	public int countSinks(){
		int count = 0;
		for(PipelineStepDTO step : this.pipelineSteps){
			if(step.getType() == StepTypeEnum.SINK){
				count++;
			}
		}
		return count;
	}
	
	@Transient
	public int countAgents(){
		List<Integer> agents = new ArrayList<>();
		for(PipelineStepDTO step : this.pipelineSteps){
			if(!agents.contains(step.getAgentAdvice())){
				agents.add(step.getAgentAdvice());
			}
		}
		return agents.size();
	}
}
