package org.example.jakartaeelivestream2023.anime;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/animes")
public class AnimeResource {

    @Inject
    private AnimeRepository animeRepository;

    @GET
    public List<Anime> list() {
        return this.animeRepository.findAll();
    }
}