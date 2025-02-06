package si2024.miguelquirogaalu.p06;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Practica_06_exe {

	private static List<Estado> estadosIniciales = new ArrayList<>();
	private static List<Estado> estadosFinales = new ArrayList<>();
	private static List<Accion> acciones = new ArrayList<>();

	public static void main(String[] args) throws IOException {

		//cargarArchivo("src/si2024/miguelquirogaalu/p06/entrada.txt");
		cargarArchivo(args[0]);

		List<Accion> plan = STRIPS.plan(estadosIniciales, estadosFinales, acciones);

		if (plan != null) {
			
			ecribirArchivo(plan, "../solucion.txt");

			System.out.println("\n\nPLAN ENCONTRADO:");

			for (Accion accion : plan) {
				System.out.println(accion.nombre);
			}

		} else
			System.out.println("\n\nERROR: No se encontró un plan.");
	}

	public static void ecribirArchivo(List<Accion> plan, String filename) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

		for (Accion accion : plan) {
			
			writer.write(accion.nombre);
			writer.newLine();
		}
		
		writer.close();
	}

	private static void cargarArchivo(String archivo) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(archivo));
		String linea;

		while ((linea = reader.readLine()) != null) {

			linea = linea.trim();

			// Ignoramos comentarios y líneas vacías
			if (linea.isEmpty() || linea.startsWith("#")) {
				continue;
			}

			// Estados iniciales
			if (linea.startsWith("Ei:")) {

				String[] iniciales = linea.substring(3).split(",");

				for (String nombre : iniciales) {

					nombre = nombre.trim();

					Estado estado = new Estado(nombre);

					estadosIniciales.add(estado);
				}
			}

			// Estados finales
			if (linea.startsWith("Ef:")) {

				String[] finales = linea.substring(3).split(",");

				for (String nombre : finales) {

					nombre = nombre.trim();

					Estado estado = new Estado(nombre);

					estadosFinales.add(estado);
				}
			}

			// Acciones
			if (linea.startsWith("Accion:")) {

				String[] partes = linea.split(";");
				String nombre = partes[0].split(":")[1].trim();

				// Prerequisitos
				String[] prerequisitosNombres;

				if (partes[1].trim().isEmpty())
					prerequisitosNombres = new String[0];
				else
					prerequisitosNombres = partes[1].trim().split(",");

				ArrayList<Estado> prerequisitos = new ArrayList<Estado>(prerequisitosNombres.length);

				for (String n : prerequisitosNombres) {

					if (!prerequisitos.contains(n.trim()))
						prerequisitos.add(new Estado(n.trim()));
				}

				// Adición
				Estado adicion = null;
				if (!partes[2].trim().isEmpty()) {
					adicion = new Estado(partes[2].trim());
				}

				// Eliminación
				Estado eliminacion = null;
				if (partes.length > 3 && !partes[3].trim().isEmpty()) {
					eliminacion = new Estado(partes[3].trim());
				}

				Accion accion = new Accion(nombre, prerequisitos, adicion, eliminacion);
				acciones.add(accion);
			}
		}

		reader.close();

		System.out.println("Estados Iniciales: " + estadosIniciales);
		System.out.println("Estados Finales: " + estadosFinales);
		System.out.println("Acciones: ");
		for (Accion accion : acciones) {
			System.out.println("Accion: " + accion + " " + accion.prerequisitos + " => " + accion.adicion + " - "
					+ accion.eliminacion);
		}
	}
}
