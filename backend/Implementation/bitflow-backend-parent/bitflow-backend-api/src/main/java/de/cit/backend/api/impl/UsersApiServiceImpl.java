package de.cit.backend.api.impl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import de.cit.backend.api.NotFoundException;
import de.cit.backend.api.UsersApiService;
import de.cit.backend.api.converter.UserConverter;
import de.cit.backend.mgmt.services.interfaces.IUserService;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-18T15:01:42.432+01:00")
public class UsersApiServiceImpl extends UsersApiService {

	protected IUserService userService;
	
	public UsersApiServiceImpl() {
		Context ctx;
		try {
			ctx = new InitialContext();
			userService = (IUserService) ctx.lookup("java:module/UserService");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
      public Response usersGet(SecurityContext securityContext)
      throws NotFoundException {
	  return Response.ok().entity(new UserConverter().convertToFrontend(userService.loadUsers())).build();
  }
}
