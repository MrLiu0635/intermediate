package com.inspur.edp.lcm.zhaoleitr.api.service;

import com.inspur.edp.lcm.zhaoleitr.api.entity.Material;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Material4WebApiService {
	@GET
	@Path(value = "/{id}")
	Material getMaterial(@PathParam(value="id")String id);
}
