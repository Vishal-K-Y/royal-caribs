package com.royalcaribs.proxyclient.utility;

import java.io.IOException;
import java.net.Socket;

public class SocketSingleton {
    private static SocketSingleton socketSingleton;
    private static String PROXY_SERVER_HOST;
    private static int PROXY_SERVER_PORT;
    private Socket socket;

    // Private constructor to enforce singleton
    private SocketSingleton(String host, int port) {
        PROXY_SERVER_HOST = host;
        PROXY_SERVER_PORT = port;
    }

    // Static method to get the singleton instance
    public static SocketSingleton getInstance(String host, int port) {
        if (socketSingleton == null) {
            socketSingleton = new SocketSingleton(host, port);
        }
        return socketSingleton;
    }

    // Method to get or initialize the socket
    public Socket getSocket() throws IOException {
        if (socket == null || socket.isClosed() || !socket.isConnected()) {
            reconnect();
        }
        return socket;
    }

    // Method to reconnect the socket
    private void reconnect() throws IOException {
        socket = new Socket(PROXY_SERVER_HOST, PROXY_SERVER_PORT);
        socket.setKeepAlive(true);
    }
}
