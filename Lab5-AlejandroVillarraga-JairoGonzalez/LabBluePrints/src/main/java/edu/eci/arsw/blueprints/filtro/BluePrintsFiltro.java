/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.filtro;
import edu.eci.arsw.blueprints.model.Blueprint;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2090540
 */
@Service
public interface BluePrintsFiltro {
   
    public Blueprint filtrar(Blueprint bp);
   
    
}
