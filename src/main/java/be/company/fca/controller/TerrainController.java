package be.company.fca.controller;

import be.company.fca.model.Club;
import be.company.fca.model.Terrain;
import be.company.fca.repository.ClubRepository;
import be.company.fca.repository.EquipeRepository;
import be.company.fca.repository.RencontreRepository;
import be.company.fca.repository.TerrainRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
@Api(description = "API REST pour la gestion des terrains")
public class TerrainController {

    @Autowired
    private TerrainRepository terrainRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private RencontreRepository rencontreRepository;

    @RequestMapping(path="/public/terrains", method= RequestMethod.GET)
    Iterable<Terrain> getAllTerrains() {
        return terrainRepository.findAll();
    }

    @RequestMapping(path="/public/terrain", method= RequestMethod.GET)
    Terrain getTerrain(@RequestParam Long id) {
        return terrainRepository.findOne(id);
    }

    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @RequestMapping(value = "/private/terrain", method = RequestMethod.PUT)
    public Terrain updateTerrain(@RequestBody Terrain terrain){
        return terrainRepository.save(terrain);
    }

    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @RequestMapping(value = "/private/terrain", method = RequestMethod.POST)
    public Terrain addTerrain(@RequestBody Terrain terrain){
        return terrainRepository.save(terrain);
    }

    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @RequestMapping(path="/private/terrain/{terrainId}/deletable", method= RequestMethod.GET)
    public boolean isDeletable(@PathVariable("terrainId") Long terrainId){
        Terrain terrain = new Terrain();
        terrain.setId(terrainId);

        // Faire des counts pour savoir si le terrain n'a pas de reference
        // --> pas de club, pas d'equipe, pas de rencontre

        long count = clubRepository.countByTerrain(terrain);
        if (count==0){
            count = equipeRepository.countByTerrain(terrain);
            if (count==0){
                count = rencontreRepository.countByTerrain(terrain);
                return count==0;
            }
        }
        return false;
    }

    @PreAuthorize("hasAuthority('ADMIN_USER')")
    @RequestMapping(value = "/private/terrain", method = RequestMethod.DELETE)
    public void deleteTerrain(@RequestParam Long terrainId){
        if (isDeletable(terrainId)){
            terrainRepository.delete(terrainId);
        }
    }

}
