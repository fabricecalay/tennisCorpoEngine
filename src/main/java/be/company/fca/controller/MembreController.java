package be.company.fca.controller;

import be.company.fca.model.Genre;
import be.company.fca.model.Membre;
import be.company.fca.repository.MembreRepository;
import be.company.fca.utils.POIUtils;
import io.swagger.annotations.Api;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@RestController
@RequestMapping("/api/v1")
@Api(description = "API REST pour la gestion des membres")
public class MembreController {

    @Autowired
    private MembreRepository membreRepository;

    @RequestMapping(method= RequestMethod.GET, path="/public/membre")
    public Membre getMembreByNumero(@RequestParam String numero) {
        return membreRepository.findByNumero(numero);
    }

    @RequestMapping(method= RequestMethod.GET, path="/public/membres")
    public Iterable<Membre> getMembres() {

//        //TODO : pour une liste de membre, limiter le contenu retourne (membreDto)
//
//        //TODO : si retour d'un singleton --> membre complet = ok

        return membreRepository.findAll();
    }


    @RequestMapping(value = "/public/membre", method = RequestMethod.PUT)
    public Membre updateMembre(@RequestBody Membre membre){

        //TODO : avoir plusieurs methodes pour mettre a jour le membre (par groupe de donnees car securite va etre differente)

        return membre;
    }

    @RequestMapping(value = "/public/membre", method = RequestMethod.POST)
    public Membre addMembre(@RequestBody Membre membre){

        // TODO : ne sauver que les informations generales

        return membre;
    }


//    @RequestMapping(method= RequestMethod.GET, path="/public/membre/createDb")
//    public Iterable<Membre> createMembreDb() throws IOException, InvalidFormatException {
//
//        Workbook wb = POIUtils.createWorkbook(new FileInputStream("/Users/fabricecalay/Downloads/membres.xls"));
//        Sheet sheet = POIUtils.getSheet(wb,0,false);
//
//        for (int i=1;i<sheet.getPhysicalNumberOfRows();i++){
//            String numero = POIUtils.readAsString(sheet,i,0);
//            String nom = POIUtils.readAsString(sheet,i,1);
//            String prenom = POIUtils.readAsString(sheet,i,2);
//            Object dateNaissanceObj = POIUtils.readDate(sheet,i,3);
//            String genre = POIUtils.readAsString(sheet,i,4);
//
//            if (!StringUtils.isEmpty(numero)){
//
//                //TODO : il y a des doublons dans la base initiale --> traiter lors de l'insertion
//
//                Membre membre = new Membre();
//                membre.setNumero(numero);
//                membre.setPrenom(prenom);
//                membre.setNom(nom);
//                if (dateNaissanceObj!=null && dateNaissanceObj instanceof Date){
//                    Date dateNaissance = (Date) dateNaissanceObj;
//                    Calendar gc = new GregorianCalendar();
//                    gc.setTime(dateNaissance);
//                    // Gestion bug de l'export Excel
//                    if (gc.get(Calendar.YEAR) > 2020){
//                        gc.add(Calendar.YEAR,-100);
//                    }
//                    membre.setDateNaissance(gc.getTime());
//                }
//                if ("F".equals(genre)){
//                    membre.setGenre(Genre.FEMME);
//                }else{
//                    membre.setGenre(Genre.HOMME);
//                }
//                try{
//                    membreRepository.save(membre);
//                }catch (Exception e){
//                    System.err.println("Doublon : " + numero);
//                    e.printStackTrace();
//                }
//
//            }
//        }
//
//
//        return membreRepository.findAll();
//    }

}