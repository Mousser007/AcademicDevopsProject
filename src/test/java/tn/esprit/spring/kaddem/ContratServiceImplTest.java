package tn.esprit.spring.kaddem;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.kaddem.controllers.ContratRestController;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;
import tn.esprit.spring.kaddem.services.IContratService;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Slf4j
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class ContratServiceImplTest {

    /*@InjectMocks
    private ContratRestController contratRestController;
    @Mock
    private IContratService contratService;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ContratRepository contratRepository;
    @Autowired
    private ContratServiceImpl contratServiceImpl;*/

    @InjectMocks
    private ContratRestController contratRestController;

    @InjectMocks
    private ContratServiceImpl contratServiceImpl;
    @Mock
    private ContratRepository contratRepository;


    @Test
    @Order(1)
    void testRetrieveAllContrats() {
        ArrayList<Contrat> contratList = new ArrayList<>();
        when(contratRepository.findAll()).thenReturn(contratList);
        List<Contrat> actualRetrieveAllContratsResult = contratServiceImpl.retrieveAllContrats();
        assertSame(contratList, actualRetrieveAllContratsResult);
        assertTrue(actualRetrieveAllContratsResult.isEmpty());
        verify(contratRepository).findAll();
    }
       @Test
       @Order(2)
        void testAddContrat() {
            Contrat contrat = new Contrat();
            contrat.setIdContrat(1);
            contrat.setMontantContrat(500);
            contrat.setDateDebutContrat(new Date(2023,10,31));
            contrat.setDateFinContrat(new Date(2023,12,31));
            contrat.setArchive(true);
            contrat.setSpecialite(Specialite.IA);

            when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);

            Contrat savedContrat = contratServiceImpl.addContrat(contrat);

            Assertions.assertNotNull(savedContrat);
            Assertions.assertEquals(1, savedContrat.getIdContrat());
            Assertions.assertEquals(500, savedContrat.getMontantContrat());
            Assertions.assertTrue(savedContrat.getArchive());
            Assertions.assertEquals(new Date(2023,10,31), savedContrat.getDateDebutContrat());
            Assertions.assertEquals(new Date(2023,12,31), savedContrat.getDateFinContrat());
            Assertions.assertEquals(Specialite.IA,savedContrat.getSpecialite());
            verify(contratRepository).save(any(Contrat.class));
        }

    @Test
    @Order(3)
     void testRetrieveContrat() {
        Contrat contrat = new Contrat();
        Mockito.when(contratRepository.findById(Mockito.any())).thenReturn(Optional.of(contrat));
        Contrat contrat1 = contratServiceImpl.retrieveContrat(2);
        Assertions.assertNotNull(contrat1);
    }

    @Test
    @Order(4)
     void TestUpdateContrat() {
        Contrat contrat = new Contrat();
        contrat.setIdContrat(1);
        contrat.setMontantContrat(500);
        contrat.setDateDebutContrat(new Date(2023,10,31));
        contrat.setDateFinContrat(new Date(2023,12,31));
        contrat.setArchive(false);
        contrat.setSpecialite(Specialite.IA);

        when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);

        Contrat savedContrat = contratServiceImpl.addContrat(contrat);

        savedContrat.setMontantContrat(600);
        savedContrat.setDateDebutContrat(new Date(2023,10,31));
        savedContrat.setDateFinContrat(new Date(2023,11,30));
        savedContrat.setArchive(true);
        savedContrat.setSpecialite(Specialite.CLOUD);

        when(contratRepository.save(any(Contrat.class))).thenReturn(savedContrat);

        Contrat contratMisAJour = contratServiceImpl.updateContrat(savedContrat);


        Assertions.assertEquals(600, contratMisAJour.getMontantContrat());
        Assertions.assertEquals(new Date(2023,10,31), contratMisAJour.getDateDebutContrat());
        Assertions.assertEquals(new Date(2023,11,30), contratMisAJour.getDateFinContrat());
        Assertions.assertEquals(Specialite.CLOUD,contratMisAJour.getSpecialite());
        assertTrue(contratMisAJour.getArchive());
        verify(contratRepository,times(2)).save(any(Contrat.class));
    }

    @Test
    @Order(5)
     void TestRemoveContrat() {
        Contrat contrat = new Contrat();
        contrat.setIdContrat(1);
        contrat.setMontantContrat(500);
        contrat.setDateDebutContrat(new Date(2023,10,31));
        contrat.setDateFinContrat(new Date(2023,12,31));
        contrat.setArchive(false);
        contrat.setSpecialite(Specialite.IA);


        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));


        doNothing().when(contratRepository).delete(contrat);


        contratServiceImpl.removeContrat(1);


        verify(contratRepository).findById(1);


        verify(contratRepository).delete(contrat);

        Optional<Contrat> removedContrat = contratRepository.findById(1);

        Assertions.assertTrue(removedContrat.isPresent());

    }

}






