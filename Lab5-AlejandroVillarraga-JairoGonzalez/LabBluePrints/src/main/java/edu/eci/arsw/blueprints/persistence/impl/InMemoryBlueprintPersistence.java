/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.filtro.BluePrintsFiltro;
import edu.eci.arsw.blueprints.filtro.FiltroDeRedundancias;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115),};
        Blueprint bp0=new Blueprint("Jairo", "Maqueta",pts);
        blueprints.put(new Tuple<>(bp0.getAuthor(),bp0.getName()), bp0);
        Point[] pts1=new Point[]{new Point(14, 200),new Point(115, 115), new Point(200, 110), new Point(60,60)};
        Blueprint bp1=new Blueprint("Miguel","Mapaamigos",pts1);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);
        Point[] pts2=new Point[]{new Point(50, 150),new Point(110, 110), new Point(60,60)};
        Blueprint bp2=new Blueprint("Alejandro", "plano1",pts2);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        Point[] pts3=new Point[]{new Point(200, 250),new Point(115, 115)};
        Blueprint bp3=new Blueprint("Alejandro", "plano2",pts3);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);
        
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        System.out.println("Entro a agregar");
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        Blueprint bp = blueprints.get(new Tuple<>(author, bprintname));
        //FiltroDeRedundancias bpf = new FiltroDeRedundancias();
        //Blueprint bpfiltrado = bpf.filtrar(bp);
        return bp;
    }

    @Override
    public Set<Blueprint> getBlueprintByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> bpbyauthor = new HashSet<>();
        
        for (Map.Entry<Tuple<String, String>, Blueprint> entry : blueprints.entrySet()) {
            Tuple<String, String> key = entry.getKey();
            Blueprint value = entry.getValue();
            if(author.equals(value.getAuthor())){
                bpbyauthor.add(value);
            }
        }
        return bpbyauthor;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        Set<Blueprint> bpbyauthor = new HashSet<>();
        
        for (Map.Entry<Tuple<String, String>, Blueprint> entry : blueprints.entrySet()) {
            Tuple<String, String> key = entry.getKey();
            Blueprint value = entry.getValue();
            bpbyauthor.add(value);
        }
        return bpbyauthor;
    }

    
    @Override
    public void deleteBlueprint(String author, String nameBlueprint) throws BlueprintNotFoundException {
        blueprints.remove(new Tuple<>(author, nameBlueprint));
    }
    
    
    
    
}
