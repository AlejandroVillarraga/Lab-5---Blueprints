/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.filtro;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *(A) Filtrado de redundancias: suprime del plano los puntos consecutivos que sean repetidos.
 * @author 2101751
 */
@Service
public class FiltroDeRedundancias implements BluePrintsFiltro{

    
    public Blueprint filtrar(Blueprint bp) {
        
        Blueprint bpfiltrado = new Blueprint(bp.getAuthor(), bp.getName());
        List<Point> points= bp.getPoints();
        List<Point> pntsSinRedundacia = null;
        
        int i = 0;
        while(i<points.size()){
            
            System.out.println((points.get(i).getX()==points.get(i+1).getX()) && (points.get(i).getY()==points.get(i+1).getY()));
            System.out.println(points.get(i).getX()+"****"+points.get(i).getY());
            System.out.println(points.get(i+1).getX()+"****"+points.get(i+1).getY());
            if((points.get(i).getX()==points.get(i+1).getX()) && (points.get(i).getY()==points.get(i+1).getY())){
                
                pntsSinRedundacia.add(points.get(i));
                //points.remove(i);
                System.out.println("Entro aqui"+i);
                bpfiltrado.addPoint(points.get(i));
                i=i+2;
            }
            else{
                pntsSinRedundacia.add(points.get(i));
                i++;
            }
        }
        
        return bpfiltrado;
    }
    
}
