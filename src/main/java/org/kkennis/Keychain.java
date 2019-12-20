package org.kkennis;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.params.TestNet3Params;

import java.security.SecureRandom;

public class Keychain {
    public String prv;
    public String pub;
    public DeterministicKey masterKey;

    public Keychain(DeterministicKey masterKey) {
        this.masterKey = masterKey;

        NetworkParameters networkParams = TestNet3Params.get();

        this.prv = this.masterKey.serializePrivB58(networkParams);
        this.pub = this.masterKey.serializePubB58(networkParams);
    }

    // Add ability to encrypt/decrypt keychain with something known to user
    public static Keychain createNew() {
        SecureRandom random = new SecureRandom();
        ECKey newKey = new ECKey(random);
        byte[] chainCode = new byte[32];
        random.nextBytes(chainCode);
        DeterministicKey masterKey = HDKeyDerivation.createMasterPrivKeyFromBytes(newKey.getPrivKeyBytes(), chainCode);
        return new Keychain(masterKey);
    }

//    public static Keychain importMnemonic(String mnemonic) throws Exception {
//        InputStream seedStgream = new ByteArrayInputStream(mnemonic.getBytes());0
//
//        MessageDigest digest = null;
//        try {
//            digest = MessageDigest.getInstance("SHA-256");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            throw e;
//        }
//
//        byte[] encodedHash = digest.digest(mnemonic.getBytes(StandardCharsets.UTF_8));
//
//        MnemonicCode mnemonicCode = null;
//        try {
//            mnemonicCode = new MnemonicCode(seedStream, null);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw e;
//        }
//
//        byte[] seed = mnemonicCode.toSeed();
//
//        NetworkParameters networkParams = TestNet3Params.get();
//
//
//        return new Keychain(masterKey)
//    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Keychain{");
        sb.append("\n\tprv='").append(prv).append('\'');
        sb.append("\n\tpub='").append(pub).append('\'');
        sb.append("\n}");
        return sb.toString();
    }
}
