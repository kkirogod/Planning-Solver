package si2024.miguelquirogaalu.p06;

import java.util.ArrayList;
import java.util.Objects;

public class Accion {

	String nombre;
	ArrayList<Estado> prerequisitos;
	Estado adicion;
	Estado eliminacion;
	
	public Accion(String nombre, ArrayList<Estado> prerequisitos, Estado adicion, Estado eliminacion) {
		this.nombre = nombre;
		this.prerequisitos = prerequisitos;
		this.adicion = adicion;
		this.eliminacion = eliminacion;
	}
	
	@Override
    public String toString() {
        return nombre;
    }
	
	@Override
    public boolean equals(Object o) {
    	
        if (this == o) 
        	return true;
        
        if (o == null || getClass() != o.getClass()) 
        	return false;
        
        Accion accion = (Accion) o;
        
        return Objects.equals(nombre, accion.nombre);
    }
}
