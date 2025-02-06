package si2024.miguelquirogaalu.p06;

import java.util.*;

public class STRIPS {
    
	public static List<Accion> plan(List<Estado> iniciales, List<Estado> finales, List<Accion> acciones) {

		Stack<Object> pila = new Stack<>();
		List<Accion> plan = new ArrayList<>();
		Set<Estado> estadoActual = new HashSet<>(iniciales);

		for (Estado eFinal : finales) {
			pila.push(eFinal);
		}
		
		System.out.println("\n*************************************\n");

		while (!pila.isEmpty()) {
			
			System.out.println("PILA: " + pila);

			Object cima = pila.peek();

			if (cima instanceof Accion) {

				Accion accion = (Accion) cima;

				if (sePuedeRealizar(accion, estadoActual)) {

					realizarAccion(accion, estadoActual);
					pila.pop();
					plan.add(accion);

				} else {

					for (Estado precondicion : accion.prerequisitos) {
						pila.push(precondicion);
					}
				}

			} else if (cima instanceof Estado) {

				Estado meta = (Estado) cima;

				if (estadoActual.contains(meta))
					pila.pop();

				else {

					Accion evitar = null;
					boolean alcanzable = false;

					while (!alcanzable) {

						if (repetido(pila, meta)) { // si hay bucle
							evitar = retroceder(pila);
							
							if(evitar == null)
								return null;
							
							meta = (Estado) pila.peek();
						}

						alcanzable = false;
						
						/*
						// IMPLEMENTACION MAS BASICA -> SE QUEDA CON LA PRIMERA ACCION UTIL
						for (Accion accion : acciones) {

							if (accion.adicion != null && accion.adicion.equals(meta) && !accion.equals(evitar)) {
								pila.push(accion);
								alcanzable = true;
								break;
							}
						}

						if (!alcanzable) {
							evitar = retroceder(pila);
							
							if(evitar == null)
								return null;
							
							meta = (Estado) pila.peek();
						}
						//
						*/
						
						// IMPLEMENTACION MAS COMPLEJA -> SE QUEDA CON LA MEJOR ACCION UTIL
						int min = Integer.MAX_VALUE;
						Accion mejorAccion = null;

						for (Accion accion : acciones) {

							if (accion.adicion != null && accion.adicion.equals(meta) && !accion.equals(evitar)) {
								
								List<Accion> planAux = plan(new ArrayList<Estado>(estadoActual), accion.prerequisitos, acciones);
								
								if(planAux.size() < min) {
									min = planAux.size();
									mejorAccion = accion;
								}
								
								alcanzable = true;
								//break;
							}
						}

						if (!alcanzable) {
							evitar = retroceder(pila);
							
							if(evitar == null)
								return null;
							
							meta = (Estado) pila.peek();
						}
						else
							pila.push(mejorAccion);
						//
					}
				}
			}
		}

		return plan;
	}

	private static boolean sePuedeRealizar(Accion accion, Set<Estado> estadoActual) {

		for (Estado pre : accion.prerequisitos) {

			if (!estadoActual.contains(pre)) {
				return false;
			}
		}

		return true;
	}

	private static void realizarAccion(Accion accion, Set<Estado> estadoActual) {

		if (accion.adicion != null) {
			estadoActual.add(accion.adicion);
		}

		if (accion.eliminacion != null) {
			estadoActual.remove(accion.eliminacion);
		}
	}

	private static Accion retroceder(Stack<Object> pila) {

		while (!pila.isEmpty() && pila.peek() instanceof Estado) {
			pila.pop();
		}

		if(!pila.isEmpty())
			return (Accion) pila.pop();
		else
			return null;
	}
	
	private static boolean repetido(Stack<Object> pila, Estado estado) {
		
		int n = 0;
		
		for(Object obj : pila) {
			
			if(obj instanceof Estado && estado.equals((Estado)obj))
				n++;
		}
		
		if(n > 1)
			return true;
		else
			return false;
	}
}