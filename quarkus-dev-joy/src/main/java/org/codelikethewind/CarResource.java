package org.codelikethewind;

import io.quarkus.panache.common.Sort;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/car")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CarResource {

    @GET
    public List<Car> getAll(){
        return Car.findAll(Sort.ascending("make")).list();

    }
}
