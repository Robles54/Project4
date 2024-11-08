//New Maria Galarza        Project 4
//Creates ServerSocket for JUnit test for viewOrders method.
package application;

import java.io.IOException;
import java.net.ServerSocket;

public class Test_viewOrders_Helper {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(32007)) { 
            System.out.println("Helper server started on port " + serverSocket.getLocalPort());
            while (true) {
                new StoreThread(serverSocket.accept()).start();
            }
        }
    }
}