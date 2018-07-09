package be.company.fca.service.impl;

import be.company.fca.model.Championnat;
import be.company.fca.model.Division;
import be.company.fca.model.Rencontre;
import be.company.fca.repository.DivisionRepository;
import be.company.fca.repository.MatchRepository;
import be.company.fca.repository.RencontreRepository;
import be.company.fca.repository.SetRepository;
import be.company.fca.service.RencontreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RencontreServiceImpl implements RencontreService{

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private RencontreRepository rencontreRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private SetRepository setRepository;

    @Override
    @Transactional(readOnly = false)
    public List<Rencontre> saveRencontres(List<Rencontre> rencontreList) {

        List<Rencontre> savedRencontres = new ArrayList<>();

        for (Rencontre rencontre : rencontreList){
            savedRencontres.add(rencontreRepository.save(rencontre));
        }

        return savedRencontres;

    }

    @Override
    @Transactional(readOnly = false)
    public void deleteByChampionnat(Long championnatId) {
        setRepository.deleteByChampionnatId(championnatId);
        matchRepository.deleteByChampionnatId(championnatId);
        rencontreRepository.deleteByChampionnatId(championnatId);
    }
}
