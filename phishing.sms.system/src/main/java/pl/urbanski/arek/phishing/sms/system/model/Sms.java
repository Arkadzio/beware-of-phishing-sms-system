package pl.urbanski.arek.phishing.sms.system.model;

public class Sms {

  private String sender;
  private String recipient;
  private String message;

  public Sms() {
  }

  public Sms(String sender, String recipient, String message) {
    this.sender = sender;
    this.recipient = recipient;
    this.message = message;
  }

  public String getSender() {
    return sender;
  }

  public String getRecipient() {
    return recipient;
  }

  public String getMessage() {
    return message;
  }
}
