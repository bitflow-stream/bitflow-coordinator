package de.cit.backend.mgmt.services.interfaces;

import java.util.List;

import javax.ejb.Local;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.persistence.model.AgentDTO;
import de.cit.backend.mgmt.persistence.model.CapabilityDTO;

@Local
public interface IInfoService {

	List<AgentDTO> loadInfos();
	List<CapabilityDTO> loadAgentCapabilities(int agentId) throws BitflowException;
	void registerAgent(String ip, int port) throws BitflowException;
}
