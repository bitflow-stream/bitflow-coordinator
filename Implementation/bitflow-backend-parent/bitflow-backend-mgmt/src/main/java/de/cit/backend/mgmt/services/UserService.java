package de.cit.backend.mgmt.services;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import de.cit.backend.mgmt.persistence.PersistenceService;
import de.cit.backend.mgmt.persistence.model.UserDTO;
import de.cit.backend.mgmt.services.interfaces.IUserService;

@Stateless
@Local(IUserService.class)
public class UserService implements IUserService {

	@EJB
	private PersistenceService persistence;
	
	@PostConstruct
	public void init(){
		System.out.println("PostConstruct - UserService");
	}
	
	@Override
	public UserDTO loadUser(int userId) {
		return persistence.findUser(userId);
	}
	//TODO
	

}
