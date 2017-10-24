/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.filtro;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import java.util.List;

/**
 *
 * @author 2101751
 */
public class FiltroDeSubmuestreo implements BluePrintsFiltro{

    @Override
    public Blueprint filtrar(Blueprint bp) {Blueprint bpfiltrado = new Blueprint(bp.getAuthor(), bp.getName());
        System.out.println("Entro a filtrar");
        List<Point> points = bp.getPoints();
        Point[] pntsSinRedundacia = null;

        for (int i = 0; i < points.size() - 1; i++) {
            if (i%2!=0) {
                bpfiltrado.addPoint(points.get(i));
            }
        }

        return bpfiltrado;
    }
    
}
