package com.msh.mrfix.helpers;

public class SecurityConstants {

    public static final String REQUEST_TOKEN_URL = "/login";

    public static final String AUTHORITIES_KEY = "CLAIM_TOKEN";

    public static final byte[] SIGNING_KEY = "n2r5u8x/A%D*G-KaPdogVkYp3s6v9y$B&A(H+MbQeThWmZq4t7w!z%C*F-J@NcRf".getBytes();

    public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 864000000;

    public static final String ISSUER_TOKEN = "secure-api";

    public static final String HEADER_AUTHORIZATION_KEY = "Authorization";

    public static final String TOKEN_BEARER_PREFIX = "Bearer ";
}
