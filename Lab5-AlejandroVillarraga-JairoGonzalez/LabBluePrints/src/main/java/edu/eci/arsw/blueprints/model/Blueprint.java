
package edu.eci.arsw.blueprints.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.boot.jackson.JsonObjectSerializer;


public class Blueprint {

    private String author=null;
    
    private List<Point> points=null;
    
    private String name=null;
            
    public Blueprint(String author,String name,Point[] pnts){
        this.author=author;
        this.name=name;
        points=Arrays.asList(pnts);
    }
         
    public void deletePoint(int x){
        points.remove(x);
        
    }
    public Blueprint(String author, String name){
        this.author=author;
        this.name=name;
        points=new ArrayList<>();
    }

    public Blueprint() {
    }    
    
    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }
    
    public List<Point> getPoints() {
        return points;
    }
    
    public void addPoint(Point p){
        this.points.add(p);
    }

    @Override
    public String toString() {
        String concatena = new String();
        for (int i = 0; i < this.points.size(); i++) {
            if(this.points.get(i)!=null && i==0){
                concatena = concatena+("[{\"x\":"+this.points.get(i).getX()+",\"y\":"+this.points.get(i).getY()+"}");
            }if(this.points.get(i)!=null && i!=this.points.size()-1){
                concatena = concatena+(",{\"x\":"+this.points.get(i).getX()+",\"y\":"+this.points.get(i).getY()+"}");
            }if(this.points.get(i)!=null && i==this.points.size()-1){
                concatena = concatena+(",{\"x\":"+this.points.get(i).getX()+",\"y\":"+this.points.get(i).getY()+"}]");
            }
            
        }
        //return "Blueprint{" + "author=" + author + ", name=" + name + '}';
        return ("{\"author\":"+"\""+author+"\","+"\"points\":"+concatena+",\""+"name\":"+"\""+name+"\""+"}");
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Blueprint other = (Blueprint) obj;
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.points.size()!=other.points.size()){
            return false;
        }
        for (int i=0;i<this.points.size();i++){
            if (this.points.get(i)!=other.points.get(i)){
                return false;
            }
        }
        
        return true;
    }
    
    
    
}
