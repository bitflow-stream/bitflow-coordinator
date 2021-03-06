package de.cit.backend.mgmt.services.interfaces;

import java.util.List;

import javax.ejb.Local;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;

@Local
public interface IProjectService {

	ProjectDTO loadProject(int projectId) throws BitflowException;
	List<ProjectDTO> loadProjects(String username);
	void assignUserToProject(Integer projectId, Integer userId) throws BitflowException;
	void removeUserFromProject(Integer projectId, Integer userId) throws BitflowException;
	void deleteProject(Integer projectId) throws BitflowException;
	void deletePipeline(Integer projectId) throws BitflowException;
	ProjectDTO createProject(ProjectDTO project, String username) throws BitflowException;
	ProjectDTO updateProject(int projectId, ProjectDTO project) throws BitflowException;
}
