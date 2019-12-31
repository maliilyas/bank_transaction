package com.transaction.controller;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for the transaction handling
 */
@Path("/transaction")
@Produces({"application/json"})
@Slf4j
public class TransactionController  {

  @POST
  public void doNothing(){

  }



}
