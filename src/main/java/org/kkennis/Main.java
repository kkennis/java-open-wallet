package org.kkennis;

import org.kkennis.Wallet;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println("Creating testnet wallet without key");
        Wallet wallet = Wallet.create("my new wallet");
        System.out.println(wallet);

        try {
            final String filename = String.format("examples/wallet-%s.txt", System.currentTimeMillis());
            wallet.exportToFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("wrote wallet to file");
    }
}
