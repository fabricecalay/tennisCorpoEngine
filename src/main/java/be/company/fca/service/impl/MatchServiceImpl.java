package be.company.fca.service.impl;

import be.company.fca.model.Match;
import be.company.fca.model.Rencontre;
import be.company.fca.model.Set;
import be.company.fca.model.TypeMatch;
import be.company.fca.repository.MatchRepository;
import be.company.fca.repository.SetRepository;
import be.company.fca.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private SetRepository setRepository;

    @Override
    @Transactional(readOnly = false)
    public Match updateMatchAndSets(Long matchId, List<Set> sets) {

        Match match = matchRepository.findOne(matchId);

        if (match!=null){

            setRepository.deleteByMatch(match);

            for (Set set : sets){
                set.setMatch(match);
                setRepository.save(set);
            }

            calculPoints(match, sets);

            match = matchRepository.save(match);
        }

        return match;
    }

    /**
     * Methode permettant de calculer les points d'un match sur base des sets joues
     * @param match Match
     * @param sets Sets joues
     */
    private void calculPoints(Match match, List<Set> sets){

        match.setPointsVisites(null);
        match.setPointsVisiteurs(null);

        if (sets!=null && !sets.isEmpty()){
            match.setPointsVisites(0);
            match.setPointsVisiteurs(0);

            for (Set set : sets){
                if (set.getJeuxVisites()!=null && set.getJeuxVisiteurs()!=null) {
                    if (set.getJeuxVisites() > set.getJeuxVisiteurs()) {
                        match.setPointsVisites(match.getPointsVisites()+1);
                    } else if (set.getJeuxVisites() < set.getJeuxVisiteurs()) {
                        match.setPointsVisiteurs(match.getPointsVisiteurs()+1);
                    } else {
                        if (Boolean.TRUE.equals(set.getVisitesGagnant())) {
                            match.setPointsVisites(match.getPointsVisites()+1);
                        } else if (Boolean.FALSE.equals(set.getVisitesGagnant())) {
                            match.setPointsVisiteurs(match.getPointsVisiteurs()+1);
                        }
                    }
                }
            }

        }
    }

    @Override
    @Transactional(readOnly = false)
    public List<Match> createMatchs(Rencontre rencontre) {

        List<Match> matchs = new ArrayList<Match>();

        // 4 matchs simples

        for (int i=0;i<4;i++){
            Match match = new Match();
            match.setRencontre(rencontre);
            match.setType(TypeMatch.SIMPLE);
            match.setOrdre(i+1);

            match = matchRepository.save(match);

            matchs.add(match);
        }

        // 2 matchs doubles

        for (int i=0;i<2;i++){
            Match match = new Match();
            match.setRencontre(rencontre);
            match.setType(TypeMatch.DOUBLE);
            match.setOrdre(i+1);

            match = matchRepository.save(match);

            matchs.add(match);
        }

        return matchs;

    }
}