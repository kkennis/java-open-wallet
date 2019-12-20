package org.kkennis;

import org.kkennis.Wallet;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println("Creating testnet wallet without key");
        Wallet wallet = Wallet.create("my new wallet");
        System.out.println(wallet);
    }
}
