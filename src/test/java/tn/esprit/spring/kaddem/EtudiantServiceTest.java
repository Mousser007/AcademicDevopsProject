/*package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.EtudiantServiceImpl;
import tn.esprit.spring.kaddem.services.IEtudiantService;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class EtudiantServiceTest {
    @Autowired
    IEtudiantService ies;
    @Autowired
    EtudiantRepository etudiantRepository;
    EtudiantServiceImpl es;


    @Test
    @Order(1)
    public void TestaddEtudiant (){
        Etudiant etudiant = new Etudiant();
        etudiant.setNomE("Selmi");
        etudiant.setPrenomE("Moussa");
        Etudiant savedEtudiant=es.addEtudiant(etudiant);
        Assertions.assertNotNull(savedEtudiant);
        Assertions.assertEquals("Selmi",savedEtudiant.getNomE());
        Assertions.assertEquals("Moussa",savedEtudiant.getPrenomE());
    }

    @Test
    @Order(2)
    @Transactional
    public void TestretrieveAllEtudiants(){
    List<Etudiant> etudiantList=    (List<Etudiant>) etudiantRepository.findAll();
        Assertions.assertEquals(0, etudiantList.size());
    }
    @Test
    @Order(3)
    @Transactional
    public void TestupdateEtudiant (){
        Etudiant etudiant = new Etudiant();
        etudiant.setNomE("Selmi");
        etudiant.setPrenomE("Moussa");
        Etudiant savedEtudiant = es.addEtudiant(etudiant);
        Assertions.assertNotNull(savedEtudiant);

        savedEtudiant.setNomE("Selmiii");

        Etudiant updatedEtudiant = es.updateEtudiant(savedEtudiant);
        Assertions.assertNotNull(updatedEtudiant);

        Assertions.assertEquals("Selmiii", updatedEtudiant.getNomE());
        Assertions.assertEquals("Moussa", updatedEtudiant.getPrenomE());
    }
    @Test
    @Order(4)
    @Transactional
    public void TestremoveEtudiant(){
        Etudiant etudiant = new Etudiant();
        etudiant.setNomE("test");
        etudiant.setPrenomE("pp");

        Etudiant savedEtudiant = etudiantRepository.save(etudiant);

        Assertions.assertNotNull(savedEtudiant);

        es.removeEtudiant(savedEtudiant.getIdEtudiant());

        Etudiant removedEtudiant = etudiantRepository.findById(savedEtudiant.getIdEtudiant()).orElse(null);
        Assertions.assertNull(removedEtudiant);
    }

}*/
