package de.cit.backend.api.factories;

import de.cit.backend.api.RegisterApiService;
import de.cit.backend.api.impl.RegisterApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2018-01-18T15:01:42.432+01:00")
public class RegisterApiServiceFactory {

   private final static RegisterApiService service = new RegisterApiServiceImpl();

   public static RegisterApiService getRegisterApi()
   {
      return service;
   }
}
