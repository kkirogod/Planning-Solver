package si2024.miguelquirogaalu.p06;

import java.util.Objects;

public class Estado {
    String nombre;

    public Estado(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
    	
        if (this == o) 
        	return true;
        
        if (o == null || getClass() != o.getClass()) 
        	return false;
        
        Estado estado = (Estado) o;
        
        return Objects.equals(nombre, estado.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
