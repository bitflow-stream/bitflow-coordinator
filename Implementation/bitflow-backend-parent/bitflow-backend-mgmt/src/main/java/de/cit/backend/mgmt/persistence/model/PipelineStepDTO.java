package de.cit.backend.mgmt.persistence.model;
// Generated 10.12.2017 16:49:45 by Hibernate Tools 5.2.3.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * PipelineStep generated by hbm2java
 */
@Entity
@Table(name = "PIPELINE_STEP", catalog = "citBitDB")
public class PipelineStepDTO extends BaseIdEntity implements java.io.Serializable {

	private Integer id;
	private AgentDTO agent;
	private int stepNumber;
	private String status;
	private String content;
	private StepTypeEnum type;
	private List<PipelineParameterDTO> params = new ArrayList<>();
	private List<PipelineStepDTO> successors = new ArrayList<>();
	
	private List<Integer> successorsFlat = new ArrayList<>();
	/**
	 * This does not reference any actual agent ID. PipelineSteps with the same agentAdvise will be run on the
	 * same agent. The assignment to an actual agent will be made else where.
	 */
	private int agentAdvice = 0;
	
	public PipelineStepDTO() {
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENT_ID")
	public AgentDTO getAgent() {
		return this.agent;
	}

	public void setAgent(AgentDTO agent) {
		this.agent = agent;
	}

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "PIPELINE_ID", nullable = false)
//	public PipelineDTO getPipeline() {
//		return this.pipeline;
//	}

//	public void setPipeline(PipelineDTO pipeline) {
//		this.pipeline = pipeline;
//	}

	@Column(name = "STEP_NUMBER", nullable = false)
	public int getStepNumber() {
		return this.stepNumber;
	}

	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "CONTENT", nullable = false, length = 256)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "STEP_TYPE", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	public StepTypeEnum getType() {
		return type;
	}

	public void setType(StepTypeEnum type) {
		this.type = type;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER, orphanRemoval=true)
    @JoinColumn(name="PIPELINE_STEP_ID")
	public List<PipelineParameterDTO> getParams() {
		return params;
	}

	public void setParams(List<PipelineParameterDTO> params) {
		this.params = params;
	}

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="PIPELINE_STEP_SUCCESSORS",
    joinColumns=
        @JoinColumn(name="STEP_ID", referencedColumnName="ID"),
    inverseJoinColumns=
        @JoinColumn(name="SUCCESSOR_ID", referencedColumnName="ID")
    )
	public List<PipelineStepDTO> getSuccessors() {
		return successors;
	}

	public void setSuccessors(List<PipelineStepDTO> successors) {
		this.successors = successors;
	}

	@Transient
	public List<Integer> getSuccessorsFlat() {
		return successorsFlat;
	}

	@Transient
	public List<Integer> getSuccessorsFlatUpdated() {
		List<Integer> successorsSimple = new ArrayList<>();
		for(PipelineStepDTO succ : successors){
			successorsSimple.add(succ.getStepNumber());
		}
		this.successorsFlat = successorsSimple;
		return successorsFlat;
	}
	
	public void setSuccessorsFlat(List<Integer> successorsFlat) {
		this.successorsFlat = successorsFlat;
	}
	
	@Transient
	public int getAgentAdvice() {
		return agentAdvice;
	}

	public void setAgentAdvice(int agentAdvice) {
		this.agentAdvice = agentAdvice;
	}

	@Override
	public String toString() {
		return "Step " + stepNumber;
	}
}
