package com.bank.uat;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

public class BankHttpClient {

  private static final String HOST_NAME = "http://localhost:9090";
  private static final String CREATE_TRANSACTION_URL = "/create-transaction";
  private static final String CHECK_BALANCE_URL = "/check-balance";
  private static final String TRANSACTION_STATUS = "/status-transaction";
  private static final String MEDIA_TYPE_JSON = "application/json";

  private final CloseableHttpClient httpClient;

  public BankHttpClient() {
    httpClient = HttpClients.createDefault();
  }

  /**
   * Checks the balance of the customer
   * @param username the username of the customer
   * @param pin the password of the customer
   * @param iban the iban of the customer
   * @return
   * @throws Exception
   */
  public double getBalance(final String username, final String pin, final String iban)
      throws Exception {
    JSONObject json = new JSONObject();
    json.put("username", username);
    json.put("pin", pin);
    json.put("iban", iban);
    HttpResponse response = doPost(json.toString(), CHECK_BALANCE_URL);
    final int httpResponseCode = response.getStatusLine().getStatusCode();
    if (httpResponseCode != HttpStatus.SC_OK) {
      throw new Exception("Error occurred with http status code :" + httpResponseCode);
    }
    String responseString = IOUtils
        .toString(response.getEntity().getContent(), Charset.defaultCharset());
    JSONObject responseJson = new JSONObject(responseString);
    return responseJson.getDouble("balance");
  }

  /**
   * Creates a transaction
   * @param username the username of the customer creating the transaction
   * @param pin the password of the username
   * @param toIban the iban of the receiver
   * @param fromIban the iban of the sender
   * @param amount the amount of the transaction
   * @param transactionMessage the message explaining the transaction
   * @param transactionReference the reference to identify the transaction
   * @param transactionDate the transaction date
   * @return
   * @throws Exception
   */
  public int createTransaction(final String username, final String pin, final String toIban,
      final String fromIban,final Double amount, final String transactionMessage, final String transactionReference,
      final String transactionDate) throws Exception {
    JSONObject json = new JSONObject();
    json.put("username", username);
    json.put("pin", pin);
    json.put("toIban", toIban);
    json.put("fromIban", fromIban);
    json.put("amount", amount);
    json.put("transactionMessage", transactionMessage);
    json.put("transactionReference", transactionReference);
    json.put("transactionDate", transactionDate);

    HttpResponse response = doPost(json.toString(), CREATE_TRANSACTION_URL);
    final int httpResponseCode = response.getStatusLine().getStatusCode();
    if (httpResponseCode != HttpStatus.SC_CREATED) {
      throw new Exception("Error occurred with http status code :" + response.getStatusLine());
    }
    return httpResponseCode;
  }

  /**
   * Checking the status of the transaction
   * @param iban the iban of the customer
   * @return
   * @throws Exception
   */
  public String checkFirstTransactionStatus(final String iban) throws Exception {
    JSONObject json = new JSONObject();
    json.put("iban", iban);

    HttpResponse response = doPost(json.toString(), TRANSACTION_STATUS);
    final int httpResponseCode = response.getStatusLine().getStatusCode();
    if (httpResponseCode != HttpStatus.SC_OK) {
      throw new Exception("Error occurred with http status code :" + response.getStatusLine());
    }
    String responseString = IOUtils
        .toString(response.getEntity().getContent(), Charset.defaultCharset());
    JSONArray responseJson = new JSONArray(responseString);
    Object obj =  responseJson.get(0);
    return ((JSONObject) obj).getString("transactionStatus");
  }


  /**
   * Doing the http post.
   * @param jsonString
   * @param url
   * @return
   * @throws IOException
   */
  private HttpResponse doPost(final String jsonString, final String url) throws IOException {
    final HttpPost post = new HttpPost(HOST_NAME + url);
    post.setHeader(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE_JSON);
    post.setHeader(HttpHeaders.ACCEPT, MEDIA_TYPE_JSON);
    post.setEntity(new StringEntity(jsonString));
    return httpClient.execute(post);
  }


}
