package org.kkennis;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;

import java.security.SecureRandom;
import java.util.List;

public class Keychain {
    public static String network = "testnet";
    public static NetworkParameters networkParams = TestNet3Params.get();


    public String pub;
    public DeterministicKeyChain singleSigDerivationKeychain;

    public Keychain(DeterministicKeyChain masterKeyChain) {
        this.singleSigDerivationKeychain = masterKeyChain;
        this.pub = this.singleSigDerivationKeychain.getWatchingKey().serializePubB58(Keychain.networkParams);
    }

    // Add ability to encrypt/decrypt keychain with something known to user
    public static Keychain createNew() {
        // Create a new seed
        SecureRandom random = new SecureRandom();
        DeterministicSeed seed = new DeterministicSeed(
                random,
                DeterministicSeed.DEFAULT_SEED_ENTROPY_BITS,
                ""
        );

        DeterministicKeyChain masterKeyChain = DeterministicKeyChain.builder()
                .seed(seed).outputScriptType(Script.ScriptType.P2PKH).build();
        return new Keychain(masterKeyChain);
    }

    public static Keychain createFromSeed(String mnemonic, long creationTimeSeconds) throws UnreadableWalletException {
        DeterministicSeed seed = new DeterministicSeed(mnemonic, null, "", creationTimeSeconds);

        // Create single sig key by default, information to create multisig key
        DeterministicKeyChain masterKeyChain = DeterministicKeyChain.builder()
                .seed(seed).outputScriptType(Script.ScriptType.P2PKH).build();

        return new Keychain(masterKeyChain);
    }

    public String getMnemonic() {
        List<String> mnemonicCode = this.singleSigDerivationKeychain.getSeed().getMnemonicCode();
        return String.join(" ", mnemonicCode);
    }

    public long getCreationTimeSeconds() {
        return this.singleSigDerivationKeychain.getSeed().getCreationTimeSeconds();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Keychain{");
        sb.append("\n\tseed='").append(this.getMnemonic()).append('\'');
        sb.append("\n\tpub='").append(this.pub).append('\'');
        sb.append("\n}");
        return sb.toString();
    }
}
