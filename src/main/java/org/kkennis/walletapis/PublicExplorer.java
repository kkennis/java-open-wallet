package org.kkennis.walletapis;

import org.json.simple.JSONObject;

import java.util.HashMap;

enum ACTION {
    GET_LATEST_BLOCK,
    GET_BLOCK,
    GET_TRANSACTION,
    GET_ADDRESS,
    GET_ADDRESS_MULTI,
    GET_UTXOS,
    GET_BALANCE,
    GET_UNCONFIRMED_TXS,
    DECODE_TX,
    PUSH_TX
}

abstract public class PublicExplorer {
    public String baseUrl;
    public HashMap<ACTION, String> endpoints;

//    public JSONObject call(ACTION action, JSONObject params) {
//
//    }
}
