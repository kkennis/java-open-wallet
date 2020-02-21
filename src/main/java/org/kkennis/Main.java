package org.kkennis;

import org.bitcoinj.crypto.DeterministicKey;
import org.kkennis.Wallet;

import java.io.IOException;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        try {
//            saveLoadCompare(args);
            loadAndDerive(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveLoadCompare(String[] args) throws Exception {
        final String filename = String.format("examples/wallet-%s.txt", System.currentTimeMillis());

        System.out.println("Creating testnet wallet without key");
        Wallet wallet = Wallet.create("my new wallet");
        System.out.println(wallet);
        wallet.exportToFile(filename);

        System.out.println("wrote wallet to file, will re-import");

        System.out.println("Importing Wallet:");

        Wallet importedWallet;
        importedWallet = Wallet.importSeedFromFile(filename);
        System.out.println("imported wallet:");
        System.out.println(importedWallet);

        System.out.println("Do the wallet pubs match?");
        System.out.println(wallet.keychain.pub.equals(importedWallet.keychain.pub));
    }

    public static void loadAndDerive(String[] args) throws Exception {
        final String filename = String.format("examples/wallet-1582240830247.txt", System.currentTimeMillis());

        System.out.println("Importing Wallet:");

        Wallet importedWallet;
        importedWallet = Wallet.importSeedFromFile(filename);
        System.out.println("imported wallet:");
        System.out.println(importedWallet);

        DeterministicKey rootKey = importedWallet.deriveKey("");
        System.out.println("Root Key:");
        System.out.println(rootKey);

        DeterministicKey key1 = importedWallet.deriveKey("0/0/1/100");
        System.out.println("Key 1:");
        System.out.println(key1);

        DeterministicKey key2 = importedWallet.deriveKey("0/0/1/100'");
        System.out.println("Key 2:");
        System.out.println(key2);

        System.out.println("Key 3:");
        try {
            DeterministicKey key3 = importedWallet.deriveKey("0/0/1/hello");
            System.out.println("This line should not print");
        } catch (PathFormatException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Re-derive Key 2:");
        DeterministicKey key4 = importedWallet.deriveKey("0/0/1/100");
        System.out.println(key4);
    }
}
