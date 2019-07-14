package com.paazl.rest;

import com.paazl.service.SheepOrderDto;
import com.paazl.service.SheepService;
import com.paazl.service.SheepStatusesDto;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/sheeps")
@Service
public class SheepResource {
    private final SheepService sheepService;

    public SheepResource(SheepService sheepService) {
        this.sheepService = sheepService;
    }

    @GET
    @Path("/statistics")
    public SheepStatusesDto getSheepStatistics() {
        return sheepService.getSheepStatuses();
    }

    @POST
    @Path("/order")
    public Response orderSheeps(Integer numberOfSheeps) {
        SheepOrderDto sheepOrderDto = new SheepOrderDto(sheepService.orderSheeps(numberOfSheeps));
        return Response.status(Response.Status.CREATED).entity(sheepOrderDto).build();
    }
}
