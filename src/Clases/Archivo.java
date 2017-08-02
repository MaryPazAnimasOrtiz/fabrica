
package Clases;

import java.io.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public final class Archivo 
{
    Lista<Double[]> lista;
    File archivo;
    String nombreArchivo;

    public Archivo() 
    {
        System.out.println("Archivo Nulo: ");
        System.out.print("Nombre o Dirección no designado");
    }

    public Archivo(String nombreArchivo) 
    {
        this.nombreArchivo = nombreArchivo;
        archivo =  new File(nombreArchivo);
        
    }
    
    public String getNombreArchivo() 
    {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
    public void AgregarArchivo()
    {
        
    }
    
    public JTable LeerDatos(JTable tabla) throws FileNotFoundException, IOException
    {
        lista = new Lista<>();
        try
        {
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            String [] vector;
            FileInputStream fstream = new FileInputStream(archivo);
            try (DataInputStream entrada = new DataInputStream(fstream)) 
            {
                BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
                String strLinea;
                modelo.setRowCount(0);
                while ((strLinea = buffer.readLine()) != null) 
                { 
                    Double []datos = new Double[2];
                    vector = strLinea.split("/");
                    datos[0] = Double.parseDouble(vector[0]);
                    datos[1] = Double.parseDouble(vector[1]);
                    lista.insertarUltimo(datos);
                    modelo.addRow(vector);
                }
            }
        }
        catch (IOException e)
        { 
            System.err.println("Ocurrio un error: " + e.getMessage());
        }
        return tabla;
    }
    
    public JTable CalcularOperaciones(JTable tabla)
    {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        double mediaA = 0;
        double mediaB = 0;
        double desviacioA = 0;
        double desviacioB = 0;
        for (int i = 0; i < lista.cuantosElementos(); i++) 
        {
            mediaA+=lista.devolverDato(i)[0];
            mediaB+=lista.devolverDato(i)[1];
        }
        mediaA = mediaA / lista.cuantosElementos();
        mediaB = mediaB / lista.cuantosElementos();
        for (int i = 0; i < lista.cuantosElementos(); i++) 
        {
            desviacioA += Math.pow(lista.devolverDato(i)[0],2);
            desviacioB += Math.pow(lista.devolverDato(i)[1],2);
        }
        desviacioA = Math.sqrt(desviacioA / lista.cuantosElementos());
        desviacioB = Math.sqrt(desviacioA / lista.cuantosElementos());
        String vector[] = {""+mediaA,""+desviacioA,""+mediaB,""+desviacioB};
        modelo.addRow(vector);
        return tabla;
    }
    
    public void InsertarDatos(int posicion,Double vector[],JTable tabla, JTable tabla2) throws IOException
    {
        switch (posicion)
        {
            case 1:
            {
                lista.insertarPrimero(vector);
                ActualizarTXT();
                LeerDatos(tabla);
                CalcularOperaciones(tabla2);
            }
            break;
            case 3:
            {
                lista.insertarUltimo(vector);
                ActualizarTXT();
                LeerDatos(tabla);
                CalcularOperaciones(tabla2);
            }
            break;
            case 2:
            {
                lista.introducirDato(Math.round(lista.cuantosElementos()/2)-1, vector);
                ActualizarTXT();
                LeerDatos(tabla);
                CalcularOperaciones(tabla2);
            }
        }
    }
    
    private void ActualizarTXT() throws IOException
    {
        
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(archivo.getName()))) 
        {
            bufferedWriter.flush();
        }
        try
        {
            FileOutputStream salida = new FileOutputStream(new File(archivo.getName()),true);
            try (PrintWriter writer = new PrintWriter(salida))
            {
                for (int i = 0; i < lista.cuantosElementos(); i++) 
                {
                    if(i == 0)
                    {
                        writer.append(lista.devolverDato(i)[0]+"/"+lista.devolverDato(i)[1]);
                    }
                    else
                    {
                        writer.println();
                        writer.append(lista.devolverDato(i)[0]+"/"+lista.devolverDato(i)[1]);
                    }
                }
            }
        }
        catch(IOException e)
        {
            System.out.println(""+e);
        }
    }
    
    public void EscribirDatos() 
    {
       try
       {
           FileOutputStream salida = new FileOutputStream(new File("Archivo.txt"),true);
           try (PrintWriter writer = new PrintWriter(salida)) 
           {
               writer.println();
               writer.append(JOptionPane.showInputDialog(null,"Escriba la linea"));
           }
       }
       catch(FileNotFoundException e)
       {
           System.out.println(""+e);
       }
    }
    
    public void BuscarArchivo(){
//    {
//        JFileChooser selectorArchivos = new JFileChooser();
//        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivo", "txt");
//        File archivo = selectorArchivos.getFileFilter();
//        System.out.println(""+selectorArchivos.getName(f));
    }
}
