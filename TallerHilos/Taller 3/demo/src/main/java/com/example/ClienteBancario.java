package com.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ClienteBancario {
    private String cedula;
    private String nombre;
    private double balance;
    private String archTX; //archivo de transacciones

    public ClienteBancario(String cedula, String nombre, double balance, String archTX) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.balance = balance;
        this.archTX = archTX;
    }

    public String getNombre() {
        return nombre;
    }

    public double getBalance() {
        return balance;
    }

    public String getArchTX() {
        return archTX;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "ClienteBancario{" + "nombre=" + nombre + ", balance=" + balance + '}';
    }
    

    public static ArrayList<ClienteBancario> getClientes(){
        ArrayList<ClienteBancario> lista = new ArrayList<>();
        lista.add(new ClienteBancario("1234567890", "Mario Calle",50,"TX1234567890.txt"));
        lista.add(new ClienteBancario("0934567812", "Valeria Naranjo",100,"TX0934567812.txt"));
        lista.add(new ClienteBancario("1334567892", "Jaime Guerrero",120,"TX1334567892.txt"));
        return lista;
    }


    //completar método
    public static void actualizarBalances(ArrayList<ClienteBancario> lstClientes){
            for (ClienteBancario cliente: lstClientes){
                Thread hilo = new Thread(()->{
                System.out.println("Procesando las transacciones del cliente: " + cliente.getNombre());
                    try (BufferedReader br = new BufferedReader(new FileReader(cliente.getArchTX()))){
                        String line;
                        while ((line = br.readLine()) != null){
                            String[] linea = line.split(",");
                            if (linea[0].equals("credito")){
                                cliente.setBalance(cliente.getBalance() + Integer.parseInt(linea[1]) );           
                            } else{
                                cliente.setBalance(cliente.getBalance() - Integer.parseInt(linea[1]));
                            }
                        }
                        try{
                            Thread.sleep(2000);
                            System.out.println("Cliente actualizado " + cliente.getNombre() + " : " + cliente.getBalance());
                        } catch (InterruptedException e){
                        }
                    }catch (IOException e) {
                    e.getMessage();
                    } 
                });
            hilo.start();
            }     
    }
}

